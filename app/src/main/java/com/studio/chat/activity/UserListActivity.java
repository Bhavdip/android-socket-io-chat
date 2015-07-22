package com.studio.chat.activity;

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
import com.studio.chat.R;
import com.studio.chat.utility.Constants;
import com.studio.chat.utility.ParseFromJsonCommand;
import com.studio.chat.utility.RecyclerItemClickListener;
import com.studio.chat.adapter.UsersAdapter;
import com.studio.chat.model.User;
import com.studio.chat.utility.SocketManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_users_list);

        mTextViewUserName = (TextView)findViewById(R.id.textview_userName);
        mTextViewUserName.setText("");

        mUserListView = (RecyclerView) findViewById(R.id.userlist);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class RecyclerViewClickHandler implements RecyclerItemClickListener.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            if(mUserList != null){
                ObjectMapper objectMapper = new ObjectMapper();
                try{
                    String json = objectMapper.writeValueAsString(mUserList.get(position));
                    Intent mIntent  = new Intent(getApplicationContext(), ConvertationActivity.class);
                    mIntent.putExtra(ConvertationActivity.TO_CONVERSION_USER, json);
                    mIntent.putExtra(ConvertationActivity.FROM_CONVERSION_USER,mUsername);
                    startActivity(mIntent);
                }catch (Exception e){}

            }
        }
    }


    public void appendUser(String jsonUsers){
        if(TextUtils.isEmpty(jsonUsers)){
            return;
        }
        mUserList = new ParseFromJsonCommand(jsonUsers,User.class).buildList();

        if(mUserList != null && mUserList.size() > 0)
        {
            mTextViewUserName.setText(mUsername);
            mUserAdapter.addAllUsers(mUserList);

        }
    }
}
