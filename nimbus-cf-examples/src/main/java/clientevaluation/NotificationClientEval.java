package clientevaluation;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import com.nimbusframework.nimbuscore.annotation.annotations.notification.UsesNotificationTopic;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.notification.NotificationClient;
import com.nimbusframework.nimbuscore.clients.notification.Protocol;

public class NotificationClientEval {

    @BasicServerlessFunction
    @UsesNotificationTopic(topic = "nimbusevaluationtopic")
    public void evaluateNotificationClient() {
        long startTime = System.nanoTime();

        NotificationClient client = ClientBuilder.getNotificationClient("nimbusevaluationtopic");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);


        startTime = System.nanoTime();

        String id = client.createSubscription(Protocol.SMS, "+447872646190");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("ADD: " + duration);

        startTime = System.nanoTime();

        client.notify("NIMBUS NOTIFY");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("NOTIFY: " + duration);

        startTime = System.nanoTime();

        client.deleteSubscription(id);

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("REMOVE: " + duration);
    }

    @BasicServerlessFunction
    @UsesNotificationTopic(topic = "nimbusevaluationtopic")
    public void evaluateAWSNotificationClient() {
        long startTime = System.nanoTime();

        AmazonSNS client = AmazonSNSClientBuilder.defaultClient();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        SubscribeResult subscribeResult = client.subscribe("arn:aws:sns:eu-west-1:175020273353:nimbusevaluationtopicdev", "SMS", "+447872646190");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("ADD: " + duration);

        startTime = System.nanoTime();

        client.publish("arn:aws:sns:eu-west-1:175020273353:nimbusevaluationtopicdev", "AWS NOTIFY");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("NOTIFY: " + duration);

        startTime = System.nanoTime();

        client.unsubscribe(subscribeResult.getSubscriptionArn());

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("REMOVE: " + duration);
    }
}
