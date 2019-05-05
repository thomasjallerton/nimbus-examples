package com.nimbusframework.examples.webchat.endpoints;


import com.nimbusframework.examples.webchat.models.ConnectionDetail;
import com.nimbusframework.examples.webchat.models.Message;
import com.nimbusframework.examples.webchat.models.UserDetail;
import com.nimbusframework.examples.webchat.models.WebSocketMessage;
import com.nimbusframework.nimbuscore.annotation.annotations.deployment.AfterDeployment;
import com.nimbusframework.nimbuscore.annotation.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotation.annotations.function.EnvironmentVariable;
import com.nimbusframework.nimbuscore.annotation.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotation.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.annotation.annotations.function.WebSocketServerlessFunction;
import com.nimbusframework.nimbuscore.annotation.annotations.keyvalue.UsesKeyValueStore;
import com.nimbusframework.nimbuscore.annotation.annotations.websocket.UsesServerlessFunctionWebSocketClient;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.clients.keyvalue.KeyValueStoreClient;
import com.nimbusframework.nimbuscore.clients.websocket.ServerlessFunctionWebSocketClient;
import com.nimbusframework.nimbuscore.wrappers.websocket.models.WebSocketEvent;

import java.util.List;
import java.util.Map;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

public class WebchatApi {

    private ServerlessFunctionWebSocketClient webSocketClient = ClientBuilder.getServerlessFunctionWebSocketClient();
    private DocumentStoreClient<UserDetail> userDetails = ClientBuilder.getDocumentStoreClient(UserDetail.class);
    private KeyValueStoreClient<String, ConnectionDetail> connectionDetails = ClientBuilder.getKeyValueStoreClient(String.class, ConnectionDetail.class);

    @WebSocketServerlessFunction(topic = "$connect", stages = {DEV_STAGE, PRODUCTION_STAGE})
    @UsesDocumentStore(dataModel = UserDetail.class, stages = {DEV_STAGE, PRODUCTION_STAGE})
    @UsesKeyValueStore(dataModel = ConnectionDetail.class, stages = {DEV_STAGE, PRODUCTION_STAGE})
    public void onConnect(WebSocketEvent event) throws Exception {
        String connectionId = event.getRequestContext().getConnectionId();
        String username = event.getQueryStringParameters().get("user");
        UserDetail validUser = userDetails.get(username);
        if (validUser != null) {
            ConnectionDetail connectionDetail = new ConnectionDetail(username);
            System.out.println("Adding " + connectionDetail.getUsername() + " with connection " + connectionId);
            connectionDetails.put(connectionId, connectionDetail);
            if (validUser.getCurrentWebsocket() != null) {
                connectionDetails.delete(validUser.getCurrentWebsocket());
            }
            validUser.setCurrentWebsocket(connectionId);
            userDetails.put(validUser);
        } else {
            throw new Exception("Not a valid user");
        }
    }

    @WebSocketServerlessFunction(topic = "$disconnect", stages = {DEV_STAGE, PRODUCTION_STAGE})
    @UsesDocumentStore(dataModel = UserDetail.class, stages = {DEV_STAGE, PRODUCTION_STAGE})
    @UsesKeyValueStore(dataModel = ConnectionDetail.class, stages = {DEV_STAGE, PRODUCTION_STAGE})
    public void onDisconnect(WebSocketEvent event) {
        String connectionId = event.getRequestContext().getConnectionId();
        ConnectionDetail disconnectedUser = connectionDetails.get(connectionId);
        if (disconnectedUser != null) {
            UserDetail validUser = userDetails.get(disconnectedUser.getUsername());
            if (validUser != null) {
                validUser.setCurrentWebsocket(null);
                userDetails.put(validUser);
            }
        }
        connectionDetails.delete(event.getRequestContext().getConnectionId());
    }

    @WebSocketServerlessFunction(topic = "sendMessage", stages = {DEV_STAGE, PRODUCTION_STAGE})
    @UsesServerlessFunctionWebSocketClient(stages = {DEV_STAGE, PRODUCTION_STAGE})
    @UsesDocumentStore(dataModel = UserDetail.class, stages = {DEV_STAGE, PRODUCTION_STAGE})
    @UsesKeyValueStore(dataModel = ConnectionDetail.class, stages = {DEV_STAGE, PRODUCTION_STAGE})
    public void onMessage(WebSocketMessage message, WebSocketEvent event) {
        UserDetail userDetail = userDetails.get(message.getRecipient());
        ConnectionDetail connectionDetail = connectionDetails.get(event.getRequestContext().getConnectionId());
        if (userDetail != null && connectionDetail != null) {
            System.out.println();
            webSocketClient.sendToConnectionConvertToJson(userDetail.getCurrentWebsocket(),
                    new Message(message.getMessage(), connectionDetail.getUsername()));
        }
    }

    @HttpServerlessFunction(method = HttpMethod.POST, path = "register", stages = {DEV_STAGE, PRODUCTION_STAGE}, allowedCorsOrigin = "${WEBCHATNIMBUS_URL}")
    @UsesDocumentStore(dataModel = UserDetail.class, stages = {DEV_STAGE, PRODUCTION_STAGE})
    public void register(String username) {
        userDetails.put(new UserDetail(username, null));
    }

    //For development stage - Create users and endpoints to see contents of data stores

    @AfterDeployment(stages = {DEV_STAGE})
    @UsesDocumentStore(dataModel = UserDetail.class, stages = {DEV_STAGE})
    public String setupBasicUsers() {
        UserDetail user1 = new UserDetail("user1", null);
        UserDetail user2 = new UserDetail("user2", null);

        userDetails.put(user1);
        userDetails.put(user2);
        return "Created test users";
    }

    @HttpServerlessFunction(path = "UserDetails", method = HttpMethod.GET, stages = {DEV_STAGE})
    @UsesDocumentStore(dataModel = UserDetail.class, stages = {DEV_STAGE})
    public List<UserDetail> getUserDetails() {
        return userDetails.getAll();
    }

    @HttpServerlessFunction(path = "ConnectionDetails", method = HttpMethod.GET, stages = {DEV_STAGE})
    @UsesKeyValueStore(dataModel = ConnectionDetail.class, stages = {DEV_STAGE})
    public Map<String, ConnectionDetail> getConnectionDetail() {
        return connectionDetails.getAll();
    }
}
