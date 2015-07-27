package com.studio.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "category_id:",
        "blog_id",
        "blog_title",
        "blog_desc",
        "blog_image"
})

public class Blogs {

    @JsonProperty("category_id")
    private String categoryId;
    @JsonProperty("blog_id")
    private String blogId;
    @JsonProperty("blog_title")
    private String blogTitle;
    @JsonProperty("blog_desc")
    private String blogDesc;
    @JsonProperty("blog_image")
    private String blogImage;


    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCategoryId() {
        return categoryId;
    }

    public Blogs setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getBlogId() {
        return blogId;
    }

    public Blogs setBlogId(String blogId) {
        this.blogId = blogId;
        return this;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public Blogs setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
        return this;
    }

    public String getBlogDesc() {
        return blogDesc;
    }

    public Blogs setBlogDesc(String blogDesc) {
        this.blogDesc = blogDesc;
        return this;
    }

    public String getBlogImage() {
        return blogImage;
    }

    public Blogs setBlogImage(String blogImage) {
        this.blogImage = blogImage;
        return this;
    }

    public static class Builder {

        private String mCategoryId;

        private String mBlogId;

        private String mBlogTitle;

        private String mBlogDesc;

        private String mBlogImage;

        public String getmCategoryId() {
            return mCategoryId;
        }

        public Builder setmCategoryId(String mCategoryId) {
            this.mCategoryId = mCategoryId;
            return this;
        }

        public String getmBlogId() {
            return mBlogId;
        }

        public Builder setmBlogId(String mBlogId) {
            this.mBlogId = mBlogId;
            return this;
        }

        public String getmBlogTitle() {
            return mBlogTitle;
        }

        public Builder setmBlogTitle(String mBlogTitle) {
            this.mBlogTitle = mBlogTitle;
            return this;
        }

        public String getmBlogDesc() {
            return mBlogDesc;
        }

        public Builder setmBlogDesc(String mBlogDesc) {
            this.mBlogDesc = mBlogDesc;
            return this;
        }

        public String getmBlogImage() {
            return mBlogImage;
        }

        public Builder setmBlogImage(String mBlogImage) {
            this.mBlogImage = mBlogImage;
            return this;
        }

        public Blogs buildBlog(){
        return new Blogs()
                .setBlogId(getmBlogId())
                .setCategoryId(getmCategoryId())
                .setBlogTitle(getmBlogTitle())
                .setBlogDesc(getmBlogDesc())
                .setBlogImage(getmBlogImage());
        }
    }
}
