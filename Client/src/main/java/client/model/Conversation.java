package client.model;

import java.util.ArrayList;

import chatroomlibrary.Message;
import chatroomlibrary.User;

/**
 * <p>Conversation class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class Conversation {
    private ArrayList<Message> messages;
    private User user;

    /**
     * <p>Constructor for Conversation.</p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     */
    public Conversation(User user) {
        this.user = user;
        messages = new ArrayList<>();
    }

    /**
     * <p>addMessage.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void addMessage(Message message) {
        messages.add(message);
    }

    /**
     * <p>Getter for the field <code>messages</code>.</p>
     *
     * @return the messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * <p>Setter for the field <code>messages</code>.</p>
     *
     * @param messages the messages to set
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    /**
     * <p>Getter for the field <code>user</code>.</p>
     *
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * <p>Setter for the field <code>user</code>.</p>
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

}
