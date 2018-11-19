package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import chatroomlibrary.Message;

public class ChatServer {
    /**
     * The set of all names of clients in the chat room. Maintained so that we can
     * check that new clients are not registering name already in use.
     */
    private static HashSet<User> users = new HashSet<User>();

    /**
     * The set of all the outputs for all the clients. This set is kept so we can
     * easily broadcast messages.
     */
    private static HashSet<ObjectOutputStream> outputs = new HashSet<ObjectOutputStream>();

    private DBHelper dbHelper;

    private User user;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    private Socket socket;

    public ChatServer(ServerSocket serverSocket, DBHelper dbHelper) {
        this.dbHelper = dbHelper;

        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                out.writeObject(new Message(Message.Action.REQUEST_LOGIN));
                out.flush();

                Message message = (Message) in.readObject();

                if (message == null || message.getAction() != Message.Action.LOGIN || message.getUsername() == null) {
                    return;
                }

                user = login(message.getUsername(), message.getPassword());

                if (user != null) {
                    synchronized (users) {
                        if (!users.contains(user)) {
                            System.out.println(user.getUsername() + " : Logged");
                            users.add(user);
                            break;
                        }
                    }
                } else {
                    out.writeObject(new Message(Message.Action.LOGIN_FAILED));
                    out.flush();
                }
            }

            // Now that a successful name has been chosen, add the
            // socket's print writer to the set of all writers so
            // this client can receive broadcast messages.
            Message msg = new Message(Message.Action.LOGGED);
            msg.setUsername(user.getUsername());
            out.writeObject(msg);
            out.flush();
            outputs.add(out);

            // Accept messages from this client and broadcast them.
            // Ignore other clients that cannot be broadcasted to.
            while (true) {
                Message message = (Message) in.readObject();
                if (message.getAction() != Message.Action.MESSAGE || message.getText() == null) {
                    return;
                }

                for (ObjectOutputStream output : outputs) {
                    output.writeObject(new Message(user.getUsername(), message.getText()));
                }
            }
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // This client is going down! Remove its name and its print
            // writer from the sets, and close its socket.
            if (user != null) {
                users.remove(user);
            }
            if (out != null) {
                outputs.remove(out);
            }
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public User login(String username, String password) {
        return dbHelper.getUserByUserNameAndPassword(username, password);
    }
}
