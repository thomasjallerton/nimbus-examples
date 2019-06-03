package clientevaluation;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.document.LocalDocumentStore;
import models.DataModel;
import org.junit.jupiter.api.Test;

class DynamoClientTest {

    @Test
    public void dynamoClient() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(DynamoClient.class, DataModel.class);

        LocalDocumentStore store = localNimbusDeployment.getDocumentStore(DataModel.class);
        assert store.size() == 0;

        localNimbusDeployment.getBasicFunction(DynamoClient.class, "evaluateDynamoClient").invoke();

        assert store.size() == 0;
    }

}