package com.studio.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;
import com.studio.chat.R;
import com.studio.chat.model.User;

import org.w3c.dom.Text;

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

    public Context getApplicationContext(){
        return  mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        User message = mUserList.get(position);
        viewHolder.buildUser(message);
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
        private TextView mUserid;
        private ImageView profileImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mUsernameView = (TextView) itemView.findViewById(R.id.username);
            mUnreadMessage = (TextView) itemView.findViewById(R.id.unreadmessage);
            mLastMessage = (TextView) itemView.findViewById(R.id.lastmessage);
            mMessageTime = (RelativeTimeTextView) itemView.findViewById(R.id.time);
            mUserid = (TextView)itemView.findViewById(R.id.userid);
            profileImageView = (ImageView)itemView.findViewById(R.id.image_profile);
        }

        public void buildUser(User user){
            mUserid.setText(String.valueOf(user.getUserId()));
            mUsernameView.setText(user.getUserName());
            mUnreadMessage.setText(String.valueOf(user.getUnreadMsg()));
            mLastMessage.setText(user.getLastMsg());
            if (!TextUtils.isEmpty(user.getLastMsgTime())) {

                DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.JAPAN);
                Date date = null;
                try {
                    date = format.parse(user.getLastMsgTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mMessageTime.setReferenceTime(date.getTime());
            } else {
                mMessageTime.setText("");
            }

            final Picasso picasso = Picasso.with(getApplicationContext());
            picasso.setLoggingEnabled(true);
            picasso.setIndicatorsEnabled(true);
            if(!TextUtils.isEmpty(user.getProfilePic())){
                picasso.load(user.getProfilePic()).placeholder(R.drawable.user).into(profileImageView);
            }
        }
    }
}
