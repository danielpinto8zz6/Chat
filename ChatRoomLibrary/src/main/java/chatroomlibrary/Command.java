package chatroomlibrary;

import java.io.Serializable;

public class Command implements Serializable {
    private static final long serialVersionUID = 1L;
    private Action action;
    private Message message;
    private Object extraParameters;

    public Command(Action action) {
        this.action = action;
    }

    public Command(Action action, Message message) {
        this.action = action;
        this.message = message;
    }

    /**
     * @return the extraParameters
     */
    public Object getExtraParameters() {
        return extraParameters;
    }

    /**
     * @param extraParameters the extraParameters to set
     */
    public void setExtraParameters(Object extraParameters) {
        this.extraParameters = extraParameters;
    }

    /**
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(Message message) {
        this.message = message;
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

    public enum Action {
        REQUEST_LOGIN, LOGIN, LOGGED, LOGIN_FAILED, MESSAGE, BROADCAST_USERS, REQUEST_FILE, FILE_ACCEPTED
    }
}