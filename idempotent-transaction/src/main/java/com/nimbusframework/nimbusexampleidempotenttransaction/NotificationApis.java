package com.nimbusframework.nimbusexampleidempotenttransaction;

import com.nimbusframework.nimbuscore.annotations.function.DocumentStoreServerlessFunction;
import com.nimbusframework.nimbuscore.annotations.notification.UsesNotificationTopic;
import com.nimbusframework.nimbuscore.annotations.persistent.StoreEventType;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;

public class NotificationApis {

  @DocumentStoreServerlessFunction(dataModel = Balance.class, method = StoreEventType.MODIFY)
  @UsesNotificationTopic(notificationTopic = LowBalanceNotification.class)
  public void notifyOfLowBalance(Balance balance) {
    if (balance.getAmount() < 10) {
      ClientBuilder.getNotificationClient(LowBalanceNotification.class).notify("User " + balance.getUser() + " has a low balance of " + balance.getAmount());
    }
  }

}
