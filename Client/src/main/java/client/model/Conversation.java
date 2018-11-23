package client.model;

import java.util.ArrayList;

import chatroomlibrary.Message;

public class Conversation {
    private String talkWith;
    private ArrayList<Message> messages;

    public Conversation() {
        messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    /**
     * @return the messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    /**
     * @return the talkWith
     */
    public String getTalkWith() {
        return talkWith;
    }

    /**
     * @param talkWith the talkWith to set
     */
    public void setTalkWith(String talkWith) {
        this.talkWith = talkWith;
    }

}