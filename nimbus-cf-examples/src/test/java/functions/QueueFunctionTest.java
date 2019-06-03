package functions;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.ServerlessMethod;
import com.nimbusframework.nimbuscore.testing.queue.LocalQueue;
import models.DataModel;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class QueueFunctionTest {

    @Test
    public void invokeQueueFunction() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(QueueFunction.class);
        ServerlessMethod method = localNimbusDeployment.getMethod(QueueFunction.class, "queueFunction");

        LocalQueue queue = localNimbusDeployment.getQueue("evaluation");
        queue.add(new DataModel("test", "test"));

        assert method.getTimesInvoked() == 1;
    }

    @Test
    public void invokeBatchQueueFunctionOneItem() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(QueueFunction.class);
        ServerlessMethod method = localNimbusDeployment.getMethod(QueueFunction.class, "queueFunctionBatched");

        LocalQueue queue = localNimbusDeployment.getQueue("evaluationbatch");
        queue.add(new DataModel("test", "test"));

        assert method.getTimesInvoked() == 1;
    }

    @Test
    public void invokeBatchQueueFunctionMultipleItems() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(QueueFunction.class);
        ServerlessMethod method = localNimbusDeployment.getMethod(QueueFunction.class, "queueFunctionBatched");

        LocalQueue queue = localNimbusDeployment.getQueue("evaluationbatch");
        List<DataModel> toAdd = new LinkedList<>();
        toAdd.add(new DataModel("test", "test"));
        toAdd.add(new DataModel("test", "test"));

        queue.addBatch(toAdd);

        assert method.getTimesInvoked() == 1;
    }

}