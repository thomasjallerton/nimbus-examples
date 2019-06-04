package clientevaluation;

import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.testing.LocalNimbusDeployment;
import models.Database;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

class QueueClientEvalTest {

    @Test
    public void rdsClient() throws SQLException {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance(Database.class, RelationalDatabaseClientEval.class);

        localNimbusDeployment.getBasicFunction(RelationalDatabaseClientEval.class, "evaluateRDBClient").invoke();

        Connection connection = ClientBuilder.getDatabaseClient(Database.class).getConnection("testdb", false);
        ResultSet result = connection.getMetaData().getTables(null, null, "REGISTRATION", new String[] {"TABLE"});

        assert result.next();
    }

}