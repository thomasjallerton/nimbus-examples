package com.nimbusframework.nimbusexampleidempotenttransaction;

import com.nimbusframework.nimbuscore.annotations.function.DocumentStoreServerlessFunction;
import com.nimbusframework.nimbuscore.annotations.notification.UsesNotificationTopic;
import com.nimbusframework.nimbuscore.annotations.persistent.StoreEventType;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;

public class NotificationApis {

  private static final String TOPIC_IDENTIFIER = "LowBalanceNotification";

  @DocumentStoreServerlessFunction(dataModel = Balance.class, method = StoreEventType.MODIFY)
  @UsesNotificationTopic(topic = TOPIC_IDENTIFIER) //TODO: I want to make notifications more tailored to individuals
  public void notifyOfLowBalance(Balance balance) {
    if (balance.getAmount() < 10) {
      ClientBuilder.getNotificationClient(TOPIC_IDENTIFIER).notify("User " + balance.getUser() + " has a low balance of " + balance.getAmount());
    }
  }

}
