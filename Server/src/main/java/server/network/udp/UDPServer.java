package server.network.udp;

import java.net.DatagramSocket;
import java.net.SocketException;

import server.controller.ServerController;

public class UDPServer {
    private ServerController controller;
    private DatagramSocket udpSocket;
    private int port;

    public UDPServer(ServerController controller, int port) {
        this.controller = controller;
        this.port = port;

        try {
            this.udpSocket = new DatagramSocket(this.port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread thread = new Thread(new UDPServerReceiver(controller, udpSocket));
        thread.start();
    }
}