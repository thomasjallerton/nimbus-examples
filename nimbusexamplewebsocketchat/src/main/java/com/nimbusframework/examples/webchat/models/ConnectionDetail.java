package com.nimbusframework.examples.webchat.models;


import com.nimbusframework.nimbuscore.annotation.annotations.keyvalue.KeyValueStore;
import com.nimbusframework.nimbuscore.annotation.annotations.persistent.Attribute;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

@KeyValueStore(
        keyType = String.class,
        readCapacityUnits = 1,
        writeCapacityUnits = 1,
        stages = {DEV_STAGE, PRODUCTION_STAGE}
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
