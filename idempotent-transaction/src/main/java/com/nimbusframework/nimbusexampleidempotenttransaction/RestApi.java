package com.nimbusframework.nimbusexampleidempotenttransaction;

import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.annotations.queue.UsesQueue;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.queue.QueueClient;
import com.nimbusframework.nimbuscore.eventabstractions.HttpEvent;

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
  public Balance getBalance(HttpEvent event) {
    String user = event.getPathParameters().get("user");
    return ClientBuilder.getDocumentStoreClient(Balance.class).get(user);
  }

}
