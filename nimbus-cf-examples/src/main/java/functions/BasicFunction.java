package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import models.DataModel;
import models.ResponseModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicFunction {

    @BasicServerlessFunction()
    public ResponseModel basicFunction(DataModel model) {
        assertEquals(model, new DataModel("test", "test"));
        return new ResponseModel(model.getUsername(), model.getFullName(),true);
    }
}
