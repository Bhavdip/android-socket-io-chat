package com.studio.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserListActivity extends Activity{

    private final String TAG = UserListActivity.class.getName();

    public final static String KEY_JSON_USERS = "userjson";
    public final static String KEY_USER_NAME = "username";
    public final static int  REQUEST_LOGIN = 1;

    private List<User> mUserList = new ArrayList<>();
    private TextView mTextViewUserName;
    private String mUsername;
    private String jsonUsers;
    private RecyclerView mUserListView;
    private UsersAdapter mUserAdapter;
    private ObjectMapper objectMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.github.nkzawa.socketio.chat.R.layout.activity_users_list);

        mTextViewUserName = (TextView)findViewById(com.github.nkzawa.socketio.chat.R.id.textview_userName);
        mTextViewUserName.setText("");

        mUserListView = (RecyclerView) findViewById(com.github.nkzawa.socketio.chat.R.id.userlist);
        mUserListView.setLayoutManager(new LinearLayoutManager(this));
        mUserListView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerViewClickHandler()));

        mUserAdapter = new UsersAdapter(getApplicationContext());
        mUserListView.setAdapter(mUserAdapter);

        startSignIn();
    }

    private void startSignIn() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) {
            finish();
            return;
        }
        if(data.getExtras() != null)
        {

            mUsername = data.getStringExtra(KEY_USER_NAME);
            jsonUsers = data.getStringExtra(KEY_JSON_USERS);
            Log.d(TAG,String.format("Response : %s",jsonUsers));
            appendUser(jsonUsers);

        }
    }

    public class RecyclerViewClickHandler implements RecyclerItemClickListener.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            if(mUserList != null){
                ObjectMapper objectMapper = new ObjectMapper();
                try{
                    String json = objectMapper.writeValueAsString(mUserList.get(position));
                    Intent mIntent  = new Intent(getApplicationContext(), ConvertationActivity.class);
                    mIntent.putExtra(ConvertationActivity.TO_CONVERTATION_USER, json);
                    mIntent.putExtra(ConvertationActivity.FROM_CONVERATION_USER,mUsername);
                    startActivity(mIntent);
                }catch (Exception e){}

            }
        }
    }


    public void appendUser(String jsonUsers){
        if(TextUtils.isEmpty(jsonUsers)){
            return;
        }
        try {
            objectMapper = new ObjectMapper();
            if(mUserList.size() > 0){
                mUserList.clear();
            }
            mUserList = objectMapper.readValue(jsonUsers, TypeFactory.defaultInstance().constructCollectionType(List.class,User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(mUserList != null && mUserList.size() > 0)
        {
            mTextViewUserName.setText(mUsername);
            mUserAdapter.addAllUsers(mUserList);

        }
    }
}
