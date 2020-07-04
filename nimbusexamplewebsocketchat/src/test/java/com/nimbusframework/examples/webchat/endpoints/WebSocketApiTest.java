package com.nimbusframework.examples.webchat.endpoints;

import com.nimbusframework.examples.webchat.models.ConnectionDetail;
import com.nimbusframework.examples.webchat.models.UserDetail;
import com.nimbusframework.examples.webchat.websocketapi.OnConnect;
import com.nimbusframework.nimbuscore.annotations.function.HttpMethod;
import com.nimbusframework.nimbuslocal.LocalNimbusDeployment;
import com.nimbusframework.nimbuslocal.ServerlessMethod;
import com.nimbusframework.nimbuslocal.deployment.http.HttpRequest;
import com.nimbusframework.nimbuslocal.deployment.keyvalue.LocalKeyValueStore;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebSocketApiTest {

    @Test
    public void localWebChat() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance("com.nimbusframework.examples.webchat");
        localNimbusDeployment.startAllServers();
    }

    @Test
    public void onConnectAddsUserToConnectionDetail() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance("com.nimbusframework.examples.webchat");

        Map<String, String> queryStringParams = new HashMap<>();
        queryStringParams.put("user", "user1");

        Map<String, String> headers = new HashMap<>();

        localNimbusDeployment.connectToWebSockets(headers, queryStringParams);

        ServerlessMethod method = localNimbusDeployment.getMethod(OnConnect.class, "onConnect");
        assertEquals(1, method.getTimesInvoked());

        LocalKeyValueStore<String, ConnectionDetail> connectionDetailStore = localNimbusDeployment.getKeyValueStore(ConnectionDetail.class);
        assertEquals(1, connectionDetailStore.size());

        Map<String, ConnectionDetail> connectionDetails = connectionDetailStore.getAll();
        for (ConnectionDetail connectionDetail : connectionDetails.values()) {
            assertEquals("user1", connectionDetail.getUsername());
        }
    }

    @Test
    public void registeringAllowsYouToLogIn() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance("com.nimbusframework.examples.webchat");

        String newUser = "newUser";

        Map<String, String> queryStringParams = new HashMap<>();
        queryStringParams.put("user", "newUser");

        Map<String, String> headers = new HashMap<>();

        assertThatThrownBy(() -> localNimbusDeployment.connectToWebSockets(headers, queryStringParams))
            .isInstanceOf(Exception.class);

        HttpRequest httpRequest = new HttpRequest("register", HttpMethod.POST).withBodyFromObject(newUser);
        localNimbusDeployment.sendHttpRequest(httpRequest);

        localNimbusDeployment.connectToWebSockets(headers, queryStringParams);

        UserDetail newUserDetail = localNimbusDeployment.getDocumentStore(UserDetail.class).get(newUser);
        assertThat(newUserDetail).isNotNull();
        assertThat(newUserDetail.getUsername()).isNotNull();
    }
}
