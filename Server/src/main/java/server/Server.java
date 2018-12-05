package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Command;
import chatroomlibrary.Message;
import chatroomlibrary.User;

class Server {

    private final List<Client> clients;
    private User serv;
    private DBHelper dbHelper;
    boolean testing = true;

    /**
     * <p>
     * Constructor for Server.
     * </p>
     *
     * @param port a int.
     */
    public Server(int port) {
        this.clients = new ArrayList<>();

        try {
            this.serv = new User("Server", InetAddress.getLocalHost().getHostAddress(), port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        dbHelper = new DBHelper("sql2264793", "jG1%xX8!", "sql2.freemysqlhosting.net", "sql2264793");
    }

    /**
     * <p>
     * run.
     * </p>
     */
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(serv.getPort());

            System.out.println("Port " + serv.getPort() + " is now open at " + serv.getHost());

            while (true) {
                // accepts a new client
                Socket socket = server.accept();

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                Command command = (Command) in.readObject();

                User user = command.getMessage().getUser();

                if (!testing) {
                    if (command.getAction() == Command.Action.LOGIN) {
                        if (!authenticate(user) && !testing) {
                            System.out.println("User not autenticated");
                            out.writeObject(new Command(Command.Action.LOGIN_FAILED));
                            out.flush();
                            continue;
                        }
                    } else if (command.getAction() == Command.Action.REGISTER) {
                        if (!register(user)) {
                            out.writeObject(new Command(Command.Action.LOGIN_FAILED));
                            out.flush();
                            continue;
                        }
                    }
                }

                System.out.println("New Client: " + user.getUsername() + "\n\t     Host:"
                        + socket.getInetAddress().getHostAddress());

                Client newClient = new Client(user, socket, in, out);

                // add newUser message to list
                this.clients.add(newClient);

                newClient.getObjectOutputStream().writeObject(new Command(Command.Action.LOGGED));
                newClient.getObjectOutputStream().flush();

                // create a new thread for newUser incoming messages handling
                new Thread(new ClientHandler(this, newClient)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (server != null)
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    // delete a user from the list
    /**
     * <p>
     * removeClient.
     * </p>
     *
     * @param client a {@link server.Client} object.
     */
    public void removeClient(Client client) {
        this.clients.remove(client);
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
        for (Client client : this.clients) {
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
        Command command = new Command(Command.Action.BROADCAST_USERS, new Message(serv, getUsers()));

        for (Client client : this.clients) {
            try {
                client.getObjectOutputStream().writeObject(command);
                client.getObjectOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastFiles() {
        Command command = new Command(Command.Action.BROADCAST_FILES, new Message(serv, getFiles()));

        for (Client client : this.clients) {
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

        for (Client client : clients) {
            DefaultMutableTreeNode sharedFiles = client.getUser().getFiles();
            if (sharedFiles != null)
                files.add(sharedFiles);
        }
        return files;
    }

    private ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (Client client : clients) {
            users.add(client.getUser());
        }
        return users;
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
        for (Client client : this.clients) {
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
            sender.getObjectOutputStream()
                    .writeObject(new Command(Command.Action.MESSAGE, new Message(serv, "User not found")));
            sender.getObjectOutputStream().flush();
        }
    }

    private boolean authenticate(User user) {
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

    private boolean register(User user) {
        dbHelper.open();
        boolean success = dbHelper.insertUser(user);
        dbHelper.close();

        return success;
    }
}
