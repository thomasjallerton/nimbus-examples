package functions;

import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import com.nimbusframework.nimbuscore.testing.ServerlessMethod;
import com.nimbusframework.nimbuscore.testing.file.LocalFileStorage;
import org.junit.jupiter.api.Test;

class FileStorageFunctionTest
{

    @Test
    public void invokeFileStorageFunction() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(FileStorageFunction.class);
        ServerlessMethod method = localNimbusDeployment.getMethod(FileStorageFunction.class, "fileStorageFunction");

        LocalFileStorage fileStorage = localNimbusDeployment.getLocalFileStorage("NimbusEvaluationBucket");
        fileStorage.saveFile("testFile", "helloWorld");
        assert method.getTimesInvoked() == 1;
    }

}