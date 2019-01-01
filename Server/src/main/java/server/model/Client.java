package server.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.User;

/**
 * <p>Client class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class Client {
    private final ObjectOutputStream tcpOut;
    private final ObjectInputStream tcpIn;
    private User user;
    private Socket tcpSocket;
    private DefaultMutableTreeNode files;

    /**
     * <p>
     * Constructor for Client.
     * </p>
     *
     * @param user   a {@link chatroomlibrary.User} object.
     * @param tcpSocket a {@link java.net.Socket} object.
     * @param tcpIn a {@link java.io.ObjectInputStream} object.
     * @param tcpOut a {@link java.io.ObjectOutputStream} object.
     */
    public Client(User user, Socket tcpSocket, ObjectInputStream tcpIn, ObjectOutputStream tcpOut) {
        this.user = user;
        this.tcpSocket = tcpSocket;
        this.tcpOut = tcpOut;
        this.tcpIn = tcpIn;
    }

    /**
     * <p>Getter for the field <code>tcpOut</code>.</p>
     *
     * @return the tcpOut
     */
    public ObjectOutputStream getTcpOut() {
        return tcpOut;
    }

    /**
     * <p>Getter for the field <code>tcpIn</code>.</p>
     *
     * @return the tcpIn
     */
    public ObjectInputStream getTcpIn() {
        return tcpIn;
    }

    /**
     * <p>Getter for the field <code>tcpSocket</code>.</p>
     *
     * @return the tcpSocket
     */
    public Socket getTcpSocket() {
        return tcpSocket;
    }

    /**
     * <p>Getter for the field <code>files</code>.</p>
     *
     * @return the files
     */
    public DefaultMutableTreeNode getFiles() {
        return files;
    }

    /**
     * <p>Setter for the field <code>files</code>.</p>
     *
     * @param files the files to set
     */
    public void setFiles(DefaultMutableTreeNode files) {
        this.files = files;
    }

    /**
     * <p>
     * Getter for the field <code>user</code>.
     * </p>
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * <p>
     * toString.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String toString() {
        return "<u>" + this.getUser().getUsername() + "</u>";
    }
}
