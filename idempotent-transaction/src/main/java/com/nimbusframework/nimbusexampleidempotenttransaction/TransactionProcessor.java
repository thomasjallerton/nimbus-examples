package com.nimbusframework.nimbusexampleidempotenttransaction;

import com.nimbusframework.nimbuscore.annotations.deployment.AfterDeployment;
import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.QueueServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.clients.store.TransactionalClient;
import com.nimbusframework.nimbuscore.clients.store.WriteItemRequest;
import java.util.Arrays;
import java.util.List;

public class TransactionProcessor {

  static final String QUEUE_IDENTIFIER = "TransactionQueue";

  private DocumentStoreClient<TransactionRequest> processedTransactions = ClientBuilder.getDocumentStoreClient(TransactionRequest.class);
  private DocumentStoreClient<Balance> balances = ClientBuilder.getDocumentStoreClient(Balance.class);
  private TransactionalClient transactionalClient = ClientBuilder.getTransactionalClient();

  @QueueServerlessFunction(id = QUEUE_IDENTIFIER, batchSize = 1)
  @UsesDocumentStore(dataModel = TransactionRequest.class)
  @UsesDocumentStore(dataModel = Balance.class)
  public void processTransaction(TransactionRequest transactionRequest) {
    TransactionRequest existingRequest = processedTransactions.get(transactionRequest.getTransactionUid());
    if (existingRequest != null) return;

    Balance senderBalance = balances.get(transactionRequest.getSender());
    Balance receiverBalance = balances.get(transactionRequest.getReceiver());

    // Sender does not have enough money
    if (senderBalance == null || senderBalance.getAmount() < transactionRequest.getAmount()) {
      processedTransactions.put(transactionRequest);
      return;
    }

    // Sender has enough money and exists
    if (receiverBalance == null) {
      receiverBalance = new Balance(transactionRequest.getReceiver(), transactionRequest.getAmount());
    } else {
      receiverBalance.setAmount(receiverBalance.getAmount() + transactionRequest.getAmount());
    }
    senderBalance.setAmount(senderBalance.getAmount() - transactionRequest.getAmount());

    List<WriteItemRequest> writes = Arrays.asList(
        balances.getWriteItem(receiverBalance),
        balances.getWriteItem(senderBalance),
        processedTransactions.getWriteItem(transactionRequest));

    transactionalClient.executeWriteTransaction(writes);
  }

  @AfterDeployment
  @UsesDocumentStore(dataModel = Balance.class)
  public void createTestUser() {
    balances.put(new Balance("test", 10000f));
  }

}
