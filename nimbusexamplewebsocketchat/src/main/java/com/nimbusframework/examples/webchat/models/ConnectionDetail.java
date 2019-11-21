package com.nimbusframework.examples.webchat.models;


import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

import com.nimbusframework.nimbuscore.annotations.keyvalue.KeyValueStore;
import com.nimbusframework.nimbuscore.annotations.persistent.Attribute;

@KeyValueStore(
        keyType = String.class,
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
