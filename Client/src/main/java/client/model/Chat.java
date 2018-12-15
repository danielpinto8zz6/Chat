package client.model;

import java.io.File;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import chatroomlibrary.Utils;

/**
 * <p>
 * Chat class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class Chat {
    private User user;

    private ArrayList<User> users;
    private ArrayList<Message> messages;

    private ArrayList<Conversation> conversations;

    private File saveLocation;

    private DefaultMutableTreeNode files;

    /**
     * <p>
     * Constructor for Chat.
     * </p>
     */
    public Chat() {
        String address = Utils.getaddress();
        this.user = new User("username", address, 9001, 6001);
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.conversations = new ArrayList<>();
    }

    /**
     * <p>
     * Getter for the field <code>conversations</code>.
     * </p>
     *
     * @return the conversation
     */
    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    /**
     * <p>
     * Setter for the field <code>conversations</code>.
     * </p>
     *
     * @param conversations a {@link java.util.ArrayList} object.
     */
    public void setConversations(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
    }

    /**
     * <p>
     * Getter for the field <code>users</code>.
     * </p>
     *
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * <p>
     * Setter for the field <code>users</code>.
     * </p>
     *
     * @param users the users to set
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * <p>
     * Getter for the field <code>user</code>.
     * </p>
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * <p>
     * Setter for the field <code>user</code>.
     * </p>
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * <p>
     * Getter for the field <code>saveLocation</code>.
     * </p>
     *
     * @return the saveLocation
     */
    public File getSaveLocation() {
        return saveLocation;
    }

    /**
     * <p>
     * Setter for the field <code>saveLocation</code>.
     * </p>
     *
     * @param saveLocation the saveLocation to set
     */
    public void setSaveLocation(File saveLocation) {
        this.saveLocation = saveLocation;
    }

    /**
     * <p>
     * Getter for the field <code>messages</code>.
     * </p>
     *
     * @return the messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * <p>
     * Setter for the field <code>messages</code>.
     * </p>
     *
     * @param messages the messages to set
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    /**
     * <p>
     * addMessage.
     * </p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * <p>
     * getLastMessage.
     * </p>
     *
     * @return a {@link chatroomlibrary.Message} object.
     */
    public Message getLastMessage() {
        if (messages.size() < 1)
            return null;

        Message last = messages.get(messages.size() - 1);
        return last;
    }

    /**
     * <p>
     * addConversation.
     * </p>
     *
     * @param conversation a {@link client.model.Conversation} object.
     */
    public void addConversation(Conversation conversation) {
        conversations.add(conversation);
    }

    public void setFiles(DefaultMutableTreeNode files) {
        this.files = files;
    }

    public DefaultMutableTreeNode getFiles() {
        return files;
    }
}
