package com.nimbusframework.nimbusexampes.restapi;

import com.nimbusframework.nimbuscore.annotations.document.UsesDocumentStore;
import com.nimbusframework.nimbuscore.annotations.function.HttpMethod;
import com.nimbusframework.nimbuscore.annotations.function.HttpServerlessFunction;
import com.nimbusframework.nimbuscore.clients.ClientBuilder;
import com.nimbusframework.nimbuscore.clients.document.DocumentStoreClient;
import com.nimbusframework.nimbuscore.exceptions.NonRetryableException;
import com.nimbusframework.nimbuscore.exceptions.RetryableException;

public class PostApi {

  private DocumentStoreClient<Person> personStore = ClientBuilder.getDocumentStoreClient(Person.class);

  @HttpServerlessFunction(method = HttpMethod.POST, path = "person")
  @UsesDocumentStore(dataModel = Person.class)
  public boolean newPerson(Person newPerson) {
    try {
      personStore.put(newPerson);
      return true;
    } catch (RetryableException e) {
      return newPerson(newPerson);
    } catch (NonRetryableException e) {
      e.printStackTrace();
      return false;
    }
  }

}
