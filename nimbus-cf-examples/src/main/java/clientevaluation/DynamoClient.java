package clientevaluation;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.nimbusframework.nimbuscore.annotation.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import models.DataModel;

public class DynamoClient {

    @BasicServerlessFunction
    @UsesDocumentStore(dataModel = DataModel.class)
    public void evaluateDynamoClient() {
        long startTime = System.nanoTime();

        DocumentStoreClient<DataModel> client = ClientBuilder.getDocumentStoreClient(DataModel.class);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        client.put(new DataModel("test", "test"));

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("INSERT: " + duration);

        startTime = System.nanoTime();

        DataModel model = client.get("test");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        assert model.equals(new DataModel("test", "test"));

        System.out.println("GET: " + duration);

        startTime = System.nanoTime();

        client.deleteKey("test");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("REMOVE: " + duration);

    }

    @BasicServerlessFunction
    @UsesDocumentStore(dataModel = DataModel.class)
    public void evaluateAWSDynamo() {
        long startTime = System.nanoTime();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("DataModeldev");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        DataModel dataModel = new DataModel("test", "test");
        table.putItem(new Item()
                .withString("username", dataModel.getUsername())
                .withString("fullName", dataModel.getFullName()));


        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("INSERT: " + duration);

        startTime = System.nanoTime();

        Item item = table.getItem("username", "test");
        DataModel result = new DataModel(
                item.getString("username"),
                item.getString("fullName")
        );

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("GET: " + duration);

        startTime = System.nanoTime();

        table.deleteItem("username", "test");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("REMOVE: " + duration);

    }
}
