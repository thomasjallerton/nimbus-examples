package com.nimbusframework.nimbusexampleidempotenttransaction;

import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.annotations.queue.UsesQueue;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.clients.queue.QueueClient;
import com.nimbusframework.nimbuscore.clients.store.ReadItemRequest;
import com.nimbusframework.nimbuscore.eventabstractions.HttpEvent;
import com.nimbusframework.nimbuscore.exceptions.NonRetryableException;
import com.nimbusframework.nimbuscore.exceptions.RetryableException;
import java.util.LinkedList;
import java.util.List;

public class RestApi {

  @HttpServerlessFunction(path = "transactions", method = HttpMethod.POST)
  @UsesQueue(id = TransactionProcessor.QUEUE_IDENTIFIER)
  public String requestTransaction(TransactionRequest request) {
    QueueClient queueClient = ClientBuilder.getQueueClient(TransactionProcessor.QUEUE_IDENTIFIER);
    try {
      queueClient.sendMessageAsJson(request);
      return request.getTransactionUid().toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "processing error";
    }
  }


  @HttpServerlessFunction(path = "balances/{user}", method = HttpMethod.GET)
  @UsesDocumentStore(dataModel = Balance.class)
  public Balance getBalance(HttpEvent event) throws NonRetryableException, RetryableException {
    String user = event.getPathParameters().get("user");
    DocumentStoreClient<Balance> balanceClient = ClientBuilder.getDocumentStoreClient(Balance.class);
    List<ReadItemRequest<Balance>> reads = new LinkedList<>();
    reads.add(balanceClient.getReadItem(user));
    return (Balance) ClientBuilder.getTransactionalClient().executeReadTransaction(reads).get(0);
  }

}
