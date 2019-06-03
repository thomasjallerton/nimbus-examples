package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.ServerlessMethod;
import com.nimbusframework.nimbuscore.testing.http.HttpRequest;
import models.DataModel;
import org.junit.jupiter.api.Test;

class HttpFunctionTest {

    @Test
    public void invokeFileStorageFunction() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(HttpFunction.class);
        ServerlessMethod method = localNimbusDeployment.getMethod(HttpFunction.class, "httpFunction");

        HttpRequest request = new HttpRequest("evaluation", HttpMethod.POST);
        request.setBodyFromObject(new DataModel("test", "test"));

        localNimbusDeployment.sendHttpRequest(request);

        assert method.getTimesInvoked() == 1;
    }

}