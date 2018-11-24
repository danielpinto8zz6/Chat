package client.model;

import java.util.ArrayList;

import chatroomlibrary.Message;
import chatroomlibrary.User;

public class Conversation {
    private ArrayList<Message> messages;
    private User user;

    public Conversation(User user) {
        this.user = user;
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
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

}