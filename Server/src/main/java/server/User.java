package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class User {
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final String username;

    // constructor
    public User(String username, ObjectInputStream in, ObjectOutputStream out) {
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