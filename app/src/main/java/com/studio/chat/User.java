package com.studio.chat;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "unread_msg",
        "profile_pic",
        "last_msg",
        "last_msg_time",
        "user_name",
        "thread_id"
})
public class User {

    @JsonProperty("userId")
    private Integer userId;
    @JsonProperty("unread_msg")
    private Integer unreadMsg;
    @JsonProperty("profile_pic")
    private String profilePic;
    @JsonProperty("last_msg")
    private String lastMsg;
    @JsonProperty("last_msg_time")
    private String lastMsgTime;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("thread_id")
    private Integer threadId;
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
     * @return The unreadMsg
     */
    @JsonProperty("unread_msg")
    public Integer getUnreadMsg() {
        return unreadMsg;
    }

    /**
     * @param unreadMsg The unread_msg
     */
    @JsonProperty("unread_msg")
    public void setUnreadMsg(Integer unreadMsg) {
        this.unreadMsg = unreadMsg;
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
     * @return The lastMsg
     */
    @JsonProperty("last_msg")
    public String getLastMsg() {
        return lastMsg;
    }

    /**
     * @param lastMsg The last_msg
     */
    @JsonProperty("last_msg")
    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    /**
     * @return The lastMsgTime
     */
    @JsonProperty("last_msg_time")
    public String getLastMsgTime() {
        return lastMsgTime;
    }

    /**
     * @param lastMsgTime The last_msg_time
     */
    @JsonProperty("last_msg_time")
    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
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
    public Integer getThreadId() {
        return threadId;
    }

    /**
     * @param threadId The thread_id
     */
    @JsonProperty("thread_id")
    public void setThreadId(Integer threadId) {
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

}