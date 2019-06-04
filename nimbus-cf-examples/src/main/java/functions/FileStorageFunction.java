package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.file.FileStorageEventType;
import com.nimbusframework.nimbuscore.annotation.annotations.function.FileStorageServerlessFunction;
import com.nimbusframework.nimbuscore.wrappers.file.models.FileStorageEvent;
import models.ResponseModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileStorageFunction {
    @FileStorageServerlessFunction(bucketName = "NimbusEvaluationBucket", eventType = FileStorageEventType.OBJECT_CREATED)
    public ResponseModel fileStorageFunction(FileStorageEvent event)
    {
        assertEquals(event.getKey(), "testFile");
        return new ResponseModel(true);
    }
}
