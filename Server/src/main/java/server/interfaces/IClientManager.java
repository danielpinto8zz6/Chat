package server.interfaces;

import server.model.Client;

/**
 * <p>IClientManager interface.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public interface IClientManager {
    /**
     * <p>addClient.</p>
     *
     * @param client a {@link server.model.Client} object.
     */
    public void addClient(Client client);

    /**
     * <p>removeClient.</p>
     *
     * @param client a {@link server.model.Client} object.
     */
    public void removeClient(Client client);
}
