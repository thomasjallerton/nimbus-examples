package com.nimbusframework.examples.webchat.endpoints;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import org.junit.jupiter.api.Test;

public class WebSocketApiTest {

    @Test
    public void localWebChat() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance("com.nimbusframework.examples.webchat");
        localNimbusDeployment.startAllServers();
    }
}
