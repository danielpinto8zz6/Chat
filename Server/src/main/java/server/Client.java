package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import chatroomlibrary.User;

class Client {
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private User user;
    private Socket socket;

    // constructor
    public Client(User user, Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.user = user;
        this.socket = socket;
        this.objectOutputStream = out;
        this.objectInputStream = in;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return this.objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return this.objectInputStream;
    }

    public String toString() {

        return "<u>" + this.getUser().getUsername() + "</u>";

    }
}