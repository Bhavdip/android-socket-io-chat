package com.studio.chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.Socket;
import com.studio.chat.events.AddUserEvent;
import com.studio.chat.events.AskUserBlogLike;
import com.studio.chat.events.AskUserHistoryEvent;
import com.studio.chat.events.ReceiveMsgEvent;
import com.studio.chat.events.SendChatEvent;
import com.studio.chat.events.SocketEvent;
import com.studio.chat.events.UserBlogLike;
import com.studio.chat.events.UserLoginEvent;
import com.studio.chat.utility.Constants;
import com.studio.chat.utility.Prefrence;
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
        initializationSocket();
    }

    public void initializationSocket() {
        if (!TextUtils.isEmpty(getCurrentUserID()))
        {
            Log.d(TAG, "SocketService#initializationSocket#SUCSSFULL");

            SocketManager.getInstance().openSocket(getApplicationContext());

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

            SocketManager.getInstance().listenOn(Constants.LISTEN_USER_BLOG_LIKE, onReturnUserBlogLike);

            SocketManager.getInstance().listenOn(Constants.LISTEN_BOTH_USER_BLOG_LIKE, onBothUserBlogLike);

            SocketManager.getInstance().connect();
        }else{
            Log.d(TAG, "SocketService#initializationSocket#FAIL");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().disconnect();
        Log.d(TAG, "SocketService#EventBus#UnRegister");
        mEventBus.unregister(this);
        Log.d(TAG, "SocketService#onDestroy");
    }

    public String getCurrentUserID() {
        String userId = Prefrence.getCurrentUser(getApplicationContext(), null);
        if (!TextUtils.isEmpty(userId)) {
            return userId;
        }
        Log.d(TAG, String.format("SocketService#FetCurrentUserID#%s", userId));
        return null;
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
            Log.d(TAG, String.format("SocketService#onConnectionError#%s",args));
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Socket connection error listener
            Log.d(TAG, String.format("SocketService#onDisconnect#%s",args));
        }
    };

    private Emitter.Listener onReconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Socket connection error listener
            Log.d(TAG, String.format("SocketService#onReconnect#%s",args));
        }
    };

    private Emitter.Listener onReconnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Socket connection error listener
            Log.d(TAG, String.format("SocketService#onReconnect#Error%s",args));
        }
    };

    private Emitter.Listener onEventError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Socket connection error listener
            Log.d(TAG, String.format("SocketService#onEventError%s", args));
        }
    };

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String result = (String) args[0];
            Log.d(TAG, String.format("SocketService#Response[Login :%s]", result));
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

    private Emitter.Listener onReturnUserBlogLike = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String response = (String) args[0];
            Log.d(TAG, String.format("SocketService#Response[Return_user_blog_like]: %s", response));
            if (!TextUtils.isEmpty(response)) {
                mEventBus.post(new UserBlogLike().setResponse(response).setForBothUser(false));
            }
        }
    };

    private Emitter.Listener onBothUserBlogLike = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String response = (String) args[0];
            Log.d(TAG, String.format("SocketService#Response[return_bothuser_blog_like]: %s", response));
            if (!TextUtils.isEmpty(response)) {
                mEventBus.post(new UserBlogLike().setResponse(response).setForBothUser(true));
            }
        }
    };

    @Subscribe
    public void sendChatMessage(SendChatEvent sendChatEvent){
        Log.d(TAG, String.format("SocketService#Emit#sendChatMessage[FmId:%s],[ToId:%s],[ThreadId:%s]",
                sendChatEvent.getFromUserId(),sendChatEvent.getToUserId(), sendChatEvent.getThreadId()));

        SocketManager.getInstance().emitEvent(Constants.NODE_SEND_CHAT_MESSAGE,
                sendChatEvent.getMessage(),
                sendChatEvent.getFromUserId(),
                sendChatEvent.getToUserId(), sendChatEvent.getMsgType(),sendChatEvent.getThreadId());
    }

    @Subscribe
    public void askAddUserEvent(AddUserEvent userEvent) {
        Log.d(TAG, "SocketService#Emit#onAddUserEvent");
        Prefrence.updateCurrentUser(getApplicationContext(), userEvent.getUserId());
        Log.d(TAG, String.format("SocketService#Emit#Prefrence.updateCurrentUser#%s", userEvent.getUserId()));
        initializationSocket();
        SocketManager.getInstance().emitEvent(Constants.NODE_ADD_USER, userEvent.getUserId());
    }

    @Subscribe
    public void askChatHistory(AskUserHistoryEvent userHistoryEvent) {
        Log.d(TAG, String.format("SocketService#Emit#onAskChatHistory[Offset:%s]",userHistoryEvent.getOffset()));
        // event, from user id, to user id
        SocketManager.getInstance().emitEvent(Constants.EMIT_CHAT_USER_HISTORY,
                userHistoryEvent.getThreadId(),
                userHistoryEvent.getFromUserId(),
                userHistoryEvent.getToUserID(),
                userHistoryEvent.getOffset(), new Ack() {
                    @Override
                    public void call(Object... args) {
                        String arg = (String) args[0];
                        Log.d(TAG, String.format("SocketService#Emit#askChatHistory#%s",arg));
                        mEventBus.post(new SocketEvent().setEventCode(200).setMessage(arg));
                    }
                });
    }

    @Subscribe
    public void askBlogLikeByUser(AskUserBlogLike userBlogLike) {
        if (!TextUtils.isEmpty(userBlogLike.getFromUserId())) {
            if (!userBlogLike.isForBothUser()) {
                Log.d(TAG, "SocketService#Emit#askSingleUserBlogLike");
                SocketManager.getInstance().emitEvent(Constants.EMIT_USER_BLOG_LIKE, userBlogLike.getFromUserId());
            } else {
                Log.d(TAG, "SocketService#Emit#askBothUserBlogLike");
                SocketManager.getInstance().emitEvent(Constants.EMIT_BOTH_USER_BLOG_LIKE, userBlogLike.getFromUserId(), userBlogLike.getToUserId());
            }

        }
    }


}
