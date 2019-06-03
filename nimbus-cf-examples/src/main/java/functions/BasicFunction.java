package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import models.DataModel;
import models.ResponseModel;

public class BasicFunction {

    @BasicServerlessFunction()
    public ResponseModel basicFunction(DataModel model)
    {
        assert model.equals(new DataModel("test", "test"));
        return new ResponseModel(model.getUsername(), model.getFullName(),true);
    }
}
