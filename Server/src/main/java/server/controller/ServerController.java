package server.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Message;
import chatroomlibrary.NotFoundException;
import chatroomlibrary.User;
import chatroomlibrary.UserDao;
import server.database.DbHelper;
import server.interfaces.IClientManager;
import server.interfaces.IServerListener;
import server.interfaces.IUsersCommunication;
import server.model.Client;
import server.model.Server;
import server.network.CommunicationHandler;
import server.network.tcp.ClientHandler;

public class ServerController extends Observable implements IServerListener, IClientManager, IUsersCommunication {
    private final Server model;
    private CommunicationHandler communication;

    public ServerController(Server model) {
        this.model = model;
        communication = new CommunicationHandler(this);
    }

    public void stop() {
        model.setRunning(false);
    }

    public synchronized void broadcastMessage(Message message) {
        for (Client client : model.getClients()) {
            try {
                client.getTcpOut().writeObject(message);
                client.getTcpOut().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<User> users = getOtherServersLoggedUsers();
        if (!users.isEmpty())
            for (User user : getOtherServersLoggedUsers()) {
                communication.sendUDPMessage(message, user.getAddress(), 52684);
            }
    }

    public synchronized DefaultMutableTreeNode getFiles() {
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

    public synchronized void sendPrivateMessage(Message message, Client sender, String user) {
        boolean find = false;
        for (Client client : model.getClients()) {
            if (client.getUser().getUsername().equals(user)) {
                find = true;
                try {
                    client.getTcpOut().writeObject(message);
                    client.getTcpOut().flush();
                    if (message.getType() == Message.Type.MESSAGE) {
                        sender.getTcpOut().writeObject(message);
                        sender.getTcpOut().flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!find) {
            try {
                sender.getTcpOut()
                        .writeObject(new Message(Message.Type.MESSAGE, model.getServerDetails(), "User not found"));
                sender.getTcpOut().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean authenticate(User user) {
        Connection connection = DbHelper.getConnection();
        try {
            User userFromDb = UserDao.getObject(connection, user.getUsername());

            if (userFromDb.match(user) && userFromDb.getState() == 0) {
                // Login succeed
                user.setState(1);
                UserDao.save(connection, user);

                return true;
            }
        } catch (NotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public synchronized boolean register(User user) {
        Connection connection = DbHelper.getConnection();
        try {
            UserDao.create(connection, user);
            user.setState(1);
            UserDao.save(connection, user);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (NotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTcpPort() {
        return model.getTcpPort();
    }

    public int getUdpPort() {
        return model.getUdpPort();
    }

    public void loginFailed(User user) {
        String username = user.getUsername();
        String address = user.getAddress();

        setChanged();
        notifyObservers(new String[] { "login-failed", username, address });
    }

    public boolean isRunning() {
        return model.isRunning();
    }

    public String getHostAddress() {
        return model.getHostAddress();
    }

    public synchronized List<User> getUsers() {
        return model.getUsers();
    }

    @Override
    public synchronized void onMessageReceived(Client client, Message message) {
        if (message.getType() == Message.Type.SEND_SHARED_FILES) {
            DefaultMutableTreeNode files = (DefaultMutableTreeNode) message.getData();
            client.setFiles(files);
            notifyFilesList();
        } else if (message.getTo() != null) {
            sendPrivateMessage(message, client, message.getTo());
        } else {
            broadcastMessage(message);
        }
    }

    @Override
    public synchronized void removeClient(Client client) {
        User user = client.getUser();

        user.setState(0);

        try {
            UserDao.save(DbHelper.getConnection(), user);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String username = user.getUsername();
        String address = user.getAddress();

        setChanged();
        notifyObservers(new String[] { "user-exited", username, address });

        model.removeClient(client);
    }

    @Override
    public synchronized void addClient(Client client) {
        model.addClient(client);
        // create a new thread for newUser incoming messages handling
        new Thread(new ClientHandler(this, client)).start();

        User user = client.getUser();

        String username = user.getUsername();
        String address = user.getAddress();

        setChanged();
        notifyObservers(new String[] { "user-joined", username, address });
    }

    @Override
    public synchronized void notifyUsersList() {
        Message message = new Message(Message.Type.BROADCAST_USERS, model.getServerDetails(), model.getUsers());

        for (Client client : model.getClients()) {
            try {
                client.getTcpOut().writeObject(message);
                client.getTcpOut().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void notifyFilesList() {
        Message message = new Message(Message.Type.BROADCAST_FILES, model.getServerDetails(), getFiles());

        for (Client client : model.getClients()) {
            try {
                client.getTcpOut().writeObject(message);
                client.getTcpOut().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized List<User> getOtherServersLoggedUsers() {
        List<User> dbLoggedUsers;
        try {
            dbLoggedUsers = UserDao.loadAll(DbHelper.getConnection(), "WHERE state=1");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        for (User dbUser : dbLoggedUsers) {
            for (User user : model.getUsers()) {
                if (dbUser.getUsername().equals(user.getUsername())) {
                    dbLoggedUsers.remove(dbUser);
                }
            }
        }
        return dbLoggedUsers;
    }
}