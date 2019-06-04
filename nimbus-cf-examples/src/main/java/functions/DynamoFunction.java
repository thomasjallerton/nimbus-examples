package functions;

import com.nimbusframework.nimbuscore.annotation.annotations.function.DocumentStoreServerlessFunction;
import com.nimbusframework.nimbuscore.annotation.annotations.persistent.StoreEventType;
import com.nimbusframework.nimbuscore.wrappers.store.models.StoreEvent;
import models.DataModel;
import models.ResponseModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamoFunction {
    @DocumentStoreServerlessFunction(method = StoreEventType.INSERT, dataModel =DataModel.class)
    public ResponseModel dynamoFunction(DataModel newItem, StoreEvent storeEvent)
    {
        assertEquals(newItem, new DataModel("test", "test"));
        return new ResponseModel(newItem.getUsername(), newItem.getFullName(),true);
    }
}
