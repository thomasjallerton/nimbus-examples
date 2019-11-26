package com.nimbusframework.nimbusexampes.restapi;

import com.nimbusframework.nimbuslocal.LocalNimbusDeployment;
import org.junit.jupiter.api.Test;

public class LocalServer {

  @Test
  public void startLocalServer() {
    LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance("com.nimbusframework.nimbusexampes.restapi");
    localNimbusDeployment.startAllServers();
  }

}
