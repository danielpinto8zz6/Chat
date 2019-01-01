package server.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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

/**
 * <p>ServerController class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class ServerController extends Observable implements IServerListener, IClientManager, IUsersCommunication {
    private final Server model;
    private CommunicationHandler communication;

    /**
     * <p>Constructor for ServerController.</p>
     *
     * @param model a {@link server.model.Server} object.
     */
    public ServerController(Server model) {
        this.model = model;
    }

    /**
     * <p>stop.</p>
     */
    public void stop() {
        model.setRunning(false);
    }

    /**
     * <p>broadcastMessage.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public synchronized void broadcastMessage(Message message) {
        List<User> logged = getLogggedUsers();
        for (Client client : model.getClients()) {
            communication.sendTCPMessage(client, message);
        }
        for (User user : logged) {
            if (user.getAddress() != getHostAddress() && user.getTcpPort() != getTcpPort())
                communication.sendUDPMessage(message, user);
        }
    }

    /**
     * <p>getFiles.</p>
     *
     * @return a {@link javax.swing.tree.DefaultMutableTreeNode} object.
     */
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

    /**
     * <p>sendPrivateMessage.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     * @param sender a {@link server.model.Client} object.
     * @param user a {@link java.lang.String} object.
     */
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
            List<User> users = getLogggedUsers();
            for (User u : users) {
                if (u.getUsername().equals(user)) {
                    communication.sendUDPMessage(message, u);
                    break;
                }
            }
        }
    }

    /**
     * <p>authenticate.</p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @return a boolean.
     */
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

    /**
     * <p>register.</p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @return a boolean.
     */
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

    /**
     * <p>getTcpPort.</p>
     *
     * @return a int.
     */
    public int getTcpPort() {
        return model.getTcpPort();
    }

    /**
     * <p>getUdpPort.</p>
     *
     * @return a int.
     */
    public int getUdpPort() {
        return model.getUdpPort();
    }

    /**
     * <p>loginFailed.</p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     */
    public void loginFailed(User user) {
        String username = user.getUsername();
        String address = user.getAddress();

        setChanged();
        notifyObservers(new String[] { "login-failed", username, address });
    }

    /**
     * <p>isRunning.</p>
     *
     * @return a boolean.
     */
    public boolean isRunning() {
        return model.isRunning();
    }

    /**
     * <p>getHostAddress.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getHostAddress() {
        return model.getHostAddress();
    }

    /**
     * <p>getUsers.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public synchronized List<User> getUsers() {
        return model.getUsers();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void onMessageReceived(Message message) {
        broadcastMessage(message);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void onMessageReceived(Client client, Message message) {
        if (message.getType() == Message.Type.SEND_SHARED_FILES) {
            DefaultMutableTreeNode files = (DefaultMutableTreeNode) message.getData();
            client.setFiles(files);
            try {
                UserDao.save(DbHelper.getConnection(), client.getUser());
            } catch (NotFoundException | SQLException e) {
                e.printStackTrace();
            }
            notifyFilesList();
        } else if (message.getTo() != null) {
            sendPrivateMessage(message, client, message.getTo());
        } else {
            broadcastMessage(message);
        }
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public synchronized void notifyUsersList() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<User> users = getLogggedUsers();
                Message message = new Message(Message.Type.BROADCAST_USERS, model.getServerDetails(), users);

                for (Client client : model.getClients()) {
                    try {
                        client.getTcpOut().writeObject(message);
                        client.getTcpOut().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                for (User user : users) {
                    if (user.getAddress() != getHostAddress() && user.getTcpPort() != getTcpPort())
                        communication.sendUDPMessage(message, user);
                }

                communication.rmiService.notifyUsersList(users);
            }
        }).start();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void notifyFilesList() {
        // List<User> users = getLogggedUsers();
        DefaultMutableTreeNode files = getFiles();
        Message message = new Message(Message.Type.BROADCAST_FILES, model.getServerDetails(), files);

        for (Client client : model.getClients()) {
            try {
                client.getTcpOut().writeObject(message);
                client.getTcpOut().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Send files list by udp
        // for (User user : users) {
        //     if (user.getAddress() != getHostAddress() && user.getTcpPort() != getTcpPort())
        //         communication.sendUDPMessage(message, user);
        // }

        communication.rmiService.notifySharedFiles(files);
    }

    /**
     * <p>getLogggedUsers.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<User> getLogggedUsers() {
        try {
            return UserDao.loadAll(DbHelper.getConnection(), "WHERE state=1");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>exit.</p>
     */
    public void exit() {
        for (User user : model.getUsers()) {
            user.setState(0);
            try {
                UserDao.save(DbHelper.getConnection(), user);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        communication.removeRmi();
    }

    /**
     * <p>Setter for the field <code>communication</code>.</p>
     *
     * @param handler a {@link server.network.CommunicationHandler} object.
     */
    public void setCommunication(CommunicationHandler handler) {
        communication = handler;
    }
}
