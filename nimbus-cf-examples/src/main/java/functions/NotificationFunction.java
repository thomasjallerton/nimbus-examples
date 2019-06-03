package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.NotificationServerlessFunction;
import com.nimbusframework.nimbuscore.wrappers.notification.models.NotificationEvent;
import models.DataModel;
import models.ResponseModel;

public class NotificationFunction {
    @NotificationServerlessFunction(topic = "evaluation")
    public ResponseModel notificationFunction(DataModel model, NotificationEvent notificationEvent)
    {
        assert model.equals(new DataModel("test", "test"));
        return new ResponseModel(model.getUsername(), model.getFullName(),true);
    }
}
