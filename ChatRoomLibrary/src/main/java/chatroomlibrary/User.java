package chatroomlibrary;

import java.io.Serializable;

/**
 * <p>User class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    String username;
    String host;
    int port;

    /**
     * <p>Constructor for User.</p>
     *
     * @param username a {@link java.lang.String} object.
     * @param host a {@link java.lang.String} object.
     * @param port a int.
     */
    public User(String username, String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
    }

    /**
     * <p>Getter for the field <code>username</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * <p>Setter for the field <code>username</code>.</p>
     *
     * @param username a {@link java.lang.String} object.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>Getter for the field <code>host</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * <p>Setter for the field <code>host</code>.</p>
     *
     * @param host a {@link java.lang.String} object.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * <p>Getter for the field <code>port</code>.</p>
     *
     * @return a int.
     */
    public int getPort() {
        return this.port;
    }

    /**
     * <p>Setter for the field <code>port</code>.</p>
     *
     * @param port a int.
     */
    public void setPort(int port) {
        this.port = port;
    }
}
