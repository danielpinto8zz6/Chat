package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class User {
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final String username;
    private Socket socket;

    // constructor
    public User(String username, Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.username = username;

        this.socket = socket;
        this.objectOutputStream = out;
        this.objectInputStream = in;
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return this.objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return this.objectInputStream;
    }

    // print user with his color
    public String toString() {

        return "<u>" + this.getUsername() + "</u>";

    }
}