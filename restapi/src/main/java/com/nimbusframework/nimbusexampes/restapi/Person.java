package com.nimbusframework.nimbusexampes.restapi;

import com.nimbusframework.nimbuscore.annotations.document.DocumentStoreDefinition;
import com.nimbusframework.nimbuscore.annotations.persistent.Attribute;
import com.nimbusframework.nimbuscore.annotations.persistent.Key;

import java.util.Objects;

@DocumentStoreDefinition
public class Person {

  @Key
  private String name;

  @Attribute
  private int age;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public Person() {}

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return age == person.age &&
        Objects.equals(name, person.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, age);
  }
}
