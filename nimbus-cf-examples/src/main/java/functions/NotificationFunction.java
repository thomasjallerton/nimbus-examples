package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.NotificationServerlessFunction;
import com.nimbusframework.nimbuscore.wrappers.notification.models.NotificationEvent;
import models.DataModel;
import models.ResponseModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationFunction {
    @NotificationServerlessFunction(topic = "evaluation")
    public ResponseModel notificationFunction(DataModel model, NotificationEvent notificationEvent)
    {
        assertEquals(model, new DataModel("test", "test"));
        return new ResponseModel(model.getUsername(), model.getFullName(),true);
    }
}
