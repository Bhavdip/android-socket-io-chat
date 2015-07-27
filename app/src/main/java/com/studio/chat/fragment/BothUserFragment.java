package com.studio.chat.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

public class BothUserFragment extends Fragment {

    private final String TAG = BothUserFragment.class.getName();

    private EventBus mEventBus = EventBus.getDefault();

    private TextView mTextVieUserName;
    private EmptyRecyclerView mEmptyRecyclerView;
    private UserBlogsAdapter mBothUsersBlogAdapter;

    public BothUserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BothUserFragment#onCreate");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "BothUserFragment#onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View createdView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_blog, null);
        initialize(createdView);
        return createdView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "BothUserFragment#onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "BothUserFragment#onStart");
        mEventBus.register(this);
        askBothUserBlogLikeList();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "BothUserFragment#onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "BothUserFragment#onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "BothUserFragment#onStop");
        mEventBus.unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "BothUserFragment#onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "BothUserFragment#onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "BothUserFragment#onDetach");
    }

    private void initialize(View createdView) {
        mTextVieUserName = (TextView) createdView.findViewById(R.id.textview_userName);
        mEmptyRecyclerView = (EmptyRecyclerView) createdView.findViewById(R.id.blog_list);
        mEmptyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBothUsersBlogAdapter = new UserBlogsAdapter(getActivity());
        mEmptyRecyclerView.setAdapter(mBothUsersBlogAdapter);

        // set empty view after set the adapter
        mEmptyRecyclerView.setEmptyView(createdView.findViewById(android.R.id.empty));
    }

    private void askBothUserBlogLikeList() {
        String fromUser = Prefrence.getCurrentUser(getActivity(), "");
        String toUser = Prefrence.getSelectUser(getActivity(), "");
        mEventBus.post(new AskUserBlogLike().setFromUserId(fromUser).setToUserId(toUser).isForBothUser(true));
        Log.d(TAG, String.format("BothUserFragment#askBothUserBlogLikeList[%s,%s]", fromUser, toUser));
    }

    @Subscribe
    public void receiveBlogList(UserBlogLike userBlogLike) {
        if (userBlogLike.hasBlogsAvailable()) {
            if(userBlogLike.isForBothUser()){
                mBothUsersBlogAdapter.addBlogs(userBlogLike.getBlogsList());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBothUsersBlogAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
