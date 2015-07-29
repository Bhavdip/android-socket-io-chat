package com.studio.chat.utility;

public class Constants {
    //public static final String CHAT_SERVER_URL = "http://54.65.152.9:3000/";
    public static final String CHAT_SERVER_URL = "http://192.168.1.208:3000";

    public static final String NODE_LOGIN  = "login";
    public static final String NODE_ADD_USER = "add user";
    public static final String NODE_SEND_CHAT_MESSAGE = "chat message";
    public static final String NODE_CHAT_RECEIVE = "chat receive";
    public static final String NODE_CHAT_ACK = "chat ack";

    public static final String LISTEN_CHAT_USER_HISTORY = "return_user_history";
    public static final String EMIT_CHAT_USER_HISTORY = "ask_user_history";



    public static final String EMIT_USER_BLOG_LIKE = "ask_user_blog_like";
    public static final String LISTEN_USER_BLOG_LIKE = "return_user_blog_like";

    public static final String EMIT_BOTH_USER_BLOG_LIKE = "ask_bothuser_blog_like";
    public static final String LISTEN_BOTH_USER_BLOG_LIKE = "return_bothuser_blog_like";

    public static final String PREF_KEY_FROM_USER = "fromuser";
    public static final String PREF_KEY_TO_USER = "touser";
}
