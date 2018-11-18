package server;

import chatroomlibrary.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import chatroomlibrary.Message.Action;

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

    public ChatServer(ServerSocket serverSocket, DBHelper dbHelper) {
        this.dbHelper = dbHelper;

        try {
            while (true) {
                Thread thread = new Thread(new Handler(serverSocket.accept()));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A handler runnable class. Handlers are spawned from the listening loop and
     * are responsible for a dealing with a single client and broadcasting its
     * messages.
     */
    private class Handler implements Runnable {
        private User user;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        private Socket socket;

        /**
         * Constructs a handler thread, squirreling away the socket. All the interesting
         * work is done in the run method.
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Services this thread's client by repeatedly requesting a screen name until a
         * unique one has been submitted, then acknowledges the name and registers the
         * output stream for the client in a global set, then repeatedly gets inputs and
         * broadcasts them.
         */
        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    out.writeObject(new Message(Message.Action.REQUEST_LOGIN));
                    out.flush();

                    System.out.println("Sending login request");

                    Message message = (Message) in.readObject();

                    if (message.getAction() != Message.Action.LOGIN || message.getUsername() == null) {
                        return;
                    }

                    user = login(message.getUsername(), message.getPassword());

                    if (user != null) {
                        synchronized (users) {
                            if (!users.contains(user)) {
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
                out.writeObject(new Message(Action.LOGGED));
                out.flush();
                outputs.add(out);

                // Accept messages from this client and broadcast them.
                // Ignore other clients that cannot be broadcasted to.
                while (true) {
                    Message message = (Message) in.readObject();
                    if (message.getAction() != Action.MESSAGE || message.getText() == null) {
                        return;
                    }

                    for (ObjectOutputStream output : outputs) {
                        output.writeObject(new Message("MESSAGE " + user.getUsername() + ": " + message.getText()));
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e);
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
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        public User login(String username, String password) {
            return dbHelper.getUserByUserNameAndPassword(username, password);
        }
    }
}