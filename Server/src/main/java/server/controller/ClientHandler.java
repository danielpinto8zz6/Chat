package server.controller;

import java.io.EOFException;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Command;
import server.model.Client;

class ClientHandler implements Runnable {

    private final ServerController controller;
    private final Client client;

    /**
     * <p>
     * Constructor for ClientHandler.
     * </p>
     *
     * @param server a {@link server.Server} object.
     * @param client a {@link server.Client} object.
     */
    public ClientHandler(ServerController controller, Client client) {
        this.controller = controller;
        this.client = client;

        this.controller.broadcastAllUsers();
        if (this.client.getUser().getFiles() != null)
            this.controller.broadcastFiles();
    }

    /**
     * <p>
     * run.
     * </p>
     */
    public void run() {
        Command command;

        try {
            while ((command = (Command) this.client.getObjectInputStream().readObject()) != null) {
                if (command.getAction() == Command.Action.SEND_SHARED_FILES) {
                    DefaultMutableTreeNode files = (DefaultMutableTreeNode) command.getMessage().getData();
                    client.setFiles(files);
                    controller.broadcastFiles();
                } else if (command.getMessage().getTo() != null) {
                    controller.sendCommandToUser(command, client, command.getMessage().getTo());
                } else {
                    controller.broadcastCommand(command);
                }
            }
        } catch (EOFException e) {
            // Client disconnected
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                client.getObjectInputStream().close();
                client.getObjectInputStream().close();
                client.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // end of Thread
        controller.removeClient(client);
        controller.broadcastAllUsers();
    }
}
