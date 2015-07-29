package com.studio.chat.utility;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.Arrays;

public class SocketManager {

    private final String TAG = SocketManager.class.getSimpleName();

    private Socket mSocket;

    private static SocketManager mSocketManager;

    static {
        mSocketManager = new SocketManager();
    }

    private SocketManager() {
    }

    public static SocketManager getInstance() {
        return mSocketManager;
    }

    public String rootHostURL(Context context) {
        StringBuilder hostBuilder = new StringBuilder(Constants.CHAT_SERVER_URL);
        hostBuilder.append("/");
        hostBuilder.append("?data=").append(getCurrentUserID(context));
        Log.d(TAG, String.format("SocketManager#HostURL#[%s]", hostBuilder.toString()));
        return hostBuilder.toString();
    }

    public String getCurrentUserID(Context context) {
        String userId = Prefrence.getCurrentUser(context, null);
        if (!TextUtils.isEmpty(userId)) {
            return userId;
        }
        return null;
    }

    public void openSocket(Context context) {
        try {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            mSocket = IO.socket(rootHostURL(context),options);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketManager listenOn(String event, Emitter.Listener fn) {
        mSocket.on(event, fn);
        Log.d(TAG, String.format("SocketManager#Listen On# :[%s]", event));
        return mSocketManager;
    }

    public SocketManager emitEvent(final String event, final Object[] args, final Ack ack) {
        mSocket.emit(event, args, ack);
        return mSocketManager;
    }

    public SocketManager emitEvent(final String event, final Object... args) {
        Log.d(TAG, String.format("SocketManager#Event:[%s], Param:[%s]", event, Arrays.toString(args)));
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

