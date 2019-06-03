package functions;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.ServerlessMethod;
import com.nimbusframework.nimbuscore.testing.document.LocalDocumentStore;
import models.DataModel;
import org.junit.jupiter.api.Test;

class DynamoFunctionTest {

    @Test
    public void invokeDynamoFunction() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(DataModel.class, DynamoFunction.class);
        ServerlessMethod method = localNimbusDeployment.getMethod(DynamoFunction.class, "dynamoFunction");

        LocalDocumentStore<DataModel> documentStore = localNimbusDeployment.getDocumentStore(DataModel.class);
        documentStore.put(new DataModel("test", "test"));

        assert method.getTimesInvoked() == 1;
    }

}