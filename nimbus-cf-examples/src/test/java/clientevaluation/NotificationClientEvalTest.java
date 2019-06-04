package clientevaluation;

import com.nimbusframework.nimbuscore.clients.notification.Protocol;
import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.notification.LocalNotificationTopic;
import org.junit.jupiter.api.Test;

class NotificationClientEvalTest {

    @Test
    public void notificationClient() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(NotificationClientEval.class);

        LocalNotificationTopic topic = localNimbusDeployment.getNotificationTopic("nimbusevaluationtopic");
        assert topic.getNumberOfSubscribers() == 0;

        localNimbusDeployment.getBasicFunction(NotificationClientEval.class, "evaluateNotificationClient").invoke();

        assert topic.getEndpointsMessages(Protocol.SMS, "+447872646190").contains("NIMBUS NOTIFY");
        assert topic.getNumberOfSubscribers() == 0;
    }

}