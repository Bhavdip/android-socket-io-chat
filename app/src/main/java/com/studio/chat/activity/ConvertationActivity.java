package com.studio.chat.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.studio.chat.utility.Constants;
import com.studio.chat.adapter.MessageAdapter;
import com.studio.chat.R;
import com.studio.chat.model.Message;
import com.studio.chat.model.User;
import com.studio.chat.utility.ParseFromJsonCommand;
import com.studio.chat.utility.SocketManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ConvertationActivity extends BaseActivity {

    public final String TAG = ConvertationActivity.class.getName();

    public final static String TO_CONVERSION_USER = "touser";
    public final static String FROM_CONVERSION_USER = "fromuser";

    private ObjectMapper mObjectMapper = new ObjectMapper();
    private RecyclerView mMessagesView;
    private MessageAdapter mAdapter;
    private String mUsername;
    private EditText mInputMessageView;
    private TextView mTextview_userName;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT, onConnect);
        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT_ERROR, onConnectError);
        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        SocketManager.getInstance().listenOn(Constants.NODE_CHAT_RECEIVE, onReceiveMessage);
        SocketManager.getInstance().listenOn(Constants.NODE_CHAT_ACK, onACKMessage);
        SocketManager.getInstance().listenOn(Constants.LISTEN_CHAT_USER_HISTORY, onChatUserHistory);

        if (getIntent().getExtras() != null) {
            mUsername = getIntent().getExtras().getString(FROM_CONVERSION_USER);

            try {
                mUser = mObjectMapper.readValue(getIntent().getExtras().getString(TO_CONVERSION_USER), User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mAdapter = new MessageAdapter(getApplicationContext(), mUsername);
        mMessagesView = (RecyclerView) findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) findViewById(R.id.message_input);
        mInputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.send_button || id == EditorInfo.IME_NULL) {
                    attemptSend();
                    return true;
                }
                return false;
            }
        });

        ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });

        mTextview_userName = (TextView) findViewById(R.id.textview_userName);
        mTextview_userName.setText(String.valueOf(mUsername));

        askUserChatHistory();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().listenOff(Socket.EVENT_CONNECT, onConnect);
        SocketManager.getInstance().listenOff(Socket.EVENT_CONNECT_ERROR, onConnectError);
        SocketManager.getInstance().listenOff(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        SocketManager.getInstance().listenOff(Constants.NODE_CHAT_RECEIVE, onReceiveMessage);
        SocketManager.getInstance().listenOff(Constants.NODE_CHAT_ACK, onACKMessage);
        SocketManager.getInstance().listenOff(Constants.LISTEN_CHAT_USER_HISTORY, onChatUserHistory);
    }

    private void askUserChatHistory(){
        //thread_id, from_user, to_name
        String threadId;
        if(mUser.getThreadId() == null){
            threadId = new String("0");
        }else{
            threadId =mUser.getThreadId();
        }

        //mdf6ia1nhfr, from_user(675) , to_user(664)
        //SocketManager.getInstance().emitEvent(Constants.EMIT_CHAT_USER_HISTORY,"mdf6ia1nhfr","664","675");
        SocketManager.getInstance().emitEvent(Constants.EMIT_CHAT_USER_HISTORY,threadId,mUsername,mUser.getUserId());
    }

    private void addAllMessage(final List<Message> messages) {
        updateUserModel(messages.get(0).getThreadId());
        mAdapter.addMessages(messages);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                scrollToBottom();
            }
        });
    }

    private void addSingleMessage(Message nwMessage) {
        mAdapter.addMessages(nwMessage);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                scrollToBottom();
            }
        });
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void attemptSend() {
        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }
        SocketManager.getInstance().emitEvent(Constants.NODE_SEND_CHAT_MESSAGE, mInputMessageView.getText(), String.valueOf(mUser.getUserId()), mUsername, "0", String.valueOf(mUser.getThreadId()));
        mInputMessageView.setText("");
    }

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Receive Message");
                }
            });
        }
    };

    private Emitter.Listener onMessageSend = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
        }
    };

    private Emitter.Listener onReceiveMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            String response = (String) args[0];
            if (!TextUtils.isEmpty(response)) {
                List<Message> messages = new ParseFromJsonCommand(response, Message.class).buildList();
                if (messages != null && messages.size() > 0) {
                    addAllMessage(messages);
                    Log.d(TAG, "Receive Message");
                }
            }
        }
    };

    private Emitter.Listener onACKMessage = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            String response = (String) args[0];
            Log.d(TAG, "Ack Message");
            List<Message> sendMessage = new ParseFromJsonCommand(response, Message.class).buildList();
            if (sendMessage != null && sendMessage.size() > 0) {
                addAllMessage(sendMessage);
                Log.d(TAG, "Ack Message");
            }
        }
    };


    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "conversation Activity");
                }
            });
        }
    };


    private Emitter.Listener onChatUserHistory = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String response = (String) args[0];
            List<Message> sendMessage = new ParseFromJsonCommand(response, Message.class).buildList();
            if (sendMessage != null && sendMessage.size() > 0) {
                addAllMessage(sendMessage);
                Log.d(TAG, "Listen Chat History");
            }
        }
    };

    private void updateUserModel(String threadId){
        if(mUser != null){
            mUser.setThreadId(threadId);
        }
    }

}
