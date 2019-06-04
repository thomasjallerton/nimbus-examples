package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.QueueServerlessFunction;
import com.nimbusframework.nimbuscore.wrappers.queue.models.QueueEvent;
import models.DataModel;
import models.ResponseModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueueFunction {
    @QueueServerlessFunction(id = "evaluation", batchSize = 1)
    public ResponseModel queueFunction(DataModel model, QueueEvent event)
    {
        assertEquals(model, new DataModel("test", "test"));
        return new ResponseModel(model.getUsername(), model.getFullName(),true);
    }
//
//
//    @QueueServerlessFunction(id = "evaluationbatch", batchSize = 5)
//    public ResponseModel queueFunctionBatched(DataModel model, QueueEvent event)
//    {
//        assertEquals(model, new DataModel("test", "test"));
//        return new ResponseModel(model.getUsername(), model.getFullName(),true);
//    }
}
