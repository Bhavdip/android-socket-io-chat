package com.studio.chat.utility;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketManager {

    private final String TAG = SocketManager.class.getName();

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

    private boolean hasSocketAvailable() {
        if (mSocket != null) {
            return true;
        }
        return false;
    }

    public boolean socketConnected() {
        if (hasSocketAvailable()) {
            if (mSocket.connected()) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static SocketManager getInstance() {
        return mSocketManager;
    }

    public SocketManager listenOn(String event, Emitter.Listener fn) {
        if (hasSocketAvailable()) {
            mSocket.on(event, fn);
        }
        return mSocketManager;
    }

    public SocketManager listenOff(String event, Emitter.Listener fn) {
        if (hasSocketAvailable()) {
            mSocket.off(event, fn);
        }
        return mSocketManager;
    }

    public SocketManager listenOffAll() {
        if (hasSocketAvailable()) {
            mSocket.off();
        }
        return mSocketManager;
    }

    public SocketManager emitEvent(final String event, final Object[] args, final Ack ack) {
        if (hasSocketAvailable()) {
            mSocket.emit(event, args, ack);
        }
        return mSocketManager;
    }

    public SocketManager emitEvent(final String event, final Object... args) {
        if (hasSocketAvailable()) {
            Log.d(TAG,String.format("Event:[%s], Param:[%s]",event,args));
            mSocket.emit(event, args);
        }
        return mSocketManager;
    }

    public SocketManager connect() {
        if (!socketConnected()) {
            mSocket.connect();
        }
        return mSocketManager;
    }

    public SocketManager disconnect() {
        if (socketConnected()) {
            mSocket.off();
            mSocket.disconnect();
        }
        return mSocketManager;
    }

}
