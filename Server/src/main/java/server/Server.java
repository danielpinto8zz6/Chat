package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import chatroomlibrary.Message;

class Server {

    private final int port;
    private final List<User> clients;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);

            System.out.println("Port " + port + " is now open at " + InetAddress.getLocalHost().getHostAddress());

            while (true) {
                // accepts a new client
                Socket client = server.accept();

                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());

                Message message = (Message) in.readObject();

                String username = message.getUsername();

                System.out.println(
                        "New Client: \"" + username + "\"\n\t     Host:" + client.getInetAddress().getHostAddress());

                // create new User
                User newUser = new User(username, in, out);

                // add newUser message to list
                this.clients.add(newUser);

                newUser.getObjectOutputStream().writeObject(new Message(Message.Action.LOGGED));
                newUser.getObjectOutputStream().flush();

                // create a new thread for newUser incoming messages handling
                new Thread(new UserHandler(this, newUser)).start();
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
    public void removeUser(User user) {
        this.clients.remove(user);
    }

    // send incoming msg to all Users
    public void broadcastMessages(Message message) {
        for (User client : this.clients) {
            try {
                client.getObjectOutputStream().writeObject(message);
                client.getObjectOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // send list of clients to all Users
    public void broadcastAllUsers() {
        Message message = new Message(Message.Action.BROADCAST_USERS, this.clients.toString());

        for (User client : this.clients) {
            try {
                client.getObjectOutputStream().writeObject(message);
                client.getObjectOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToUser(Message message, User sender, String user) throws IOException {
        boolean find = false;
        for (User client : this.clients) {
            if (client.getUsername().equals(user) && client != sender) {
                find = true;
                sender.getObjectOutputStream().writeObject(message);
                sender.getObjectOutputStream().flush();
            }
        }
        if (!find) {
            sender.getObjectOutputStream().writeObject(new Message("User not found"));
            sender.getObjectOutputStream().flush();
        }
    }
}