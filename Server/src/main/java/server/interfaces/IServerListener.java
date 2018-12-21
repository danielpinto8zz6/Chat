package server.interfaces;

import chatroomlibrary.Message;
import server.model.Client;

public interface IServerListener {
    public void onMessageReceived(Client client, Message message);
}