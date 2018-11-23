package server;

import java.io.IOException;

import chatroomlibrary.Command;

class ClientHandler implements Runnable {

    private final Server server;
    private final Client client;

    public ClientHandler(Server server, Client client) {
        this.server = server;
        this.client = client;
        this.server.broadcastAllUsers();
    }

    public void run() {
        Command command;

        try {
            while ((command = (Command) this.client.getObjectInputStream().readObject()) != null) {
                if (command.getMessage().getTo() != null) {
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
