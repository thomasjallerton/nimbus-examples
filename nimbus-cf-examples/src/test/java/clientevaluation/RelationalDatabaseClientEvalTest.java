package clientevaluation;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.ServerlessMethod;
import com.nimbusframework.nimbuscore.testing.queue.LocalQueue;
import functions.QueueFunction;
import models.DataModel;
import org.junit.jupiter.api.Test;

class RelationalDatabaseClientEvalTest {

    @Test
    public void queueClient() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(QueueFunction.class, QueueClientEval.class);
        ServerlessMethod method = localNimbusDeployment.getMethod(QueueFunction.class, "queueFunction");

        LocalQueue queue = localNimbusDeployment.getQueue("evaluation");

        localNimbusDeployment.getBasicFunction(QueueClientEval.class, "evaluateQueueClient").invoke();

        assert queue.getNumberOfItemsAdded() == 1;
        assert queue.itemsAddedContains(new DataModel("test", "test"));
        assert method.getTimesInvoked() == 1;
    }
}