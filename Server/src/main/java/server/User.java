package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {
    private String username;
    private Socket socket;

    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;

    // constructor
    public User(Socket socket, String username, ObjectInputStream in, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        this.username = username;

        this.objectOutputStream = out;
        this.objectInputStream = in;
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