package com.studio.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUserList = new ArrayList<>();

    public UsersAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(com.github.nkzawa.socketio.chat.R.layout.item_users, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        User message = mUserList.get(position);
        viewHolder.setName(message.getUserName(), message.getLastMsg(), message.getUnreadMsg(), message.getLastMsgTime());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    public void addAllUsers(List<User> userCollections){
        if(!userCollections.isEmpty()){
            mUserList.addAll(userCollections);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUsernameView;
        private TextView mUnreadMessage;
        private RelativeTimeTextView mMessageTime;
        private TextView mLastMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            mUsernameView = (TextView) itemView.findViewById(com.github.nkzawa.socketio.chat.R.id.username);
            mUnreadMessage = (TextView) itemView.findViewById(com.github.nkzawa.socketio.chat.R.id.unreadmessage);
            mLastMessage = (TextView) itemView.findViewById(com.github.nkzawa.socketio.chat.R.id.lastmessage);
            mMessageTime = (RelativeTimeTextView) itemView.findViewById(com.github.nkzawa.socketio.chat.R.id.time);
        }

        public void setName(String username, String lastmessage, int unreadmessage, String time) {
            mUsernameView.setText(username);
            mUnreadMessage.setText(String.valueOf(unreadmessage));
            mLastMessage.setText(lastmessage);

            if (!TextUtils.isEmpty(time)) {

                DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.JAPAN);
                Date date = null;
                try {
                    date = format.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mMessageTime.setReferenceTime(date.getTime());
            } else {
                mMessageTime.setText("");
            }


        }
    }
}
