package com.studio.chat.events;


public class AddUserEvent {

    private String userId;

    public AddUserEvent(){
    }

    public String getUserId() {
        return userId;
    }

    public AddUserEvent setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
