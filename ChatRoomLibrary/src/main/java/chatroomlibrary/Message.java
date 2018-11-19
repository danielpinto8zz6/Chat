package chatroomlibrary;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Action {
        REQUEST_LOGIN, LOGIN, LOGGED, LOGIN_FAILED, MESSAGE, BROADCAST_USERS
    }

    private String text = null;
    private String username = null;
    private String password = null;
    private Action action;
    private LocalDateTime time;

    public Message(String text) {
        this.time = LocalDateTime.now();
        this.action = Action.MESSAGE;
        this.setText(text);
    }

    public Message(String username, String text) {
        this.time = LocalDateTime.now();
        this.action = Action.MESSAGE;
        this.setUsername(username);
        this.setText(text);
    }

    public Message(Action action) {
        this.time = LocalDateTime.now();
        this.action = action;
    }

    public Message(Action action, String text) {
        this.time = LocalDateTime.now();
        this.setAction(action);
        this.setText(text);
    }

    public Message(Action action, String username, String password) {
        this.setAction(action);
        this.setUsername(username);
        this.setPassword(password);
    }

    /**
     * @return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * @return the action
     */
    public Action getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}