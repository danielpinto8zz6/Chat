package client.model;

import java.util.ArrayList;

import chatroomlibrary.Message;
import chatroomlibrary.User;

public class Chat {
    private User user;

    private ArrayList<User> users;
    private ArrayList<Message> messages;

    private String saveLocation;

    public Chat() {
        this.user = new User("username", "localhost", 9001);

        setUsers(new ArrayList<>());
        messages = new ArrayList<>();
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

    /**
     * @return the user
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

    /**
     * @return the saveLocation
     */
    public String getSaveLocation() {
        return saveLocation;
    }

    /**
     * @param saveLocation the saveLocation to set
     */
    public void setSaveLocation(String saveLocation) {
        this.saveLocation = saveLocation;
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

    public void appendMessage(Message message) {
        this.messages.add(message);
    }

    public Message getLastMessage() {
        if (messages.size() < 1)
            return null;

        Message last = messages.get(messages.size() - 1);
        return last;
    }
}