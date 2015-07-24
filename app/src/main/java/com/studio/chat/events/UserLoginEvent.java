package com.studio.chat.events;

public class UserLoginEvent {

    public String userList;

    public UserLoginEvent() {
    }

    public String getUserList() {
        return userList;
    }

    public UserLoginEvent setUserList(String userList) {
        this.userList = userList;
        return this;
    }
}
