package chatroomlibrary;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    String username;
    String host;
    int port;

    public User(String username, String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}