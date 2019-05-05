package com.nimbusframework.examples.webchat.models;


import com.nimbusframework.nimbuscore.annotation.annotations.document.DocumentStore;
import com.nimbusframework.nimbuscore.annotation.annotations.persistent.Attribute;
import com.nimbusframework.nimbuscore.annotation.annotations.persistent.Key;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

@DocumentStore(
        readCapacityUnits = 2,
        writeCapacityUnits = 2,
        stages = {DEV_STAGE, PRODUCTION_STAGE}
)
public class UserDetail {

    @Key
    private String username = "";

    @Attribute
    private String currentWebsocket = null;

    public UserDetail() {}

    public UserDetail(String username, String currentWebsocket) {
        this.username = username;
        this.currentWebsocket = currentWebsocket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentWebsocket() {
        return currentWebsocket;
    }

    public void setCurrentWebsocket(String currentWebsocket) {
        this.currentWebsocket = currentWebsocket;
    }
}
