package functions;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.ServerlessMethod;
import com.nimbusframework.nimbuscore.testing.notification.LocalNotificationTopic;
import models.DataModel;
import org.junit.jupiter.api.Test;

class NotificationFunctionTest {

    @Test
    public void invokeNotificationFunction() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(NotificationFunction.class);
        ServerlessMethod method = localNimbusDeployment.getMethod(NotificationFunction.class, "notificationFunction");

        LocalNotificationTopic notificationTopic = localNimbusDeployment.getNotificationTopic("evaluation");
        notificationTopic.notify(new DataModel("test", "test"));

        assert method.getTimesInvoked() == 1;
    }

}