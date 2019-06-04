package clientevaluation;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import com.nimbusframework.nimbuscore.annotation.annotations.queue.UsesQueue;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.queue.QueueClient;
import models.DataModel;

public class QueueClientEval {

    @BasicServerlessFunction
    @UsesQueue(id = "evaluation")
    public void evaluateQueueClient() {
        long startTime = System.nanoTime();

        QueueClient client = ClientBuilder.getQueueClient("evaluation");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        client.sendMessageAsJson(new DataModel("test", "test"));

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("ADD: " + duration);

    }

    @BasicServerlessFunction
    @UsesQueue(id = "evaluation")
    public void evaluateAWSQueueClient() {
        long startTime = System.nanoTime();

        AmazonSQS client = AmazonSQSClientBuilder.defaultClient();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();


        client.sendMessage("https://sqs.eu-west-1.amazonaws.com/175020273353/evaluationdev", "Hello World!!");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("ADD: " + duration);
    }
}
