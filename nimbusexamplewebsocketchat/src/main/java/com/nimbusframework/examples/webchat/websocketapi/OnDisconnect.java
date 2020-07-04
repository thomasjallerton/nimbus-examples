package com.nimbusframework.examples.webchat.websocketapi;

import com.nimbusframework.examples.webchat.models.ConnectionDetail;
import com.nimbusframework.examples.webchat.models.UserDetail;
import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.WebSocketServerlessFunction;
import com.nimbusframework.nimbuscore.annotations.keyvalue.UsesKeyValueStore;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.clients.keyvalue.KeyValueStoreClient;
import com.nimbusframework.nimbuscore.clients.websocket.ServerlessFunctionWebSocketClient;
import com.nimbusframework.nimbuscore.eventabstractions.WebSocketEvent;
import com.nimbusframework.nimbuscore.exceptions.NonRetryableException;
import com.nimbusframework.nimbuscore.exceptions.RetryableException;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

public class OnDisconnect {

    private ServerlessFunctionWebSocketClient webSocketClient = ClientBuilder.getServerlessFunctionWebSocketClient();
    private DocumentStoreClient<UserDetail> userDetails = ClientBuilder.getDocumentStoreClient(UserDetail.class);
    private KeyValueStoreClient<String, ConnectionDetail> connectionDetails = ClientBuilder.getKeyValueStoreClient(String.class, ConnectionDetail.class);

    @WebSocketServerlessFunction(topic = "$disconnect")
    @UsesDocumentStore(dataModel = UserDetail.class)
    @UsesKeyValueStore(dataModel = ConnectionDetail.class)
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

}
