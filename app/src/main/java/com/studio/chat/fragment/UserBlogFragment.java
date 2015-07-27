package com.studio.chat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.studio.chat.R;
import com.studio.chat.adapter.UserBlogsAdapter;
import com.studio.chat.events.AskUserBlogLike;
import com.studio.chat.events.UserBlogLike;
import com.studio.chat.utility.EmptyRecyclerView;
import com.studio.chat.utility.Prefrence;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class UserBlogFragment extends Fragment {

    private final String TAG = UserBlogFragment.class.getName();

    private EventBus mEventBus = EventBus.getDefault();

    private TextView mTextVieUserName;

    private EmptyRecyclerView mEmptyRecyclerView;

    private UserBlogsAdapter mUserBlogsAdapter;

    public UserBlogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "UserBlogFragment#onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "UserBlogFragment#onCreateView");
        View createdView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_blog, null);
        initialize(createdView);
        return createdView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "UserBlogFragment#onActivityCreated");
        if (mTextVieUserName != null) {
            String fromUserName = Prefrence.getCurrentUser(getActivity(), "");
            if (!TextUtils.isEmpty(fromUserName)) {
                mTextVieUserName.setText(fromUserName);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "UserBlogFragment#onStart");
        mEventBus.register(this);
        askUserBlogLikeList();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "UserBlogFragment#onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "UserBlogFragment#onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "UserBlogFragment#onStop");
        mEventBus.unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "UserBlogFragment#onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "UserBlogFragment#onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "UserBlogFragment#onDetach");
    }

    private void initialize(View createdView) {
        mTextVieUserName = (TextView)createdView.findViewById(R.id.textview_userName);
        mEmptyRecyclerView = (EmptyRecyclerView) createdView.findViewById(R.id.blog_list);
        mEmptyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserBlogsAdapter = new UserBlogsAdapter(getActivity());
        mEmptyRecyclerView.setAdapter(mUserBlogsAdapter);

        // set empty view after set the adapter
        mEmptyRecyclerView.setEmptyView(createdView.findViewById(android.R.id.empty));
    }

    private void askUserBlogLikeList(){
        String fromUser = Prefrence.getCurrentUser(getActivity(),"");
        String toUser = Prefrence.getSelectUser(getActivity(),"");
        mEventBus.post(new AskUserBlogLike().setFromUserId(fromUser).setToUserId(toUser).isForBothUser(false));
        Log.d(TAG, String.format("UserBlogFragment#askUserBlogLikeList[%s,%s]", fromUser,toUser));
    }

    @Subscribe
    public void receiveBlogList(UserBlogLike userBlogLike) {
        if (userBlogLike.hasBlogsAvailable()) {
            if(!userBlogLike.isForBothUser()){
                mUserBlogsAdapter.addBlogs(userBlogLike.getBlogsList());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUserBlogsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

}
