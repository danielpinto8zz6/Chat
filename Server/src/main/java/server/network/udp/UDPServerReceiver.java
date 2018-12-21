package server.network.udp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import chatroomlibrary.Message;
import server.controller.ServerController;

public class UDPServerReceiver implements Runnable {
    private DatagramSocket udpSocket;
    private ServerController controller;

    public UDPServerReceiver(ServerController controller, DatagramSocket udpSocket) {
        this.controller = controller;
        this.udpSocket = udpSocket;
    }

    @Override
    public void run() {
        try {
            byte[] incomingData = new byte[1024];

            while (controller.isRunning()) {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                udpSocket.receive(incomingPacket);
                byte[] data = incomingPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                try {
                    Message message = (Message) is.readObject();
                    System.out.println("Object received = " + message);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}