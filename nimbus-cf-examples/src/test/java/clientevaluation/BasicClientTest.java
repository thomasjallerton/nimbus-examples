package clientevaluation;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.ServerlessMethod;
import functions.BasicFunction;
import org.junit.jupiter.api.Test;

class BasicClientTest {

    @Test
    public void basicFunctionClient() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(BasicFunction.class, BasicClient.class);
        ServerlessMethod invokedMethod = localNimbusDeployment.getMethod(BasicFunction.class, "basicFunction");

        localNimbusDeployment.getBasicFunction(BasicClient.class, "basicClientEvaluation").invoke();

        assert invokedMethod.getTimesInvoked() == 2;
    }
}