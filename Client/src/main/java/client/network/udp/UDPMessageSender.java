package client.network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import chatroomlibrary.Message;
import chatroomlibrary.Utils;

public class UDPMessageSender {
    private DatagramSocket socket;

    public UDPMessageSender() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message, String hostname, int port) throws Exception {
        byte buffer[];
        buffer = Utils.convertToBytes(message);

        InetAddress address = InetAddress.getByName(hostname);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }
}