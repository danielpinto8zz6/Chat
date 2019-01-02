package chatroomlibrary;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * Message class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private User user;
    private Object data;
    private LocalDateTime time;
    private String to = null;
    private Type type;

    /**
     * <p>
     * Constructor for Message.
     * </p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @param data a {@link java.lang.String} object.
     * @param type a {@link chatroomlibrary.Message.Type} object.
     */
    public Message(Type type, User user, Object data) {
        this.time = LocalDateTime.now();
        this.type = type;
        this.user = user;
        this.data = data;
    }

    /**
     * <p>
     * Constructor for Message.
     * </p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @param data a {@link java.lang.String} object.
     * @param to   a {@link java.lang.String} object.
     * @param type a {@link chatroomlibrary.Message.Type} object.
     */
    public Message(Type type, User user, Object data, String to) {
        this.time = LocalDateTime.now();
        this.type = type;
        this.user = user;
        this.data = data;
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
     * <p>Constructor for Message.</p>
     *
     * @param type a {@link chatroomlibrary.Message.Type} object.
     */
    public Message(Type type) {
        this.type = type;
    }

    /**
     * <p>Constructor for Message.</p>
     *
     * @param type a {@link chatroomlibrary.Message.Type} object.
     * @param user a {@link chatroomlibrary.User} object.
     */
    public Message(Type type, User user) {
        this.type = type;
        this.user = user;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * <p>Setter for the field <code>type</code>.</p>
     *
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * <p>Getter for the field <code>data</code>.</p>
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * <p>Setter for the field <code>data</code>.</p>
     *
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * <p>
     * Getter for the field <code>to</code>.
     * </p>
     *
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * <p>
     * Setter for the field <code>to</code>.
     * </p>
     *
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * <p>
     * Getter for the field <code>time</code>.
     * </p>
     *
     * @return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * <p>
     * Setter for the field <code>time</code>.
     * </p>
     *
     * @param time the time to set
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
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

    public enum Type {
        REGISTER, REQUEST_LOGIN, LOGIN, LOGGED, LOGIN_FAILED, MESSAGE, BROADCAST_USERS, SEND_FILE, REQUEST_FILE,
        FILE_ACCEPTED, BROADCAST_FILES, SEND_SHARED_FILES, TRANSFER_ACCEPTED, START_TRANSFER, KEEP_ALIVE, ACK
    }
}
