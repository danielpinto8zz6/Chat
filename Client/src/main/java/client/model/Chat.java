package client.model;

import java.util.ArrayList;

public class Chat {
    private String username;

    private String host;
    private int Port;

    private ArrayList<String> history;

    public Chat() {
        this.setHost("localhost");
        this.username = "User";
        this.setPort(9001);
        history = new ArrayList<String>();
    }

    /**
     * @return the port
     */
    public int getPort() {
        return Port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.Port = port;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void appendHistory(String text) {
        history.add(text);
    }

    public String getLastMessage() {
        if (history.size() < 1)
            return null;

        String last = history.get(history.size() - 1);
        history.remove(history.size() - 1);
        return last;
    }
}