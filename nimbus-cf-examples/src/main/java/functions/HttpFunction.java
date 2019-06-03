package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotation.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.wrappers.http.models.HttpEvent;
import models.DataModel;
import models.ResponseModel;

public class HttpFunction {
    @HttpServerlessFunction(method = HttpMethod.POST, path = "evaluation")
    public ResponseModel httpFunction(DataModel model, HttpEvent httpEvent)
    {
        assert model.equals(new DataModel("test", "test"));
        return new ResponseModel(model.getUsername(), model.getFullName(),true);
    }
}
