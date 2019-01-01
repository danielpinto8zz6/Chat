package chatroomlibrary;

import java.io.Serializable;

/**
 * <p>User class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class User implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Persistent Instance variables. This data is directly mapped to the columns of
     * database table.
     */
    private String username;
    private String password;
    private int state;
    private int tcpPort;
    private int udpPort;
    private String address;
    private int failures;

    /**
     * Constructors. DaoGen generates two constructors by default. The first one
     * takes no arguments and provides the most simple way to create object
     * instance. The another one takes one argument, which is the primary key of the
     * corresponding table.
     *
     * @param username a {@link java.lang.String} object.
     * @param address a {@link java.lang.String} object.
     * @param tcpPort a int.
     * @param udpPort a int.
     */
    public User(String username, String address, int tcpPort, int udpPort) {
        this.username = username;
        this.address = address;
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }

    /**
     * <p>Constructor for User.</p>
     */
    public User() {

    }

    /**
     * <p>Constructor for User.</p>
     *
     * @param usernameIn a {@link java.lang.String} object.
     */
    public User(String usernameIn) {

        this.username = usernameIn;

    }

    /**
     * Get- and Set-methods for persistent variables. The default behaviour does not
     * make any checks against malformed data, so these might require some manual
     * additions.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * <p>Setter for the field <code>username</code>.</p>
     *
     * @param usernameIn a {@link java.lang.String} object.
     */
    public void setUsername(String usernameIn) {
        this.username = usernameIn;
    }

    /**
     * <p>Getter for the field <code>password</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * <p>Setter for the field <code>password</code>.</p>
     *
     * @param passwordIn a {@link java.lang.String} object.
     */
    public void setPassword(String passwordIn) {
        this.password = passwordIn;
    }

    /**
     * <p>Getter for the field <code>state</code>.</p>
     *
     * @return a int.
     */
    public int getState() {
        return this.state;
    }

    /**
     * <p>Setter for the field <code>state</code>.</p>
     *
     * @param stateIn a int.
     */
    public void setState(int stateIn) {
        this.state = stateIn;
    }

    /**
     * <p>Getter for the field <code>tcpPort</code>.</p>
     *
     * @return a int.
     */
    public int getTcpPort() {
        return this.tcpPort;
    }

    /**
     * <p>Setter for the field <code>tcpPort</code>.</p>
     *
     * @param tcpPortIn a int.
     */
    public void setTcpPort(int tcpPortIn) {
        this.tcpPort = tcpPortIn;
    }

    /**
     * <p>Getter for the field <code>udpPort</code>.</p>
     *
     * @return a int.
     */
    public int getUdpPort() {
        return this.udpPort;
    }

    /**
     * <p>Setter for the field <code>udpPort</code>.</p>
     *
     * @param udpPortIn a int.
     */
    public void setUdpPort(int udpPortIn) {
        this.udpPort = udpPortIn;
    }

    /**
     * <p>Getter for the field <code>address</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * <p>Setter for the field <code>address</code>.</p>
     *
     * @param adressIn a {@link java.lang.String} object.
     */
    public void setAddress(String adressIn) {
        this.address = adressIn;
    }

    /**
     * <p>Getter for the field <code>failures</code>.</p>
     *
     * @return a int.
     */
    public int getFailures() {
        return this.failures;
    }

    /**
     * <p>Setter for the field <code>failures</code>.</p>
     *
     * @param failuresIn a int.
     */
    public void setFailures(int failuresIn) {
        this.failures = failuresIn;
    }

    /**
     * setAll allows to set all persistent variables in one method call. This is
     * useful, when all data is available and it is needed to set the initial state
     * of this object. Note that this method will directly modify instance variales,
     * without going trough the individual set-methods.
     *
     * @param usernameIn a {@link java.lang.String} object.
     * @param passwordIn a {@link java.lang.String} object.
     * @param stateIn a int.
     * @param tcpPortIn a int.
     * @param udpPortIn a int.
     * @param adressIn a {@link java.lang.String} object.
     * @param filesIn a {@link java.lang.String} object.
     * @param failuresIn a int.
     */
    public void setAll(String usernameIn, String passwordIn, int stateIn, int tcpPortIn, int udpPortIn, String adressIn,
            String filesIn, int failuresIn) {
        this.username = usernameIn;
        this.password = passwordIn;
        this.state = stateIn;
        this.tcpPort = tcpPortIn;
        this.udpPort = udpPortIn;
        this.address = adressIn;
        this.failures = failuresIn;
    }

    /**
     * hasEqualMapping-method will compare two User instances and return true if
     * they contain same values in all persistent instance variables. If
     * hasEqualMapping returns true, it does not mean the objects are the same
     * instance. However it does mean that in that moment, they are mapped to the
     * same row in database.
     *
     * @param valueObject a {@link chatroomlibrary.User} object.
     * @return a boolean.
     */
    public boolean hasEqualMapping(User valueObject) {

        if (this.username == null) {
            if (valueObject.getUsername() != null)
                return (false);
        } else if (!this.username.equals(valueObject.getUsername())) {
            return (false);
        }
        if (this.password == null) {
            if (valueObject.getPassword() != null)
                return (false);
        } else if (!this.password.equals(valueObject.getPassword())) {
            return (false);
        }
        if (valueObject.getState() != this.state) {
            return (false);
        }
        if (valueObject.getTcpPort() != this.tcpPort) {
            return (false);
        }
        if (valueObject.getUdpPort() != this.udpPort) {
            return (false);
        }
        if (this.address == null) {
            if (valueObject.getAddress() != null)
                return (false);
        } else if (!this.address.equals(valueObject.getAddress())) {
            return (false);
        }
        if (valueObject.getFailures() != this.failures) {
            return (false);
        }

        return true;
    }

    /**
     * toString will return String object representing the state of this
     * valueObject. This is useful during application development, and possibly when
     * application is writing object states in textlog.
     *
     * @return a {@link java.lang.String} object.
     */
    public String toString() {
        StringBuffer out = new StringBuffer(this.getDaogenVersion());
        out.append("\nclass User, mapping to table users\n");
        out.append("Persistent attributes: \n");
        out.append("username = " + this.username + "\n");
        out.append("password = " + this.password + "\n");
        out.append("state = " + this.state + "\n");
        out.append("tcpPort = " + this.tcpPort + "\n");
        out.append("udpPort = " + this.udpPort + "\n");
        out.append("adress = " + this.address + "\n");
        out.append("failures = " + this.failures + "\n");
        return out.toString();
    }

    /**
     * Clone will return identical deep copy of this valueObject. Note, that this
     * method is different than the clone() which is defined in java.lang.Object.
     * Here, the retuned cloned object will also have all its attributes cloned.
     *
     * @return a {@link java.lang.Object} object.
     */
    public Object clone() {
        User cloned = new User();

        if (this.username != null)
            cloned.setUsername(new String(this.username));
        if (this.password != null)
            cloned.setPassword(new String(this.password));
        cloned.setState(this.state);
        cloned.setTcpPort(this.tcpPort);
        cloned.setUdpPort(this.udpPort);
        if (this.address != null)
            cloned.setAddress(new String(this.address));
        cloned.setFailures(this.failures);
        return cloned;
    }

    /**
     * getDaogenVersion will return information about generator which created these
     * sources.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDaogenVersion() {
        return "DaoGen version 2.4.1";
    }

    /**
     * <p>match.</p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @return a boolean.
     */
    public boolean match(User user) {
        return (this.username.equals(user.getUsername()) && this.password.equals(user.getPassword()));
    }

}
