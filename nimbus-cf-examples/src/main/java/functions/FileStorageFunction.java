package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.file.FileStorageEventType;
import com.nimbusframework.nimbuscore.annotation.annotations.function.FileStorageServerlessFunction;
import com.nimbusframework.nimbuscore.wrappers.file.models.FileStorageEvent;
import models.ResponseModel;

public class FileStorageFunction {
    @FileStorageServerlessFunction(bucketName = "NimbusEvaluationBucket", eventType = FileStorageEventType.OBJECT_CREATED)
    public ResponseModel fileStorageFunction(FileStorageEvent event)
    {
        assert "testFile".equals(event.getKey());
        return new ResponseModel(true);
    }
}
