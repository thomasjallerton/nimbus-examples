package com.nimbusframework.examples.webchat.models;


import com.nimbusframework.nimbuscore.annotations.keyvalue.KeyValueStoreDefinition;
import com.nimbusframework.nimbuscore.annotations.persistent.Attribute;

@KeyValueStoreDefinition(
        keyType = String.class
)
public class ConnectionDetail {

    public ConnectionDetail() {}

    public ConnectionDetail(String username) {
        this.username = username;
    }

    @Attribute
    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
