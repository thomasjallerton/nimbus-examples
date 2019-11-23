package com.nimbusframework.nimbusexampes.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nimbusframework.nimbuscore.annotations.function.HttpMethod;
import com.nimbusframework.nimbuslocal.LocalNimbusDeployment;
import com.nimbusframework.nimbuslocal.deployment.http.HttpRequest;
import org.junit.jupiter.api.Test;

class PostApiTest {

  @Test
  public void postApiPutsItemIntoStore() {
    LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance("com.nimbusframework.nimbusexampes.restapi");
    HttpRequest httpRequest = new HttpRequest("person", HttpMethod.POST, "{\"name\": \"test\", \"age\": 22}");
    localNimbusDeployment.sendHttpRequest(httpRequest);

    Person person = localNimbusDeployment.getDocumentStore(Person.class).get("test");
    assertNotNull(person);
    assertEquals("test", person.getName());
    assertEquals(22, person.getAge());
  }

}