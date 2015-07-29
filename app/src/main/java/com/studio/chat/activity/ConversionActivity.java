package com.studio.chat.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.studio.chat.R;
import com.studio.chat.adapter.MessageAdapter;
import com.studio.chat.events.AskUserHistoryEvent;
import com.studio.chat.events.ReceiveMsgEvent;
import com.studio.chat.events.SendChatEvent;
import com.studio.chat.model.Message;
import com.studio.chat.model.User;
import com.studio.chat.utility.Constants;
import com.studio.chat.utility.Prefrence;
import com.studio.chat.utility.SocketManager;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class ConversionActivity extends BaseActivity {

    public final String TAG = ConversionActivity.class.getSimpleName();

    public final int REQUEST_BLOG = 122;

    public final static String TO_CONVERSION_USER = "touser";
    public final static String FROM_CONVERSION_USER = "fromuser";

    private EventBus mEventBus = EventBus.getDefault();
    private LinearLayoutManager mLinearLayoutManager;
    private ObjectMapper mObjectMapper = new ObjectMapper();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mMessagesView;
    private MessageAdapter mAdapter;
    private String mUsername;
    private EditText mInputMessageView;
    private TextView mTextview_userName;
    private User mUser;
    private ImageButton mShare_blog;
    private int offsetcounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        mEventBus.register(this);

        if (getIntent().getExtras() != null) {
            mUsername = getIntent().getExtras().getString(FROM_CONVERSION_USER);

            try {
                mUser = mObjectMapper.readValue(getIntent().getExtras().getString(TO_CONVERSION_USER), User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Prefrence.updateSelectUser(getApplicationContext(), String.valueOf(mUser.getUserId()));
        }
        mAdapter = new MessageAdapter(getApplicationContext(), mUsername);
        mMessagesView = (RecyclerView) findViewById(R.id.messages);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mMessagesView.setLayoutManager(mLinearLayoutManager);
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) findViewById(R.id.message_input);
        mInputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.send_button || id == EditorInfo.IME_NULL) {
                    sendChatMessage();
                    return true;
                }
                return false;
            }
        });

        ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatMessage();
            }
        });

        mTextview_userName = (TextView) findViewById(R.id.textview_userName);
        mTextview_userName.setText(String.valueOf(mUsername));


        mShare_blog = (ImageButton) findViewById(R.id.share_blog);
        mShare_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), BlogListActivity.class), REQUEST_BLOG);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new ItemRefreshListener());

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
        mEventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BLOG) {
            if (resultCode == RESULT_OK) {
                String blodId = data.getStringExtra(BlogListActivity.INTENT_BLOG_ID);
                sendBlogMessage(blodId);
            }
        }
    }

    private void askUserChatHistory() {
        //thread_id, from_user, to_name
        String threadId;
        if (mUser.getThreadId() == null) {
            threadId = new String("0");
        } else {
            threadId = mUser.getThreadId();
        }
        // fire event bus event to the service
        // SocketManager.getInstance().emitEvent(Constants.EMIT_CHAT_USER_HISTORY,threadId,mUsername,mUser.getUserId());
        if (mEventBus != null) {
            mEventBus.post(new AskUserHistoryEvent().setThreadId(threadId).setFromUserId(mUsername).setOffset(String.valueOf(offsetcounter)).setToUserID(String.valueOf(mUser.getUserId())));
        }
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

    private void sendChatMessage() {
        Log.d(TAG, String.format("Conversation#sendChatMessage#"));
        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }
        mEventBus.post(new SendChatEvent().setMessage(message).setFromUserId(String.valueOf(mUser.getUserId())).setToUserId(mUsername).setMsgType("0").setThreadId(mUser.getThreadId()));
        mInputMessageView.setText("");
    }

    private void sendBlogMessage(String blogId) {
        Log.d(TAG, String.format("Conversation#sendBlogMsg#%s", blogId));
        mEventBus.post(new SendChatEvent().setMessage(blogId)
                .setFromUserId(String.valueOf(mUser.getUserId()))
                .setToUserId(mUsername).setMsgType("1").setThreadId(mUser.getThreadId()));
    }

    @Subscribe
    public void setOnReceiveMessage(ReceiveMsgEvent receiveMessage) {
        // when we receive any message we dismiss the pull to refresh
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        if (receiveMessage.hasMessages())
        {
            if (receiveMessage.getMessageType() == 0) {
                Log.d(TAG, "Chat Message");
            } else if (receiveMessage.getMessageType() == 1) {
                Log.d(TAG, "Ack Message");
            } else {
                Log.d(TAG, "Chat History Message");
            }
            addAllMessage(receiveMessage.receiveMessages());
        }
    }

    private void updateUserModel(String threadId) {
        if (mUser != null) {
            mUser.setThreadId(threadId);
        }
    }

    public class ItemRefreshListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            // Refresh items
            Log.d(TAG, String.format("Conversation#onRefresh"));
            refreshItems();
        }

        void refreshItems() {
            Log.d(TAG, String.format("Conversation#refreshItems"));
            offsetcounter = offsetcounter + 50;
            askUserChatHistory();
        }
    }
}
