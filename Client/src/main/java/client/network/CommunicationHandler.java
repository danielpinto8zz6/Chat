package client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import client.controller.ChatController;
import client.network.tcp.TCPListener;
import client.network.udp.UDPListener;
import client.network.udp.UDPMessageSender;

/**
 * <p>
 * CommunicationHandler class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class CommunicationHandler {
    private Socket tcpSocket = null;
    private ObjectOutputStream tcpOut = null;
    private ObjectInputStream tcpIn = null;

    private Thread tcpListener = null;
    private Thread udpListener = null;
    private ChatController controller;

    private UDPMessageSender udpMessageSender;

    /**
     * <p>
     * Constructor for CommunicationHandler.
     * </p>
     *
     * @param controller a {@link client.controller.ChatController} object.
     */
    public CommunicationHandler(ChatController controller) {
        this.controller = controller;
        udpMessageSender = new UDPMessageSender();
    }

    /**
     * <p>
     * startTcp.
     * </p>
     */
    public void startTcp() {
        if (tcpIn == null)
            return;

        tcpListener = new Thread(new TCPListener(controller, tcpIn));
        tcpListener.start();
    }

    /**
     * <p>
     * stopTcp.
     * </p>
     */
    public void stopTcp() {
        if (tcpSocket != null && tcpSocket.isConnected()) {
            try {
                tcpOut.close();
                tcpIn.close();
                tcpSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * startUdp.
     * </p>
     */
    public void startUdp() {
        try {
            udpListener = new Thread(new UDPListener(controller, controller.getUdpPort()));
            udpListener.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * disconnect.
     * </p>
     */
    public void disconnect() {
        stopTcp();
    }

    /**
     * <p>
     * createTcpConnection.
     * </p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @param type a {@link chatroomlibrary.Message.Type} object.
     */
    public void createTcpConnection(User user, Message.Type type) {
        try {
            tcpSocket = new Socket(user.getAddress(), user.getTcpPort());
            tcpIn = new ObjectInputStream(tcpSocket.getInputStream());
            tcpOut = new ObjectOutputStream(tcpSocket.getOutputStream());

            tcpOut.writeObject(new Message(type, user));
            tcpOut.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startTcp();
    }

    /**
     * <p>
     * sendTCPMessage.
     * </p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void sendTCPMessage(Message message) {
        try {
            tcpOut.writeObject(message);
            tcpOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * <p>
     * sendUDPMessage.
     * </p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     * @param user    a {@link chatroomlibrary.User} object.
     */
    public void sendUDPMessage(Message message, User user) {
        try {
            udpMessageSender.sendMessage(message, user.getAddress(), user.getUdpPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * sendUDPMessage.
     * </p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     * @param address a {@link java.lang.String} object.
     * @param port    a int.
     */
    public void sendUDPMessage(Message message, String address, int port) {
        try {
            udpMessageSender.sendMessage(message, address, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>sendUDPMessage.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     * @param address a {@link java.net.InetAddress} object.
     * @param port a int.
     */
    public void sendUDPMessage(Message message, InetAddress address, int port) {
        try {
            udpMessageSender.sendMessage(message, address, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
