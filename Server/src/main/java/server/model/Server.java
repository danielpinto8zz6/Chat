package server.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import chatroomlibrary.User;

/**
 * <p>Server class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class Server {

    private ArrayList<Client> clients;
    private User serverDetails;
    private boolean running = true;

    /**
     * <p>
     * Constructor for Server.
     * </p>
     *
     * @param tcpPort a int.
     * @param udpPort a int.
     */
    public Server(int tcpPort, int udpPort) {
        this.clients = new ArrayList<>();

        this.serverDetails = new User("Server", getHostAddress(), tcpPort, udpPort);
    }

    /**
     * <p>isRunning.</p>
     *
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * <p>Setter for the field <code>running</code>.</p>
     *
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * <p>Getter for the field <code>serverDetails</code>.</p>
     *
     * @return the serverDetails
     */
    public User getServerDetails() {
        return serverDetails;
    }

    /**
     * <p>Setter for the field <code>serverDetails</code>.</p>
     *
     * @param serverDetails the serverDetails to set
     */
    public void setServerDetails(User serverDetails) {
        this.serverDetails = serverDetails;
    }

    /**
     * <p>getHostAddress.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getHostAddress() {
        InetAddress hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        return hostAddress.getHostAddress();
    }

    /**
     * <p>getTcpPort.</p>
     *
     * @return a int.
     */
    public int getTcpPort() {
        return serverDetails.getTcpPort();
    }

    /**
     * <p>getUdpPort.</p>
     *
     * @return a int.
     */
    public int getUdpPort() {
        return serverDetails.getUdpPort();
    }

    /**
     * <p>removeClient.</p>
     *
     * @param client a {@link server.model.Client} object.
     * @return a boolean.
     */
    public boolean removeClient(Client client) {
        return clients.remove(client);
    }

    /**
     * <p>addClient.</p>
     *
     * @param client a {@link server.model.Client} object.
     * @return a boolean.
     */
    public boolean addClient(Client client) {
        return clients.add(client);
    }

    /**
     * <p>getUsers.</p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (Client client : clients) {
            users.add(client.getUser());
        }
        return users;
    }

    /**
     * <p>Getter for the field <code>clients</code>.</p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<Client> getClients() {
        return clients;
    }
}
