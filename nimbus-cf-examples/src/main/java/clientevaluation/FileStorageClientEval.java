package clientevaluation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.nimbusframework.nimbuscore.annotation.annotations.file.UsesFileStorageClient;
import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.file.FileStorageClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileStorageClientEval {


    @BasicServerlessFunction
    @UsesFileStorageClient(bucketName = "NimbusEvaluationBucket")
    public void evaluateFSClient() throws IOException {
        long startTime = System.nanoTime();

        FileStorageClient client = ClientBuilder.getFileStorageClient("NimbusEvaluationBucket");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        client.saveFile("evaluation.txt", "THIS IS GOING TO BE THE CONTENTS OF THE FILE");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("SAVE: " + duration);

        startTime = System.nanoTime();

        InputStream file = client.getFile("evaluation.txt");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        String fileContents = stringBuilder.toString();
        assertEquals(fileContents, "THIS IS GOING TO BE THE CONTENTS OF THE FILE");

        System.out.println("READ: " + duration);

        startTime = System.nanoTime();

        client.deleteFile("evaluation.txt");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("DELETE: " + duration);
    }

    @BasicServerlessFunction
    @UsesFileStorageClient(bucketName = "NimbusEvaluationBucket")
    public void evaluateAWSFSClient() {
        long startTime = System.nanoTime();

        AmazonS3 client = AmazonS3ClientBuilder.defaultClient();
        String bucketName = "nimbusevaluationbucketdev";

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        client.putObject(bucketName, "evaluation.txt", "THIS IS GOING TO BE THE CONTENTS OF THE FILE");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("SAVE: " + duration);

        startTime = System.nanoTime();

        InputStream file = client.getObject(bucketName, "evaluation.txt").getObjectContent();

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("READ: " + duration);

        startTime = System.nanoTime();

        client.deleteObject(bucketName, "evaluation.txt");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("DELETE: " + duration);
    }
}
