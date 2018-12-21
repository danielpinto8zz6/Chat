package server.interfaces;

import server.model.Client;

public interface IClientManager {
    public void addClient(Client client);

    public void removeClient(Client client);
}