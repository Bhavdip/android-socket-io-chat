package com.studio.chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.studio.chat.events.AddUserEvent;
import com.studio.chat.events.AskUserHistoryEvent;
import com.studio.chat.events.ReceiveMsgEvent;
import com.studio.chat.events.UserLoginEvent;
import com.studio.chat.utility.Constants;
import com.studio.chat.utility.SocketManager;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class SocketService extends Service {

    private final String TAG = SocketService.class.getSimpleName();
    private EventBus mEventBus = EventBus.getDefault();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "SocketService#onCreate");

        mEventBus.register(this);

        Log.d(TAG, "SocketService#EventBus#Register");

        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT, onConnect);
        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT_TIMEOUT, onConnectionTimeOut);
        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT_ERROR, onConnectionError);
        SocketManager.getInstance().listenOn(Socket.EVENT_DISCONNECT, onDisconnect);

        SocketManager.getInstance().listenOn(Socket.EVENT_RECONNECT, onReconnect);
        SocketManager.getInstance().listenOn(Socket.EVENT_RECONNECT_ERROR, onReconnectError);

        SocketManager.getInstance().listenOn(Socket.EVENT_ERROR, onEventError);

        /**
         *  webservice socket listen node
         *
         */
        SocketManager.getInstance().listenOn(Constants.NODE_LOGIN, onLogin);

        SocketManager.getInstance().listenOn(Constants.NODE_CHAT_RECEIVE, onReceiveMessage);

        SocketManager.getInstance().listenOn(Constants.NODE_CHAT_ACK, onACKMessage);

        SocketManager.getInstance().listenOn(Constants.LISTEN_CHAT_USER_HISTORY, onChatUserHistory);

        SocketManager.getInstance().connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SocketManager.getInstance().disconnect();

        Log.d(TAG, "SocketService#EventBus#UnRegister");
        mEventBus.unregister(this);

        Log.d(TAG, "SocketService#onDestroy");
    }


    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // socket connection listener
            Log.d(TAG, "SocketService#onConnect");
        }
    };

    private Emitter.Listener onConnectionTimeOut = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // socket connection listener
            Log.d(TAG, "SocketService#onConnectionTimeOut");
        }
    };

    private Emitter.Listener onConnectionError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // socket connection listener
            Log.d(TAG, "SocketService#onConnectionError");
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Socket connection error listener
            Log.d(TAG, "SocketService#onDisconnect");
        }
    };

    private Emitter.Listener onReconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Socket connection error listener
            Log.d(TAG, "SocketService#onReconnect");
        }
    };

    private Emitter.Listener onReconnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Socket connection error listener
            Log.d(TAG, "SocketService#onReconnect#Error");
        }
    };

    private Emitter.Listener onEventError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Socket connection error listener
            Log.d(TAG, "SocketService#onEventError");
        }
    };

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String result = (String) args[0];
            Log.d(TAG, String.format("Login :%s", result));
            // we receiver the users list here
            mEventBus.post(new UserLoginEvent().setUserList(result));
        }
    };

    private Emitter.Listener onReceiveMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            String response = (String) args[0];
            Log.d(TAG, String.format("SocketService#Response[ReceiveMessage]: %s", response));
            if (!TextUtils.isEmpty(response)) {
                mEventBus.post(new ReceiveMsgEvent().setMessageType(0).setResponse(response));
            }
        }
    };

    private Emitter.Listener onACKMessage = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            String response = (String) args[0];
            Log.d(TAG, String.format("SocketService#Response[ACKMessage]: %s", response));
            if (!TextUtils.isEmpty(response)) {
                mEventBus.post(new ReceiveMsgEvent().setMessageType(1).setResponse(response));
            }
        }
    };

    private Emitter.Listener onChatUserHistory = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String response = (String) args[0];
            Log.d(TAG, String.format("SocketService#Response[ChatUserHistory]: %s", response));
            if (!TextUtils.isEmpty(response)) {
                mEventBus.post(new ReceiveMsgEvent().setMessageType(2).setResponse(response));
            }
        }
    };

    /**
     * @param addUserEvent
     */
    @Subscribe
    public void onAddUserEvent(AddUserEvent addUserEvent) {
        Log.d(TAG, "SocketService#Emit#onAddUserEvent");
        SocketManager.getInstance().emitEvent(Constants.NODE_ADD_USER, addUserEvent.getUserId());
    }

    @Subscribe
    public void onAskChatHistory(AskUserHistoryEvent ask) {
        Log.d(TAG, "SocketService#Emit#onAskChatHistory");
        // event, from user id, to user id
        SocketManager.getInstance().emitEvent(Constants.EMIT_CHAT_USER_HISTORY, ask.getThreadId(), ask.getFromUserId(), ask.getToUserID());
    }
}
