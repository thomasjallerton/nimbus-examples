package com.nimbusframework.examples.webchat.endpoints;

import com.nimbusframework.examples.webchat.models.ConnectionDetail;
import com.nimbusframework.examples.webchat.websocketapi.OnConnect;
import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.ServerlessMethod;
import com.nimbusframework.nimbuscore.testing.keyvalue.LocalKeyValueStore;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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
}
