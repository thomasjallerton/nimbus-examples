package clientevaluation;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApi;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiClientBuilder;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionRequest;
import com.nimbusframework.nimbuscore.annotation.annotations.function.WebSocketServerlessFunction;
import com.nimbusframework.nimbuscore.annotation.annotations.websocket.UsesServerlessFunctionWebSocketClient;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.websocket.ServerlessFunctionWebSocketClient;
import com.nimbusframework.nimbuscore.wrappers.websocket.models.WebSocketEvent;

import java.nio.ByteBuffer;

public class WebSocketClientEval {

    @WebSocketServerlessFunction(topic = "evaluationNimbus")
    @UsesServerlessFunctionWebSocketClient
    public void evalNimbusWebSocket(WebSocketEvent webSocketEvent)
    {
        long startTime = System.nanoTime();

        ServerlessFunctionWebSocketClient client = ClientBuilder.getServerlessFunctionWebSocketClient();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        client.sendToConnectionConvertToJson(
                webSocketEvent.getRequestContext().getConnectionId(),
                "HELLO WORLD");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("MESSAGE: " + duration);
    }

    @WebSocketServerlessFunction(topic = "evaluationAWS")
    @UsesServerlessFunctionWebSocketClient
    public void evalAWSWebSocket(WebSocketEvent webSocketEvent) {
        long startTime = System.nanoTime();

        AmazonApiGatewayManagementApi client = AmazonApiGatewayManagementApiClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "https://wc95ugfrlc.execute-api.eu-west-1.amazonaws.com/dev",
                                "eu-west-1"
                        )
                ).build();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        PostToConnectionRequest request = new PostToConnectionRequest()
                .withConnectionId(webSocketEvent.getRequestContext().getConnectionId())
                .withData(ByteBuffer.wrap("HELLO WORLD".getBytes()));

        client.postToConnection(request);

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("MESSAGE: " + duration);
    }
}
