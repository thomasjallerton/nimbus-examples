package com.nimbusframework.examples.basicfunctions;

import com.nimbusframework.nimbuscore.annotations.deployment.AfterDeployment;
import com.nimbusframework.nimbuscore.annotations.function.UsesBasicServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;

public class AfterDeploymentFunctions {

    @AfterDeployment
    @UsesBasicServerlessFunction(targetClass = Calculator.class, methodName = "add")
    public String afterDeployment() {
        Calculator calculator = ClientBuilder.getBasicServerlessFunctionInterface(Calculator.class);
        return "Add returned " + calculator.add(new AddRequest(10, 21));
    }
}
