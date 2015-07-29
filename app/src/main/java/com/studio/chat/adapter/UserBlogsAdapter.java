package com.studio.chat.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.chat.R;
import com.studio.chat.events.BlogShareEvent;
import com.studio.chat.model.Blogs;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class UserBlogsAdapter extends RecyclerView.Adapter<UserBlogsAdapter.BlogViewHolder> {

    private List<Blogs> mBlogsList = new ArrayList<>();

    private Context mContext;

    public UserBlogsAdapter(Context context) {
        this.mContext = context;
    }

    public Context getApplicationContext() {
        return mContext;
    }

    @Override
    public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blogs, parent, false);
        return new BlogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BlogViewHolder holder, int position) {
        if (!mBlogsList.isEmpty()) {
            Blogs mBlogs = mBlogsList.get(position);
            holder.buildBlogs(mBlogs, position);
        }
    }

    @Override
    public int getItemCount() {
        return mBlogsList.size();
    }

    public void addBlogs(List<Blogs> blogsList) {
        mBlogsList.addAll(blogsList);
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {

        private TextView textview_blog_title;
        private TextView textview_blog_desc;
        private ImageView blog_picture;
        private Button btn_share_blog;
        private int mItemPosition;
        private Blogs tempBlogs;

        public BlogViewHolder(View itemView) {
            super(itemView);
            textview_blog_title = (TextView) itemView.findViewById(R.id.textview_blog_title);
            textview_blog_desc = (TextView) itemView.findViewById(R.id.textview_blog_desc);
            blog_picture = (ImageView) itemView.findViewById(R.id.imageview_blog_picture);
            btn_share_blog = (Button) itemView.findViewById(R.id.btn_share_blog);
            btn_share_blog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new BlogShareEvent().setmBlogs(tempBlogs));
                }
            });
        }

        public void buildBlogs(Blogs blogs, int position) {
            if (blogs != null) {
                tempBlogs = blogs;
                mItemPosition = position;
                textview_blog_title.setText(blogs.getBlogTitle());
                textview_blog_desc.setText(blogs.getBlogDesc());
                final Picasso picasso = Picasso.with(getApplicationContext());
                picasso.setLoggingEnabled(true);
                picasso.setIndicatorsEnabled(true);
                if (!TextUtils.isEmpty(blogs.getBlogImage())) {
                    picasso.load(blogs.getBlogImage()).placeholder(R.drawable.ic_launcher).into(blog_picture);
                }
            }
        }
    }
}
