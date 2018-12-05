package server;

import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Command;

class ClientHandler implements Runnable {

    private final Server server;
    private final Client client;

    /**
     * <p>
     * Constructor for ClientHandler.
     * </p>
     *
     * @param server a {@link server.Server} object.
     * @param client a {@link server.Client} object.
     */
    public ClientHandler(Server server, Client client) {
        this.server = server;
        this.client = client;
        this.server.broadcastAllUsers();
        if (this.client.getUser().getFiles() != null)
            this.server.broadcastFiles();
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
                    client.getUser().setFiles(files);
                    server.broadcastFiles();
                } else if (command.getMessage().getTo() != null) {
                    server.sendCommandToUser(command, client, command.getMessage().getTo());
                } else {
                    server.broadcastCommand(command);
                }
            }
        } catch (IOException e) {
            System.out.println(client.getUser().getUsername() + " : has disconnected!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                client.getSocket().close();
                client.getObjectInputStream().close();
                client.getObjectInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // end of Thread
        server.removeClient(client);
        this.server.broadcastAllUsers();
    }
}
