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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public static class Builder {

        private Integer userId;

        private String profilePic;

        private String msgText;

        private Integer msgId;

        private Integer msgFlag;

        private Integer msgType;

        private String userName;

        private String threadId;


        public Integer getUserId() {
            return userId;
        }

        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public Builder setProfilePic(String profilePic) {
            this.profilePic = profilePic;
            return this;
        }

        public String getMsgText() {
            return msgText;
        }

        public Builder setMsgText(String msgText) {
            this.msgText = msgText;
            return this;
        }

        public Integer getMsgId() {
            return msgId;
        }

        public Builder setMsgId(Integer msgId) {
            this.msgId = msgId;
            return this;
        }

        public Integer getMsgFlag() {
            return msgFlag;
        }

        public Builder setMsgFlag(Integer msgFlag) {
            this.msgFlag = msgFlag;
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
            return userName;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public String getThreadId() {
            return threadId;
        }

        public Builder setThreadId(String threadId) {
            this.threadId = threadId;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.userId = userId;
            message.profilePic = profilePic;
            message.msgText = msgText;
            message.msgId = msgId;
            message.msgFlag = msgFlag;
            message.msgType = msgType;
            message.userName = userName;
            message.threadId = threadId;
            return message;
        }
    }

}
