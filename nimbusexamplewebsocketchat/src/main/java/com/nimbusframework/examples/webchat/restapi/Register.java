package com.nimbusframework.examples.webchat.restapi;

import com.nimbusframework.examples.webchat.models.UserDetail;
import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.exceptions.NonRetryableException;
import com.nimbusframework.nimbuscore.exceptions.RetryableException;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

public class Register {

    @HttpServerlessFunction(method = HttpMethod.POST, path = "register", stages = {DEV_STAGE, PRODUCTION_STAGE}, allowedCorsOrigin = "${WEBCHATNIMBUS_URL}")
    @UsesDocumentStore(dataModel = UserDetail.class)
    public void register(String username) {
        DocumentStoreClient<UserDetail> userDetails = ClientBuilder.getDocumentStoreClient(UserDetail.class);
        userDetails.put(new UserDetail(username, null));
    }
}
