package com.nimbusframework.examples.basicfunctions;

import com.nimbusframework.nimbuslocal.LocalNimbusDeployment;
import com.nimbusframework.nimbuslocal.ServerlessMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AfterDeploymentFunctionsTest {

    @Test
    public void afterDeploymentCallsFunction() {
        LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance("com.nimbusframework.examples.basicfunctions");
        ServerlessMethod method = localNimbusDeployment.getMethod(Calculator.class, "add");
        assertEquals(1, method.getTimesInvoked());
        assertEquals(31, method.getMostRecentValueReturned());
    }

}