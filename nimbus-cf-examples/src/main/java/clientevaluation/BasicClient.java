package clientevaluation;

import com.amazonaws.Response;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import com.nimbusframework.nimbuscore.annotation.annotations.function.UsesBasicServerlessFunctionClient;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.function.BasicServerlessFunctionClient;
import functions.BasicFunction;
import models.DataModel;
import models.ResponseModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicClient {

    @BasicServerlessFunction
    @UsesBasicServerlessFunctionClient(targetClass = BasicFunction.class, methodName = "basicFunction")
    public void basicClientEvaluation() {
        long startTime = System.nanoTime();

        BasicServerlessFunctionClient client = ClientBuilder.getBasicServerlessFunctionClient(BasicFunction.class, "basicFunction");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();
        ResponseModel model = client.invoke(
                new DataModel("test", "test"),
                ResponseModel.class);
        endTime = System.nanoTime();
        duration = (endTime - startTime);

        assertEquals(model, new ResponseModel("test", "test", true));
        System.out.println("SYNC: " + duration);

        startTime = System.nanoTime();

        client.invokeAsync(new DataModel("test", "test"));

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("ASYNC: " + duration);
    }

    @BasicServerlessFunction
    @UsesBasicServerlessFunctionClient(targetClass = BasicFunction.class, methodName = "basicFunction")
    public void awsClientEvaluation() {
        long startTime = System.nanoTime();

        AWSLambda client = AWSLambdaClientBuilder.defaultClient();
        ObjectMapper objectMapper = new ObjectMapper();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();
        try {
            InvokeRequest request = new InvokeRequest()
                    .withFunctionName("cfexamples-dev-BasicFunction-basicFunction")
                    .withPayload(objectMapper.writeValueAsString(new DataModel("test", "test")))
                    .withInvocationType(InvocationType.RequestResponse);
            InvokeResult result = client.invoke(request);
            ResponseModel data = objectMapper.readValue(result.getPayload().array(), ResponseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("SYNC: " + duration);

        startTime = System.nanoTime();

        try {
            InvokeRequest request = new InvokeRequest()
                    .withFunctionName("cfexamples-dev-BasicFunction-basicFunction")
                    .withPayload(objectMapper.writeValueAsString(new DataModel("test", "test")))
                    .withInvocationType(InvocationType.Event);
            InvokeResult result = client.invoke(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("ASYNC: " + duration);
    }
}
