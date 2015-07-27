package com.studio.chat.events;

import com.studio.chat.model.Blogs;
import com.studio.chat.utility.ParseFromJsonCommand;

import java.util.ArrayList;
import java.util.List;

public class UserBlogLike {

    private List<Blogs> blogsList = new ArrayList<>();
    private String response;
    public boolean isForBothUser = false;

    public String getResponse() {
        return response;
    }

    public UserBlogLike setResponse(String response) {
        this.response = response;
        return this;
    }

    private void buildBlogList() {
        blogsList = new ParseFromJsonCommand(getResponse(), Blogs.class).buildList();
    }

    public List<Blogs> getBlogsList() {
        return blogsList;
    }

    public UserBlogLike setBlogsList(List<Blogs> blogsList) {
        this.blogsList = blogsList;
        return this;
    }

    public boolean hasBlogsAvailable() {
        buildBlogList();
        if (blogsList != null && blogsList.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isForBothUser() {
        return isForBothUser;
    }

    public UserBlogLike setForBothUser(boolean isForBothUser) {
        this.isForBothUser = isForBothUser;
        return this;
    }
}
