package com.studio.chat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class ConvertationActivity extends Activity {

    public final static String TO_CONVERTATION_USER = "touser";

    public final static String FROM_CONVERATION_USER = "fromuser";

    private RecyclerView mMessagesView;
    private MessageAdapter mAdapter;
    private String mUsername;
    private ObjectMapper mObjectMapper = new ObjectMapper();
    private EditText mInputMessageView;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        if (getIntent().getExtras() != null) {
            mUsername = getIntent().getExtras().getString(FROM_CONVERATION_USER);
            try {
                mUser = mObjectMapper.readValue(getIntent().getExtras().getString(TO_CONVERTATION_USER), User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mAdapter = new MessageAdapter(getApplicationContext());
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

        mInputMessageView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null == mUsername) return;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });

        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT, onConnect);
        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT_ERROR, onConnectError);
        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        SocketManager.getInstance().listenOn(Constants.NODE_CHAT_RECEIVE, onReceiveMessage);
        SocketManager.getInstance().listenOn(Constants.NODE_CHAT_ACK, onReceiveMessage);

        SocketManager.getInstance().connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().listenOffAll();
        SocketManager.getInstance().disconnect();
    }

    private void addAllMessage(List<Message> messages) {
        mAdapter.addMessages(messages);
        scrollToBottom();
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
        // perform the sending message attempt.

        SocketManager.getInstance().emitEvent(Constants.NODE_SEND_CHAT_MESSAGE,
                mInputMessageView.getText(),
                String.valueOf(mUser.getUserId()),"619","0",String.valueOf(0));
        mInputMessageView.setText("");
    }

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), R.string.error_connect, Toast.LENGTH_LONG).show();
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
            if(!TextUtils.isEmpty(response)){
                List<Message> messages = new ParseFromJsonCommand(response,Message.class).buildJsonToPoJo();
                if(messages != null && messages.size() > 0)
                {
                    addAllMessage(messages);
                }
            }
        }
    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), R.string.message_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };


}
