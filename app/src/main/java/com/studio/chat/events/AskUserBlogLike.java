package com.studio.chat.events;

public class AskUserBlogLike {

    public String fromUserId,toUserId;
    public boolean isForBothUser =false;

    public String getFromUserId() {
        return fromUserId;
    }

    public AskUserBlogLike setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public String getToUserId() {
        return toUserId;
    }

    public AskUserBlogLike setToUserId(String toUserId) {
        this.toUserId = toUserId;
        return this;
    }

    public boolean isForBothUser() {
        return isForBothUser;
    }

    public AskUserBlogLike isForBothUser(boolean isForBothUser) {
        this.isForBothUser = isForBothUser;
        return this;
    }
}
