package server.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

import chatroomlibrary.Message;
import chatroomlibrary.Utils;
import server.controller.ServerController;
import server.interfaces.IServerListener;

public class UDPListener implements Runnable {
    private IServerListener serverListener;
    private final static int BUFFER = 1024;

    DatagramSocket socket = null;
    private ServerController controller;

    public UDPListener(ServerController controller, int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        serverListener = controller;
        this.controller = controller;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[BUFFER];

        while (controller.isRunning()) {
            try {
                Arrays.fill(buffer, (byte) 0);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                Object readObject = Utils.convertFromBytes(buffer);
                if (readObject instanceof Message) {
                    serverListener.onMessageReceived((Message) readObject);
                } else {
                    System.err.println("Received unrecognized data on UDP socket! Ignoring...");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (socket != null && socket.isConnected()) {
            socket.close();
        }
    }
}