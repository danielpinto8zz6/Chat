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

    /**
     * <p>Constructor for Client.</p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @param socket a {@link java.net.Socket} object.
     * @param in a {@link java.io.ObjectInputStream} object.
     * @param out a {@link java.io.ObjectOutputStream} object.
     */
    public Client(User user, Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.user = user;
        this.socket = socket;
        this.objectOutputStream = out;
        this.objectInputStream = in;
    }

    /**
     * <p>Getter for the field <code>user</code>.</p>
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * <p>Getter for the field <code>socket</code>.</p>
     *
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * <p>Getter for the field <code>objectOutputStream</code>.</p>
     *
     * @return a {@link java.io.ObjectOutputStream} object.
     */
    public ObjectOutputStream getObjectOutputStream() {
        return this.objectOutputStream;
    }

    /**
     * <p>Getter for the field <code>objectInputStream</code>.</p>
     *
     * @return a {@link java.io.ObjectInputStream} object.
     */
    public ObjectInputStream getObjectInputStream() {
        return this.objectInputStream;
    }

    /**
     * <p>toString.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String toString() {

        return "<u>" + this.getUser().getUsername() + "</u>";

    }
}
