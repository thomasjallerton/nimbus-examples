package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.QueueServerlessFunction;
import com.nimbusframework.nimbuscore.wrappers.queue.models.QueueEvent;
import models.DataModel;
import models.ResponseModel;

public class QueueFunction {
    @QueueServerlessFunction(id = "evaluation", batchSize = 1)
    public ResponseModel queueFunction(DataModel model, QueueEvent event)
    {
        assert model.equals(new DataModel("test", "test"));
        return new ResponseModel(model.getUsername(), model.getFullName(),true);
    }


    @QueueServerlessFunction(id = "evaluationbatch", batchSize = 5)
    public ResponseModel queueFunctionBatched(DataModel model, QueueEvent event)
    {
        assert model.equals(new DataModel("test", "test"));
        return new ResponseModel(model.getUsername(), model.getFullName(),true);
    }
}
