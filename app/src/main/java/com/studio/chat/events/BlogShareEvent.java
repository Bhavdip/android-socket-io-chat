package com.studio.chat.events;

import com.studio.chat.model.Blogs;

public class BlogShareEvent {

    private Blogs mBlogs;

    public Blogs getmBlogs() {
        return mBlogs;
    }

    public BlogShareEvent setmBlogs(Blogs mBlogs) {
        this.mBlogs = mBlogs;
        return this;
    }
}
