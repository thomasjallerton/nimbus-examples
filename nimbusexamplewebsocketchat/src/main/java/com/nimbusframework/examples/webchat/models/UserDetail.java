package com.nimbusframework.examples.webchat.models;

import com.nimbusframework.nimbuscore.annotations.document.DocumentStoreDefinition;
import com.nimbusframework.nimbuscore.annotations.persistent.Attribute;
import com.nimbusframework.nimbuscore.annotations.persistent.Key;

@DocumentStoreDefinition
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
