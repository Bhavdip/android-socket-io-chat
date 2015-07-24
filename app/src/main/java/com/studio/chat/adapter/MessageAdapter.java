package com.studio.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.chat.R;
import com.studio.chat.model.Message;

import java.util.ArrayList;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public final String TAG = MessageAdapter.class.getSimpleName();

    private List<Message> mMessages = new ArrayList<>();
    private Context mContext;
    private String mUsername;

    public MessageAdapter(Context context, String me) {
        mContext = context;
        this.mUsername = me;
    }

    public Context getApplicationContext() {
        return mContext;
    }

    public void addMessages(List<Message> messageList) {
        mMessages.addAll(messageList);
    }
    public void addMessages(Message newMessage) {
        mMessages.add(newMessage);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case Message.TYPE_MESSAGE:
                layout = R.layout.item_message;
                break;
            case Message.TYPE_BLOG:
                layout = R.layout.item_blog;
                break;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.d(TAG, String.format("onBindViewHolder"));
        viewHolder.buildLayout(position);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getMsgType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
        }

        public void buildLayout(int msgPosition) {
            Message message = mMessages.get(msgPosition);
            if (message.getMsgType() == Message.TYPE_MESSAGE) {
                prepareTextMsg(message);
            } else {
                prepareBlogMsg(message);
            }
        }

        private void prepareTextMsg(Message message) {
            TextView textViewUserName;
            TextView textViewMessage;
            ImageView imageViewProfile;
            if (message.getUserId() == Integer.parseInt(mUsername))
            {
                // left side ( Send the message)
                (mItemView.findViewById(R.id.message_right)).setVisibility(View.VISIBLE);
                (mItemView.findViewById(R.id.message_left)).setVisibility(View.GONE);

                textViewUserName = (TextView) mItemView.findViewById(R.id.receiver);
                textViewMessage = (TextView) mItemView.findViewById(R.id.textview_message_right);
                textViewMessage.setText(message.getMsgText());
                textViewUserName.setText(message.getUserName());
            }
            else
            {
                // right side ( Receive the message)
                (mItemView.findViewById(R.id.message_left)).setVisibility(View.VISIBLE);
                (mItemView.findViewById(R.id.message_right)).setVisibility(View.GONE);
                textViewUserName = (TextView) mItemView.findViewById(R.id.sender);
                textViewMessage = (TextView) mItemView.findViewById(R.id.textview_message_left);
                textViewMessage.setText(message.getMsgText());
                textViewUserName.setText(message.getUserName());
                imageViewProfile = (ImageView)(mItemView.findViewById(R.id.image_profile));
                final Picasso picasso = Picasso.with(getApplicationContext());
                picasso.setLoggingEnabled(true);
                picasso.setIndicatorsEnabled(true);
                if(!TextUtils.isEmpty(message.getProfilePic())){
                    picasso.load(message.getProfilePic()).placeholder(R.drawable.user).into(imageViewProfile);
                }
            }
        }

        private void prepareBlogMsg(Message message) {
        }
    }
}
