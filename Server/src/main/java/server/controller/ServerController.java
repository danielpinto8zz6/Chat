package server.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Observable;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Command;
import chatroomlibrary.Message;
import chatroomlibrary.NotFoundException;
import chatroomlibrary.User;
import chatroomlibrary.UserDao;
import server.database.DbHelper;
import server.model.Client;
import server.model.Server;

@SuppressWarnings("deprecation")
public class ServerController extends Observable {
    private final Server model;

    public ServerController(Server model) {
        this.model = model;
    }

    public void startServer() {
        Thread thread = new Thread(new ClientReceiver(this));
        thread.start();
    }

    public void stopServer() {
        model.setRunning(false);
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
            DefaultMutableTreeNode sharedFiles = client.getFiles();
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
        try {
            User userFromDb = UserDao.getObject(DbHelper.getConnection(), user.getUsername());

            if (userFromDb.match(user)) {
                // Login succeed
                user.setState(1);
                return true;
            }
        } catch (NotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean register(User user) {
        Connection conn = DbHelper.getConnection();
        try {
            UserDao.create(conn, user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void removeClient(Client client) {
        User user = client.getUser();

        setChanged();
        notifyObservers(new String[] { "user-exited", user.getUsername(), user.getAddress() });

        model.removeClient(client);
    }

    public int getTcpPort() {
        return model.getTcpPort();
    }

    public int getUdpPort() {
        return model.getUdpPort();
    }

    public synchronized void addClient(Client client) {
        model.addClient(client);
        // create a new thread for newUser incoming messages handling
        new Thread(new ClientHandler(this, client)).start();

        User user = client.getUser();

        setChanged();
        notifyObservers(new String[] { "user-joined", user.getUsername(), user.getAddress() });
    }

    public void loginFailed(User user) {
        setChanged();
        notifyObservers(new String[] { "login-failed", user.getUsername(), user.getAddress() });
    }

    public boolean isRunning() {
        return model.isRunning();
    }

}