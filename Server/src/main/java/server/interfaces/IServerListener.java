package server.interfaces;

import chatroomlibrary.Message;
import server.model.Client;

/**
 * <p>IServerListener interface.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public interface IServerListener {
    /**
     * <p>onMessageReceived.</p>
     *
     * @param client a {@link server.model.Client} object.
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void onMessageReceived(Client client, Message message);
    /**
     * <p>onMessageReceived.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void onMessageReceived(Message message);
}
