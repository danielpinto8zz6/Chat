package chatroomlibrary;

import java.io.Serializable;

/**
 * <p>Command class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class Command implements Serializable {
    private static final long serialVersionUID = 1L;
    private Action action;
    private Message message;

    /**
     * <p>Constructor for Command.</p>
     *
     * @param action a {@link chatroomlibrary.Command.Action} object.
     */
    public Command(Action action) {
        this.action = action;
    }

    /**
     * <p>Constructor for Command.</p>
     *
     * @param action a {@link chatroomlibrary.Command.Action} object.
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public Command(Action action, Message message) {
        this.action = action;
        this.message = message;
    }

    /**
     * <p>Getter for the field <code>message</code>.</p>
     *
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * <p>Setter for the field <code>message</code>.</p>
     *
     * @param message the message to set
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    /**
     * <p>Getter for the field <code>action</code>.</p>
     *
     * @return the action
     */
    public Action getAction() {
        return action;
    }

    /**
     * <p>Setter for the field <code>action</code>.</p>
     *
     * @param action the action to set
     */
    public void setAction(Action action) {
        this.action = action;
    }

    public enum Action {
        REQUEST_LOGIN, LOGIN, LOGGED, LOGIN_FAILED, MESSAGE, BROADCAST_USERS, REQUEST_FILE, FILE_ACCEPTED
    }
}
