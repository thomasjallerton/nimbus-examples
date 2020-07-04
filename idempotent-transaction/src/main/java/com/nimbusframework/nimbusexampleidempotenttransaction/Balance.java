package com.nimbusframework.nimbusexampleidempotenttransaction;

import com.nimbusframework.nimbuscore.annotations.document.DocumentStoreDefinition;
import com.nimbusframework.nimbuscore.annotations.persistent.Attribute;
import com.nimbusframework.nimbuscore.annotations.persistent.Key;

@DocumentStoreDefinition
public class Balance {

  @Key
  private String user;

  @Attribute
  private Float amount;

  public Balance() {}

  public Balance(String user, Float amount) {
    this.user = user;
    this.amount = amount;
  }

  public Float getAmount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}
