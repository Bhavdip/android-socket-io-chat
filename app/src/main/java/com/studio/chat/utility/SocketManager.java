package com.studio.chat.utility;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.Arrays;
public class SocketManager {

    private final String TAG = SocketManager.class.getSimpleName();

    private static String URI = Constants.CHAT_SERVER_URL;
    private static SocketManager mSocketManager;

    static {
        mSocketManager = new SocketManager();
    }

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(URI);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private SocketManager() {
    }

    public static SocketManager getInstance() {
        return mSocketManager;
    }

    public SocketManager listenOn(String event, Emitter.Listener fn) {
        mSocket.on(event, fn);
        Log.d(TAG, String.format("Listen On# :[%s]", event));
        return mSocketManager;
    }

    public SocketManager emitEvent(final String event, final Object[] args, final Ack ack) {
        mSocket.emit(event, args, ack);
        return mSocketManager;
    }

    public SocketManager emitEvent(final String event, final Object... args) {
        Log.d(TAG, String.format("Event:[%s], Param:[%s]", event, Arrays.toString(args)));
        mSocket.emit(event, args);
        return mSocketManager;
    }

    public SocketManager connect() {
        mSocket.connect();
        return mSocketManager;
    }

    public SocketManager disconnect() {
        mSocket.off();
        mSocket.disconnect();
        return mSocketManager;
    }

}

