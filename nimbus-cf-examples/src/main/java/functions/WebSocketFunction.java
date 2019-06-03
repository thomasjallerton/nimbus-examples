package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.WebSocketServerlessFunction;
import com.nimbusframework.nimbuscore.wrappers.websocket.models.WebSocketEvent;
import models.DataModel;
import models.ResponseModel;

public class WebSocketFunction {

    @WebSocketServerlessFunction(topic = "evaluation")
    public ResponseModel webSocketFunction(DataModel model, WebSocketEvent webSocketEvent)
    {
        assert model.equals(new DataModel("test", "test"));
        return new ResponseModel(model.getUsername(), model.getFullName(),true);
    }

    @WebSocketServerlessFunction(topic = "$connect")
    public ResponseModel onConnect(WebSocketEvent webSocketEvent)
    {
        return new ResponseModel(true);
    }

    @WebSocketServerlessFunction(topic = "$disconnect")
    public ResponseModel onDisconnect(WebSocketEvent webSocketEvent)
    {
        return new ResponseModel(true);
    }

}
