package com.studio.chat.events;

import com.studio.chat.model.Message;
import com.studio.chat.utility.ParseFromJsonCommand;

import java.util.ArrayList;
import java.util.List;

public class ReceiveMsgEvent {

    private int messageType;
    private String response;
    private List<Message> messages = new ArrayList<>();

    public ReceiveMsgEvent() {

    }

    public int getMessageType() {
        return messageType;
    }

    public ReceiveMsgEvent setMessageType(int messageType) {
        this.messageType = messageType;
        return this;
    }

    /**
     * If 0 = receive message 1 = Ack/Sent Message 2 = history message
     * @return
     */
    public String getResponse() {
        return response;
    }

    public ReceiveMsgEvent setResponse(String response) {
        this.response = response;
        return this;
    }

    public List<Message> receiveMessages() {
        return messages;
    }

    private void buildMessages(){
        messages = new ParseFromJsonCommand(getResponse(), Message.class).buildList();
    }

    public boolean hasMessages() {
        buildMessages();
        if (messages != null && messages.size() > 0) {
            return true;
        }
        return false;
    }

}
