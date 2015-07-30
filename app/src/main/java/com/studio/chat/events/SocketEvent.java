package com.studio.chat.events;

public class SocketEvent{

    private int eventCode;
    private String message;

    public SocketEvent(){
    }

    public int getEventCode() {
        return eventCode;
    }

    public SocketEvent setEventCode(int eventCode) {
        this.eventCode = eventCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SocketEvent setMessage(String message) {
        this.message = message;
        return this;
    }
}
