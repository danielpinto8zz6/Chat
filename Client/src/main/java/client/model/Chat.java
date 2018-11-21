package client.model;

import java.util.ArrayList;

import chatroomlibrary.Message;

public class Chat {
    private String username;

    private String host;
    private int Port;

    private ArrayList<String> usersList;
    private ArrayList<Message> messages;

    private String saveLocation;

    public Chat() {
        this.setHost("localhost");
        this.username = "User";
        this.setPort(9001);

        usersList = new ArrayList<>();
        messages = new ArrayList<>();

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

    /**
     * @return the usersList
     */
    public ArrayList<String> getUsersList() {
        return usersList;
    }

    /**
     * @param usersList the usersList to set
     */
    public void setUsersList(ArrayList<String> usersList) {
        this.usersList = usersList;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return Port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.Port = port;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Message getLastMessage() {
        if (messages.size() < 1)
            return null;

        Message last = messages.get(messages.size() - 1);
        return last;
    }
}