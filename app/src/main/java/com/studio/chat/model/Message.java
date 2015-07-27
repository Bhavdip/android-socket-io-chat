package com.studio.chat.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "profile_pic",
        "msg_text",
        "msg_id",
        "msg_flag",
        "msg_type",
        "user_name",
        "thread_id"
})
public class Message {

    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_BLOG = 1;

    @JsonProperty("userId")
    private Integer userId;
    @JsonProperty("profile_pic")
    private String profilePic;
    @JsonProperty("msg_text")
    private String msgText;
    @JsonProperty("msg_id")
    private Integer msgId;
    @JsonProperty("msg_flag")
    private Integer msgFlag;
    @JsonProperty("msg_type")
    private Integer msgType;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("thread_id")
    private String threadId;
    @JsonProperty("blog_id")
    private String blogId;
    @JsonProperty("category_id")
    private String categoryId;
    @JsonProperty("blog_title")
    private String blogTitle;
    @JsonProperty("blog_desc")
    private String blogDesc;
    @JsonProperty("blog_image")
    private String blogImage;
    @JsonProperty("msg_time")
    private String msgTime;


    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The userId
     */
    @JsonProperty("userId")
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId The userId
     */
    @JsonProperty("userId")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return The profilePic
     */
    @JsonProperty("profile_pic")
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * @param profilePic The profile_pic
     */
    @JsonProperty("profile_pic")
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * @return The msgText
     */
    @JsonProperty("msg_text")
    public String getMsgText() {
        return msgText;
    }

    /**
     * @param msgText The msg_text
     */
    @JsonProperty("msg_text")
    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    /**
     * @return The msgId
     */
    @JsonProperty("msg_id")
    public Integer getMsgId() {
        return msgId;
    }

    /**
     * @param msgId The msg_id
     */
    @JsonProperty("msg_id")
    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    /**
     * @return The msgFlag
     */
    @JsonProperty("msg_flag")
    public Integer getMsgFlag() {
        return msgFlag;
    }

    /**
     * @param msgFlag The msg_flag
     */
    @JsonProperty("msg_flag")
    public void setMsgFlag(Integer msgFlag) {
        this.msgFlag = msgFlag;
    }

    /**
     * @return The msgType
     */
    @JsonProperty("msg_type")
    public Integer getMsgType() {
        return msgType;
    }

    /**
     * @param msgType The msg_type
     */
    @JsonProperty("msg_type")
    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    /**
     * @return The userName
     */
    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The user_name
     */
    @JsonProperty("user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return The threadId
     */
    @JsonProperty("thread_id")
    public String getThreadId() {
        return threadId;
    }

    /**
     * @param threadId The thread_id
     */
    @JsonProperty("thread_id")
    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    /**
     * @return The blogId
     */
    @JsonProperty("blog_id")
    public String getBlogId() {
        return blogId;
    }

    /**
     * @param blogId The blog_id
     */
    @JsonProperty("blog_id")
    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    /**
     * @return The categoryId
     */
    @JsonProperty("category_id")
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId The category_id
     */
    @JsonProperty("category_id")
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return The blogTitle
     */
    @JsonProperty("blog_title")
    public String getBlogTitle() {
        return blogTitle;
    }

    /**
     * @param blogTitle The blog_title
     */
    @JsonProperty("blog_title")
    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    /**
     * @return The blogDesc
     */
    @JsonProperty("blog_desc")
    public String getBlogDesc() {
        return blogDesc;
    }

    /**
     * @param blogDesc The blog_desc
     */
    @JsonProperty("blog_desc")
    public void setBlogDesc(String blogDesc) {
        this.blogDesc = blogDesc;
    }

    /**
     * @return The blogImage
     */
    @JsonProperty("blog_image")
    public String getBlogImage() {
        return blogImage;
    }

    /**
     * @param blogImage The blog_image
     */
    @JsonProperty("blog_image")
    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }

    /**
     * @return The msgTime
     */
    @JsonProperty("msg_time")
    public String getMsgTime() {
        return msgTime;
    }

    /**
     * @param msgTime The msg_time
     */
    @JsonProperty("msg_time")
    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public static class Builder {

        private Integer mUserId;

        private String mProfilePic;

        private String mMsgText;

        private Integer mMsgId;

        private Integer mMsgFlag;

        private Integer msgType;

        private String mUserName;

        private String mThreadId;

        private String mBlogId;

        private String mCategoryId;

        private String mBlogTitle;

        private String mBlogDesc;

        private String mBlogImage;

        private String mMsgTime;


        public Integer getUserId() {
            return mUserId;
        }

        public Builder setUserId(Integer userId) {
            this.mUserId = userId;
            return this;
        }

        public String getProfilePic() {
            return mProfilePic;
        }

        public Builder setProfilePic(String profilePic) {
            this.mProfilePic = profilePic;
            return this;
        }

        public String getMsgText() {
            return mMsgText;
        }

        public Builder setMsgText(String msgText) {
            this.mMsgText = msgText;
            return this;
        }

        public Integer getMsgId() {
            return mMsgId;
        }

        public Builder setMsgId(Integer msgId) {
            this.mMsgId = msgId;
            return this;
        }

        public Integer getMsgFlag() {
            return mMsgFlag;
        }

        public Builder setMsgFlag(Integer msgFlag) {
            this.mMsgFlag = msgFlag;
            return this;
        }

        public Integer getMsgType() {
            return msgType;
        }

        public Builder setMsgType(Integer msgType) {
            this.msgType = msgType;
            return this;
        }

        public String getUserName() {
            return mUserName;
        }

        public Builder setUserName(String userName) {
            this.mUserName = userName;
            return this;
        }

        public String getThreadId() {
            return mThreadId;
        }

        public Builder setThreadId(String threadId) {
            this.mThreadId = threadId;
            return this;
        }

        public String getBlogId() {
            return mBlogId;
        }

        public Builder setBlogId(String blogId) {
            this.mBlogId = blogId;
            return this;
        }

        public String getCategoryId() {
            return mCategoryId;
        }

        public Builder setCategoryId(String categoryId) {
            this.mCategoryId = categoryId;
            return this;
        }

        public String getBlogTitle() {
            return mBlogTitle;
        }

        public Builder setBlogTitle(String blogTitle) {
            this.mBlogTitle = blogTitle;
            return this;
        }

        public String getBlogImage() {
            return mBlogImage;
        }

        public Builder setBlogImage(String blogImage) {
            this.mBlogImage = blogImage;
            return this;
        }

        public String getMsgTime() {
            return mMsgTime;
        }

        public Builder setMsgTime(String msgTime) {
            this.mMsgTime = msgTime;
            return this;
        }

        public String getBlogDesc() {
            return mBlogDesc;
        }

        public Builder setBlogDesc(String blogDesc) {
            this.mBlogDesc = blogDesc;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.userId = mUserId;
            message.profilePic = mProfilePic;
            message.msgText = mMsgText;
            message.msgId = mMsgId;
            message.msgFlag = mMsgFlag;
            message.msgType = msgType;
            message.userName = mUserName;
            message.threadId = mThreadId;
            message.blogId = mBlogId;
            message.categoryId = mCategoryId;
            message.blogTitle = mBlogTitle;
            message.blogDesc = mBlogDesc;
            message.blogImage = mBlogImage;
            message.msgTime = mMsgTime;
            return message;
        }
    }

}
