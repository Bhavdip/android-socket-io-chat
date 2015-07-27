package com.studio.chat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;
import com.studio.chat.R;
import com.studio.chat.fragment.BothUserFragment;
import com.studio.chat.fragment.UserBlogFragment;

public class BlogListActivity extends BaseActivity {

    private final String TAG = BlogListActivity.class.getName();
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private BlogListAdapter mBlogListAdapter;

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "BLogListActivity#onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "BLogListActivity#onDestroy");
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
