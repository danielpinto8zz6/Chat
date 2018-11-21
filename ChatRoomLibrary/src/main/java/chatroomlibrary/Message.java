package chatroomlibrary;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String text = null;
    private String username = null;
    private String password = null;
    private Action action;
    private LocalDateTime time;
    private String host;

    public Message(String text) {
        this.time = LocalDateTime.now();
        this.action = Action.MESSAGE;
        this.text = text;
    }

    public Message(String username, String text) {
        this.time = LocalDateTime.now();
        this.action = Action.MESSAGE;
        this.username = username;
        this.text = text;
    }

    public Message(Action action) {
        this.time = LocalDateTime.now();
        this.action = action;
    }

    public Message(Action action, String text) {
        this.time = LocalDateTime.now();
        this.action = action;
        this.text = text;
    }

    public Message(Action action, String username, String password) {
        this.action = action;
        this.username = username;
        this.password = password;
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

    public enum Action {
        REQUEST_LOGIN, LOGIN, LOGGED, LOGIN_FAILED, MESSAGE, BROADCAST_USERS, REQUEST_FILE, FILE_ACCEPTED
    }
}