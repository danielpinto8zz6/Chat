package client.model;

import java.util.ArrayList;
import java.util.List;

import chatroomlibrary.Message;

public class Chat {
    private String username = "User";
    private List<Message> history;

    public Chat() {
        setHistory(new ArrayList<Message>());
    }

    /**
     * @param message Adds a message to history
     */
    public void addMessage(Message message) {
        history.add(message);
    }

    /**
     * @return the history
     */
    public List<Message> getHistory() {
        return history;
    }

    /**
     * @param history the history to set
     */
    public void setHistory(List<Message> history) {
        this.history = history;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
}