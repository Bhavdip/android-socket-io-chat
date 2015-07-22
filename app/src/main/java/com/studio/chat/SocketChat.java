package com.studio.chat;

import android.app.Application;

import com.studio.chat.utility.SocketManager;

public class SocketChat extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SocketManager.getInstance().connect();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SocketManager.getInstance().disconnect();
    }
}
