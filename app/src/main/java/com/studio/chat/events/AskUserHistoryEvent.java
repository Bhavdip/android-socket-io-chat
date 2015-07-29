package com.studio.chat.events;

public class AskUserHistoryEvent {

    private String threadId;
    private String fromUserId;
    private String toUserID;
    private String offset;

    public AskUserHistoryEvent(){

    }

    public String getThreadId() {
        return threadId;
    }

    public AskUserHistoryEvent setThreadId(String threadId) {
        this.threadId = threadId;
        return this;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public AskUserHistoryEvent setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public String getToUserID() {
        return toUserID;
    }

    public AskUserHistoryEvent setToUserID(String toUserID) {
        this.toUserID = toUserID;
        return this;
    }

    public String getOffset() {
        return offset;
    }

    public AskUserHistoryEvent setOffset(String offset) {
        this.offset = offset;
        return this;
    }
}
