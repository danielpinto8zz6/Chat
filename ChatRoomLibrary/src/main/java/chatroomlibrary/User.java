package chatroomlibrary;

import java.io.Serializable;

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
     */

    public User(String username, String address, int tcpPort, int udpPort) {
        this.username = username;
        this.address = address;
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }

    public User() {

    }

    public User(String usernameIn) {

        this.username = usernameIn;

    }

    /**
     * Get- and Set-methods for persistent variables. The default behaviour does not
     * make any checks against malformed data, so these might require some manual
     * additions.
     */

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String usernameIn) {
        this.username = usernameIn;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String passwordIn) {
        this.password = passwordIn;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int stateIn) {
        this.state = stateIn;
    }

    public int getTcpPort() {
        return this.tcpPort;
    }

    public void setTcpPort(int tcpPortIn) {
        this.tcpPort = tcpPortIn;
    }

    public int getUdpPort() {
        return this.udpPort;
    }

    public void setUdpPort(int udpPortIn) {
        this.udpPort = udpPortIn;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String adressIn) {
        this.address = adressIn;
    }

    public int getFailures() {
        return this.failures;
    }

    public void setFailures(int failuresIn) {
        this.failures = failuresIn;
    }

    /**
     * setAll allows to set all persistent variables in one method call. This is
     * useful, when all data is available and it is needed to set the initial state
     * of this object. Note that this method will directly modify instance variales,
     * without going trough the individual set-methods.
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
     */
    public String getDaogenVersion() {
        return "DaoGen version 2.4.1";
    }

    public boolean match(User user) {
        return (this.username.equals(user.getUsername()) && this.password.equals(user.getPassword()));
    }

}
