package client.model;

import java.util.ArrayList;

import chatroomlibrary.Message;
import chatroomlibrary.User;

public class Conversation {
    private ArrayList<Message> messages;
    private ArrayList<User> users;

    public Conversation() {
        messages = new ArrayList<>();
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    /**
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
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
}