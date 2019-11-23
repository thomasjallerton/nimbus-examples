package com.nimbusframework.nimbusexampes.restapi;

import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.eventabstractions.HttpEvent;

public class GetApi {

  private DocumentStoreClient<Person> personStore = ClientBuilder.getDocumentStoreClient(Person.class);

  @HttpServerlessFunction(method = HttpMethod.GET, path = "person/{name}")
  @UsesDocumentStore(dataModel = Person.class)
  public Person getPerson(HttpEvent event) {
    try {
      return personStore.get(event.getPathParameters().get("name"));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
