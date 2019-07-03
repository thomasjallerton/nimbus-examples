package com.nimbusframework.examples.webchat.restapi;

import com.nimbusframework.examples.webchat.models.UserDetail;
import com.nimbusframework.nimbuscore.annotation.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotation.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotation.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

public class Register {

    @HttpServerlessFunction(method = HttpMethod.POST, path = "register",
            stages = {DEV_STAGE, PRODUCTION_STAGE}, allowedCorsOrigin = "${WEBCHATNIMBUS_URL}")
    @UsesDocumentStore(dataModel = UserDetail.class, stages = {DEV_STAGE, PRODUCTION_STAGE})
    public void register(String username) {
        DocumentStoreClient<UserDetail> userDetails = ClientBuilder
                .getDocumentStoreClient(UserDetail.class);
        userDetails.put(new UserDetail(username, null));
    }
}
