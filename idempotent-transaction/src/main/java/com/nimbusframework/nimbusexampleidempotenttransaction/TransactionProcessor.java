package com.nimbusframework.nimbusexampleidempotenttransaction;

import com.nimbusframework.nimbuscore.annotations.deployment.AfterDeployment;
import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.QueueServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.clients.store.TransactionalClient;
import com.nimbusframework.nimbuscore.clients.store.WriteItemRequest;
import com.nimbusframework.nimbuscore.clients.store.conditions.ComparisonOperator;
import com.nimbusframework.nimbuscore.clients.store.conditions.Condition;
import com.nimbusframework.nimbuscore.clients.store.conditions.ConditionBuilder;
import com.nimbusframework.nimbuscore.clients.store.conditions.function.AttributeNotExists;
import com.nimbusframework.nimbuscore.clients.store.conditions.variable.NumericVariable;
import com.nimbusframework.nimbuscore.exceptions.NonRetryableException;
import com.nimbusframework.nimbuscore.exceptions.RetryableException;
import com.nimbusframework.nimbuscore.exceptions.StoreConditionException;
import java.util.LinkedList;
import java.util.List;

public class TransactionProcessor {

  static final String QUEUE_IDENTIFIER = "TransactionQueue";

  private DocumentStoreClient<TransactionRequest> processedTransactions = ClientBuilder.getDocumentStoreClient(TransactionRequest.class);
  private DocumentStoreClient<Balance> balances = ClientBuilder.getDocumentStoreClient(Balance.class);
  private TransactionalClient transactionalClient = ClientBuilder.getTransactionalClient();

  @QueueServerlessFunction(id = QUEUE_IDENTIFIER, batchSize = 1)
  @UsesDocumentStore(dataModel = TransactionRequest.class)
  @UsesDocumentStore(dataModel = Balance.class)
  public void processTransaction(TransactionRequest transactionRequest) throws RetryableException {
    try {
      TransactionRequest existingRequest = processedTransactions.get(transactionRequest.getTransactionUid());
      if (existingRequest != null) return;

      Balance senderBalance = balances.get(transactionRequest.getSender());
      Balance receiverBalance = balances.get(transactionRequest.getReceiver());

      if (senderBalance == null) {
        processedTransactions.put(transactionRequest);
        return;
      }

      List<WriteItemRequest> transactionItems = new LinkedList<>();

      if (receiverBalance == null) {
        receiverBalance = new Balance(transactionRequest.getReceiver(), transactionRequest.getAmount());
        Condition receiverDoesNotExist = new AttributeNotExists("user");
        transactionItems.add(balances.getWriteItem(receiverBalance, receiverDoesNotExist));
      } else {
        transactionItems.add(balances.getIncrementValueRequest(transactionRequest.getReceiver(), "amount", transactionRequest.getAmount()));
      }
      Condition updateCondition = ConditionBuilder.ifComparison("amount", ComparisonOperator.GREATER_THAN_OR_EQUAL, new NumericVariable(transactionRequest.getAmount())).build();

      transactionItems.add(balances.getDecrementValueRequest(transactionRequest.getSender(), "amount", transactionRequest.getAmount(), updateCondition));
      transactionItems.add(processedTransactions.getWriteItem(transactionRequest));

      try {
        transactionalClient.executeWriteTransaction(transactionItems);
      } catch (StoreConditionException e) {
        // Sender doesn't have to enough money so we don't want function to execute again, or user suddenly exists
      }
    } catch (NonRetryableException e) {
      // Can log this error
    }

  }

  @AfterDeployment
  @UsesDocumentStore(dataModel = Balance.class)
  public void createTestUser() throws RetryableException {
    try {
      balances.put(new Balance("test", 10000f));
    } catch (NonRetryableException e) {
      e.printStackTrace();
    }
  }

}
