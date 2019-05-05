package com.nimbusframework.examples.webchat.models;

public class WebSocketMessage {

    public WebSocketMessage() {}

    private String message = "";

    private String recipient = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
