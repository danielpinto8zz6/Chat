package server.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import chatroomlibrary.User;

public class Server {

    private ArrayList<Client> clients;
    private User serverDetails;
    private boolean running = true;

    /**
     * <p>
     * Constructor for Server.
     * </p>
     *
     * @param port a int.
     */
    public Server(int tcpPort, int udpPort) {
        this.clients = new ArrayList<>();

        this.serverDetails = new User("Server", getHostAddress(), tcpPort, udpPort);
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return the serverDetails
     */
    public User getServerDetails() {
        return serverDetails;
    }

    /**
     * @param serverDetails the serverDetails to set
     */
    public void setServerDetails(User serverDetails) {
        this.serverDetails = serverDetails;
    }

    public String getHostAddress() {
        InetAddress hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        return hostAddress.getHostAddress();
    }

    public int getTcpPort() {
        return serverDetails.getTcpPort();
    }

    public int getUdpPort() {
        return serverDetails.getUdpPort();
    }

    public boolean removeClient(Client client) {
        return clients.remove(client);
    }

    public boolean addClient(Client client) {
        return clients.add(client);
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (Client client : clients) {
            users.add(client.getUser());
        }
        return users;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }
}
