package server.controller;

import java.io.IOException;
import java.util.Observable;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Command;
import chatroomlibrary.Message;
import chatroomlibrary.User;
import server.model.Client;
import server.model.Server;

@SuppressWarnings("deprecation")
public class ServerController extends Observable {
    private final Server model;
    private DBHelper dbHelper;

    public ServerController(Server model) {
        this.model = model;

        dbHelper = new DBHelper("sql2264793", "jG1%xX8!", "sql2.freemysqlhosting.net", "sql2264793");
    }

    public void startServer() {
        Thread thread = new Thread(new Receiver(this));
        thread.start();
    }

    // send incoming msg to all Users
    /**
     * <p>
     * broadcastCommand.
     * </p>
     *
     * @param command a {@link chatroomlibrary.Command} object.
     */
    public void broadcastCommand(Command command) {
        for (Client client : model.getClients()) {
            try {
                client.getObjectOutputStream().writeObject(command);
                client.getObjectOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // send list of clients to all Users
    /**
     * <p>
     * broadcastAllUsers.
     * </p>
     */
    public void broadcastAllUsers() {
        Command command = new Command(Command.Action.BROADCAST_USERS,
                new Message(model.getServerDetails(), model.getUsers()));

        for (Client client : model.getClients()) {
            try {
                client.getObjectOutputStream().writeObject(command);
                client.getObjectOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastFiles() {
        Command command = new Command(Command.Action.BROADCAST_FILES,
                new Message(model.getServerDetails(), getFiles()));

        for (Client client : model.getClients()) {
            try {
                client.getObjectOutputStream().writeObject(command);
                client.getObjectOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private DefaultMutableTreeNode getFiles() {
        DefaultMutableTreeNode files = new DefaultMutableTreeNode(new String("Files"));

        for (Client client : model.getClients()) {
            DefaultMutableTreeNode user = new DefaultMutableTreeNode(client.getUser().getUsername());
            DefaultMutableTreeNode sharedFiles = client.getUser().getFiles();
            if (sharedFiles != null) {
                user.add(sharedFiles);
            }
            files.add(user);
        }
        return files;
    }

    /**
     * <p>
     * sendCommandToUser.
     * </p>
     *
     * @param command a {@link chatroomlibrary.Command} object.
     * @param sender  a {@link server.Client} object.
     * @param user    a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public void sendCommandToUser(Command command, Client sender, String user) throws IOException {
        boolean find = false;
        for (Client client : model.getClients()) {
            if (client.getUser().getUsername().equals(user)) {
                find = true;
                client.getObjectOutputStream().writeObject(command);
                client.getObjectOutputStream().flush();
                if (command.getAction() == Command.Action.MESSAGE) {
                    sender.getObjectOutputStream().writeObject(command);
                    sender.getObjectOutputStream().flush();
                }
            }
        }
        if (!find) {
            sender.getObjectOutputStream().writeObject(
                    new Command(Command.Action.MESSAGE, new Message(model.getServerDetails(), "User not found")));
            sender.getObjectOutputStream().flush();
        }
    }

    public boolean authenticate(User user) {
        dbHelper.open();
        int id = dbHelper.authenticate(user.getUsername(), user.getPassword());
        if (id != -1) {
            user.setId(id);
            user.setState(0);
            dbHelper.updateUser(user);
        }

        dbHelper.close();

        return (id != -1) ? true : false;
    }

    public boolean register(User user) {
        dbHelper.open();
        boolean success = dbHelper.insertUser(user);
        dbHelper.close();

        return success;
    }

    public void removeClient(Client client) {
        User user = client.getUser();

        setChanged();
        notifyObservers(new String[] { "user-exited", user.getUsername(), user.getHost() });

        model.removeClient(client);
    }

    public int getPort() {
        return model.getPort();
    }

    public void addClient(Client client) {
        model.addClient(client);
        // create a new thread for newUser incoming messages handling
        new Thread(new ClientHandler(this, client)).start();

        User user = client.getUser();

        setChanged();
        notifyObservers(new String[] { "user-joined", user.getUsername(), user.getHost() });
    }

    public void loginFailed(User user) {
        setChanged();
        notifyObservers(new String[] { "login-failed", user.getUsername(), user.getHost() });
	}
}
