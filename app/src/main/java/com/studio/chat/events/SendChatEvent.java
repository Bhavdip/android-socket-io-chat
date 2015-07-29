package com.studio.chat.events;

public class SendChatEvent {
    String message;
    String fromUserId;
    String toUserId;
    String msgType;
    String threadId;

    public String getMessage() {
        return message;
    }

    public SendChatEvent setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public SendChatEvent setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public String getToUserId() {
        return toUserId;
    }

    public SendChatEvent setToUserId(String toUserId) {
        this.toUserId = toUserId;
        return this;
    }

    public String getMsgType() {
        return msgType;
    }

    public SendChatEvent setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getThreadId() {
        return threadId;
    }

    public SendChatEvent setThreadId(String threadId) {
        this.threadId = threadId;
        return this;
    }
}
