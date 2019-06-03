package clientevaluation;

import com.nimbusframework.nimbuscore.annotation.annotations.function.BasicServerlessFunction;
import com.nimbusframework.nimbuscore.annotation.annotations.function.EnvironmentVariable;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.function.EnvironmentVariableClient;

public class EnvVariableClient {

    @BasicServerlessFunction
    @EnvironmentVariable(key = "test", value = "test")
    public void evaluateEnvClient() {
        long startTime = System.nanoTime();

        EnvironmentVariableClient client = ClientBuilder.getEnvironmentVariableClient();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        String value = client.get("test");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        assert value.equals("test");
        System.out.println("GET: " + duration);

        startTime = System.nanoTime();

        boolean contains = client.containsKey("test");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("CONTAINS: " + duration);
    }

    @BasicServerlessFunction
    @EnvironmentVariable(key = "test", value = "test")
    public void evaluateAWSClient() {
        long startTime = System.nanoTime();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INITIALISATION: " + duration);

        startTime = System.nanoTime();

        String value = System.getenv("test");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("GET: " + duration);

        startTime = System.nanoTime();

        boolean contains = System.getenv().containsKey("test");

        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("CONTAINS: " + duration);
    }
}
