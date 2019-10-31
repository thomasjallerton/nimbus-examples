package com.nimbusframework.nimbusexamplegithubwebhook;

import com.nimbusframework.nimbuscore.annotations.deployment.AfterDeployment;
import com.nimbusframework.nimbuscore.annotations.function.EnvironmentVariable;
import com.nimbusframework.nimbuscore.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.annotations.notification.UsesNotificationTopic;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.function.EnvironmentVariableClient;
import com.nimbusframework.nimbuscore.clients.notification.Protocol;

public class WebHookHandler {

  @HttpServerlessFunction(path = "stars", method = HttpMethod.POST)
  @UsesNotificationTopic(topic = "GitHubUpdates")
  public void handleWebHook() {
    ClientBuilder.getNotificationClient("GitHubUpdates")
        .notify("There has been a change to the number of stars of the GitHub repository");
  }

  @AfterDeployment
  @UsesNotificationTopic(topic = "GitHubUpdates")
  @EnvironmentVariable(key="subscriber", value="${SUBSCRIBER}")
  public void addSubscriber() {
    EnvironmentVariableClient environmentVariableClient = ClientBuilder.getEnvironmentVariableClient();
    String subscriber = environmentVariableClient.get("subscriber");
    if (subscriber != null) {
      ClientBuilder.getNotificationClient("GitHubUpdates")
          .createSubscription(Protocol.SMS, subscriber);
    }
  }
}
