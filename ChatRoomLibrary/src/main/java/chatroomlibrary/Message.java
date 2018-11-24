package chatroomlibrary;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>Message class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private User user;
    private String text = null;
    private LocalDateTime time;
    private String to = null;

    /**
     * <p>Constructor for Message.</p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @param text a {@link java.lang.String} object.
     */
    public Message(User user, String text) {
        this.time = LocalDateTime.now();
        this.user = user;
        this.text = text;
    }

    /**
     * <p>Constructor for Message.</p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @param text a {@link java.lang.String} object.
     * @param to a {@link java.lang.String} object.
     */
    public Message(User user, String text, String to) {
        this.time = LocalDateTime.now();
        this.user = user;
        this.text = text;
        this.to = to;
    }

    /**
     * <p>Getter for the field <code>to</code>.</p>
     *
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * <p>Setter for the field <code>to</code>.</p>
     *
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * <p>Constructor for Message.</p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     */
    public Message(User user) {
        this.time = LocalDateTime.now();
        this.user = user;
    }

    /**
     * <p>Getter for the field <code>time</code>.</p>
     *
     * @return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * <p>Setter for the field <code>time</code>.</p>
     *
     * @param time the time to set
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * <p>Getter for the field <code>text</code>.</p>
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * <p>Setter for the field <code>text</code>.</p>
     *
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * <p>Getter for the field <code>user</code>.</p>
     *
     * @return the user
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
