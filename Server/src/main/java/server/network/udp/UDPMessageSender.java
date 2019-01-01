package server.network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import chatroomlibrary.Message;
import chatroomlibrary.Utils;

/**
 * <p>UDPMessageSender class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class UDPMessageSender {
    private DatagramSocket socket;

    /**
     * <p>Constructor for UDPMessageSender.</p>
     */
    public UDPMessageSender() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>sendMessage.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     * @param hostname a {@link java.lang.String} object.
     * @param port a int.
     * @throws java.lang.Exception if any.
     */
    public void sendMessage(Message message, String hostname, int port) throws Exception {
        byte buffer[];
        buffer = Utils.convertToBytes(message);

        InetAddress address = InetAddress.getByName(hostname);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }
}
