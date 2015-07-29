package com.studio.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.studio.chat.R;
import com.studio.chat.events.BlogShareEvent;
import com.studio.chat.fragment.BothUserFragment;
import com.studio.chat.fragment.UserBlogFragment;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class BlogListActivity extends BaseActivity {

    private final String TAG = BlogListActivity.class.getName();

    public final static String INTENT_BLOG_ID = "INTENT_BLOG_ID";

    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private BlogListAdapter mBlogListAdapter;
    private EventBus mEventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list);
        Log.d(TAG, "BLogListActivity#onCreate");

        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.slidingTabStrip);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mBlogListAdapter = new BlogListAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mBlogListAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "BLogListActivity#onResume");
        mEventBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "BLogListActivity#onPause");
        mEventBus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "BLogListActivity#onDestroy");
    }

    @Subscribe
    public void onBlogShareEvent(BlogShareEvent blogShareEvent){
        Log.d(TAG, String.format("BlogListActivity#BlogShareEvent#%s", blogShareEvent.getmBlogs().getBlogId()));
        Intent bundleIntent = new Intent();
        bundleIntent.putExtra(INTENT_BLOG_ID,blogShareEvent.getmBlogs().getBlogId());
        setResult(RESULT_OK,bundleIntent);
        finish();
    }

    public class BlogListAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"UserBlog", "BothBlog"};

        public BlogListAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return UserBlogFragment.instantiate(getApplicationContext(), UserBlogFragment.class.getName());
            } else {
                return BothUserFragment.instantiate(getApplicationContext(), BothUserFragment.class.getName());
            }
        }

    }

}
