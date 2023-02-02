package com.example.shop_module.app.domain;

public class MessageFromSocket {

    private String sessionId;
    private String message;

    public MessageFromSocket() {
    }

    public MessageFromSocket(String sessionId, String message) {
        this.sessionId = sessionId;
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
