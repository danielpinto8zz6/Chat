package client.model;

import java.util.ArrayList;

import chatroomlibrary.Message;
import chatroomlibrary.User;

public class Chat {
    private User user;

    private ArrayList<User> users;
    private ArrayList<Message> messages;

    private ArrayList<Conversation> conversations;

    private String saveLocation;

    public Chat() {
        this.user = new User("username", "localhost", 9001);
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.conversations = new ArrayList<>();
    }

    /**
     * @return the conversation
     */
    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    /**
     * @param conversation the conversation to set
     */
    public void setConversations(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
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

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public Message getLastMessage() {
        if (messages.size() < 1)
            return null;

        Message last = messages.get(messages.size() - 1);
        return last;
    }

    public void addConversation(Conversation conversation) {
        conversations.add(conversation);
	}
}