package client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import client.controller.ChatController;
import client.network.tcp.TCPListener;
import client.network.udp.UDPListener;

public class Client {
    private Socket tcpSocket = null;
    private ObjectOutputStream tcpOut = null;
    private ObjectInputStream tcpIn = null;

    private DatagramSocket udpSocket = null;
    private ObjectOutputStream udpOut = null;
    private ObjectInputStream udpIn = null;

    private Thread tcpListener = null;
    private Thread udpListener = null;
    private ChatController controller;

    public Client(ChatController controller) {
        this.controller = controller;
    }

    public void startTcp() {
        if (tcpIn == null)
            return;

        tcpListener = new Thread(new TCPListener(controller, tcpIn));
        tcpListener.start();
    }

    public void stopTcp() {
        if (tcpListener != null)
            tcpListener.interrupt();

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

    public void startUdp() {
        udpListener = new Thread(new UDPListener(controller, udpSocket));
        udpListener.start();
    }

    public void stopUdp() {
        if (udpListener != null)
            udpListener.interrupt();

        if (udpSocket != null && udpSocket.isConnected()) {
            udpSocket.close();
        }
    }

    public void disconnect() {
        stopTcp();
        stopUdp();
    }

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

    public void createUdpConnection() {
        try {
            udpSocket = new DatagramSocket(controller.getUdpPort());
        } catch (SocketException e) {
            e.printStackTrace();
        }

        startUdp();
    }

    public void sendTcpMessage(Message message) {
        try {
            tcpOut.writeObject(message);
            tcpOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void sendUdpMessage(Message message) {

    }
}