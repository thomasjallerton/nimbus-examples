package com.nimbusframework.examples.webchat.websocketapi;

import com.nimbusframework.examples.webchat.models.UserDetail;
import com.nimbusframework.nimbuscore.annotations.deployment.AfterDeployment;
import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;

public class DevEnvironment {
    private DocumentStoreClient<UserDetail> userDetails = ClientBuilder.getDocumentStoreClient(UserDetail.class);


    @AfterDeployment(stages = {DEV_STAGE})
    @UsesDocumentStore(dataModel = UserDetail.class, stages = {DEV_STAGE})
    public String setupBasicUsers() {
        UserDetail user1 = new UserDetail("user1", null);
        UserDetail user2 = new UserDetail("user2", null);

        userDetails.deleteKey("thomas");
        userDetails.put(user1);
        userDetails.put(user2);
        return "Created test users";
    }
}
