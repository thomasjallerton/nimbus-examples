package com.nimbusframework.examples.webchat.websocketapi;

import com.nimbusframework.examples.webchat.models.ConnectionDetail;
import com.nimbusframework.examples.webchat.models.Message;
import com.nimbusframework.examples.webchat.models.UserDetail;
import com.nimbusframework.examples.webchat.models.WebSocketMessage;
import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.WebSocketServerlessFunction;
import com.nimbusframework.nimbuscore.annotations.keyvalue.UsesKeyValueStore;
import com.nimbusframework.nimbuscore.annotations.websocket.UsesServerlessFunctionWebSocket;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.clients.keyvalue.KeyValueStoreClient;
import com.nimbusframework.nimbuscore.clients.websocket.ServerlessFunctionWebSocketClient;
import com.nimbusframework.nimbuscore.eventabstractions.WebSocketEvent;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

public class SendMessage {

    private ServerlessFunctionWebSocketClient webSocketClient = ClientBuilder.getServerlessFunctionWebSocketClient();
    private DocumentStoreClient<UserDetail> userDetails = ClientBuilder.getDocumentStoreClient(UserDetail.class);
    private KeyValueStoreClient<String, ConnectionDetail> connectionDetails = ClientBuilder.getKeyValueStoreClient(String.class, ConnectionDetail.class);

    @WebSocketServerlessFunction(topic = "sendMessage", stages = {DEV_STAGE, PRODUCTION_STAGE})
    @UsesServerlessFunctionWebSocket(stages = {DEV_STAGE, PRODUCTION_STAGE})
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
}
