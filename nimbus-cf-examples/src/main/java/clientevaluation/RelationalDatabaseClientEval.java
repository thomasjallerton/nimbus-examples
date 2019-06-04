package clientevaluation;

import com.nimbusframework.nimbuscore.annotation.annotations.database.UsesRelationalDatabase;
import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.rdbms.DatabaseClient;
import models.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RelationalDatabaseClientEval {

    @BasicServerlessFunction
    @UsesRelationalDatabase(dataModel = Database.class)
    public void evaluateRDBClient() throws SQLException {
        long startTime = System.nanoTime();

        DatabaseClient client = ClientBuilder.getDatabaseClient(Database.class);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        Connection connection = client.getConnection("testdb", true);

        endTime = System.nanoTime();
        duration = (endTime - startTime);
        Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE REGISTRATION " +
                "(id INTEGER not NULL, " +
                " first VARCHAR(255), " +
                " last VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";

        stmt.executeUpdate(sql);

        System.out.println("CONNECTION: " + duration);
    }

    @BasicServerlessFunction
    @UsesRelationalDatabase(dataModel = Database.class)
    public void evaluateClient() {
        long startTime = System.nanoTime();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://evaluationdbdev.cvghjo0rbr3a.eu-west-1.rds.amazonaws.com", "test", "28397ksjhdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("CONNECTION: " + duration);
    }
}
