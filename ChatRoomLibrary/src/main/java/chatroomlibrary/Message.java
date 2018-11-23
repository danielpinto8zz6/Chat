package chatroomlibrary;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private User user;
    private String text = null;
    private LocalDateTime time;
    private String to = null;

    public Message(User user, String text) {
        this.time = LocalDateTime.now();
        this.user = user;
        this.text = text;
    }

    public Message(User user, String text, String to) {
        this.time = LocalDateTime.now();
        this.user = user;
        this.text = text;
        this.to = to;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    public Message(User user) {
        this.time = LocalDateTime.now();
        this.user = user;
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
}