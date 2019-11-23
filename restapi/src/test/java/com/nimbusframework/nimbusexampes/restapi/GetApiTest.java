package com.nimbusframework.nimbusexampes.restapi;

import static org.junit.jupiter.api.Assertions.*;

import com.nimbusframework.nimbuscore.annotations.function.HttpMethod;
import com.nimbusframework.nimbuslocal.LocalNimbusDeployment;
import com.nimbusframework.nimbuslocal.deployment.http.HttpRequest;
import org.junit.jupiter.api.Test;

class GetApiTest {

  @Test
  public void getApiFetchesItemFromStore() {
    LocalNimbusDeployment localNimbusDeployment = LocalNimbusDeployment.getNewInstance("com.nimbusframework.nimbusexampes.restapi");
    Person person = new Person("test", 24);
    localNimbusDeployment.getDocumentStore(Person.class).put(person);

    HttpRequest httpRequest = new HttpRequest("person/test", HttpMethod.GET);
    Person getPerson = (Person) localNimbusDeployment.sendHttpRequest(httpRequest);

    assertEquals(person, getPerson);
  }

}