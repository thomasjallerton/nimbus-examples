package com.nimbusframework.examples.webchat.restapi;

import com.nimbusframework.examples.webchat.models.ConnectionDetail;
import com.nimbusframework.examples.webchat.models.UserDetail;
import com.nimbusframework.nimbuscore.annotation.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotation.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotation.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.annotation.annotations.keyvalue.UsesKeyValueStore;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.clients.keyvalue.KeyValueStoreClient;

import java.util.List;
import java.util.Map;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;

public class Debugging {
    private DocumentStoreClient<UserDetail> userDetails = ClientBuilder.getDocumentStoreClient(UserDetail.class);
    private KeyValueStoreClient<String, ConnectionDetail> connectionDetails = ClientBuilder.getKeyValueStoreClient(String.class, ConnectionDetail.class);



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
