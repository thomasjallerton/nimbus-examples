package clientevaluation;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import org.junit.jupiter.api.Test;

class EnvVariableClientTest {

    @Test
    public void envClientTest() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(EnvVariableClient.class);

        localNimbusDeployment.getBasicFunction(EnvVariableClient.class, "evaluateEnvClient").invoke();
    }
}