package functions;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import models.DataModel;
import models.ResponseModel;
import org.junit.jupiter.api.Test;

class BasicFunctionTest {

    @Test
    public void invokeBasicFunction() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(BasicFunction.class);
        com.nimbusframework.nimbuscore.testing.basic.BasicFunction basicFunction = localNimbusDeployment.getBasicFunction(BasicFunction.class, "basicFunction");
        basicFunction.invoke(new DataModel("test", "test"));
        Object response = basicFunction.getMostRecentValueReturned();
        assert new ResponseModel("test", "test", true).equals(response);
    }
}