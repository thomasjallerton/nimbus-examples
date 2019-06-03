package clientevaluation;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.file.LocalFileStorage;
import org.junit.jupiter.api.Test;

class FileStorageClientEvalTest {

    @Test
    public void fileStorageClient() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(FileStorageClientEval.class);

        LocalFileStorage store = localNimbusDeployment.getLocalFileStorage("NimbusEvaluationBucket");
        assert store.listFiles().size() == 0;

        localNimbusDeployment.getBasicFunction(FileStorageClientEval.class, "evaluateFSClient").invoke();

        assert store.listFiles().size() == 0;
    }

}