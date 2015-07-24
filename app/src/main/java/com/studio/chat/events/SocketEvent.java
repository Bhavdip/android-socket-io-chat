package com.studio.chat.events;

public class SocketEvent{

    private int eventCode;

    public SocketEvent(){

    }

    public int getEventCode() {
        return eventCode;
    }

    public SocketEvent setEventCode(int eventCode) {
        this.eventCode = eventCode;
        return this;
    }


}
