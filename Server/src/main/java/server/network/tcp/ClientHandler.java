package server.network.tcp;

import java.io.EOFException;
import java.io.IOException;

import chatroomlibrary.Message;
import server.controller.ServerController;
import server.interfaces.IClientManager;
import server.interfaces.IServerListener;
import server.interfaces.IUsersCommunication;
import server.model.Client;

public class ClientHandler implements Runnable {

    private final Client client;
    private IServerListener serverListener;
    private IClientManager clientManager;
    private IUsersCommunication usersCommunication;

    /**
     * <p>
     * Constructor for ClientHandler.
     * </p>
     *
     * @param server a {@link server.Server} object.
     * @param client a {@link server.Client} object.
     */
    public ClientHandler(ServerController controller, Client client) {
        this.client = client;
        serverListener = controller;
        clientManager = controller;
        usersCommunication = controller;

        usersCommunication.notifyUsersList();
    }

    /**
     * <p>
     * run.
     * </p>
     */
    public void run() {
        Message message;

        try {
            while ((message = (Message) this.client.getTcpIn().readObject()) != null) {
                serverListener.onMessageReceived(client, message);
            }
        } catch (EOFException e) {
            // Client disconnected
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                client.getTcpIn().close();
                client.getTcpIn().close();
                client.getTcpSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        clientManager.removeClient(client);
        usersCommunication.notifyUsersList();
        usersCommunication.notifyFilesList();
    }
}
