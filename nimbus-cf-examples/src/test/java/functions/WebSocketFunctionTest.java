package functions;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.ServerlessMethod;
import com.nimbusframework.nimbuscore.testing.websocket.WebSocketRequest;
import models.DataModel;
import org.junit.jupiter.api.Test;

class WebSocketFunctionTest {

    @Test
    public void websocketConnect() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(WebSocketFunction.class);
        ServerlessMethod method = localNimbusDeployment.getMethod(WebSocketFunction.class, "webSocketFunction");

        WebSocketRequest request = new WebSocketRequest();
        request.setBodyWithTopic(new DataModel("test", "test"), "evaluation");

        localNimbusDeployment.sendWebSocketRequest(request);

        assert method.getTimesInvoked() == 1;
    }

}