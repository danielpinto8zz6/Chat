package chatroomlibrary;

import java.io.Serializable;

/**
 * <p>
 * User class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private String host;
    private int port;
    private int state;
    private String[] files;

    public User(int id, String username, String password, String host, int port, int state) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.state = state;
    }

    /**
     * <p>
     * Constructor for User.
     * </p>
     *
     * @param username a {@link java.lang.String} object.
     * @param host     a {@link java.lang.String} object.
     * @param port     a int.
     */
    public User(String username, String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
    }

    public User() {
    }

    /**
     * @return the files
     */
    public String[] getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(String[] files) {
        this.files = files;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>
     * Getter for the field <code>username</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * <p>
     * Setter for the field <code>username</code>.
     * </p>
     *
     * @param username a {@link java.lang.String} object.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>
     * Getter for the field <code>host</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * <p>
     * Setter for the field <code>host</code>.
     * </p>
     *
     * @param host a {@link java.lang.String} object.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * <p>
     * Getter for the field <code>port</code>.
     * </p>
     *
     * @return a int.
     */
    public int getPort() {
        return this.port;
    }

    /**
     * <p>
     * Setter for the field <code>port</code>.
     * </p>
     *
     * @param port a int.
     */
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;

        return (username.equals(user.getUsername()) && host.equals(user.getHost()) && port == user.getPort()) ? true
                : false;

    }
}