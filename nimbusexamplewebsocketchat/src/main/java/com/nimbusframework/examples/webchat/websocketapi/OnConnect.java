package com.nimbusframework.examples.webchat.websocketapi;

import com.nimbusframework.examples.webchat.models.ConnectionDetail;
import com.nimbusframework.examples.webchat.models.UserDetail;
import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.WebSocketServerlessFunction;
import com.nimbusframework.nimbuscore.annotations.keyvalue.UsesKeyValueStore;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.clients.keyvalue.KeyValueStoreClient;
import com.nimbusframework.nimbuscore.eventabstractions.WebSocketEvent;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

public class OnConnect {

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
}
