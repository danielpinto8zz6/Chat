package client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import chatroomlibrary.Command;
import chatroomlibrary.Message;
import chatroomlibrary.User;
import client.controller.ChatController;
import client.network.tcp.TcpListener;

public class Client {
    private Socket tcpSocket = null;
    private ObjectOutputStream tcpOut = null;
    private ObjectInputStream tcpIn = null;

    private Thread tcpListener = null;
    private ChatController controller;

    public Client(ChatController controller) {
        this.controller = controller;
    }

    public void startTcp() {
        if (tcpIn == null)
            return;

        tcpListener = new Thread(new TcpListener(controller, tcpIn));
        tcpListener.start();
    }

    public void stopTcp() {
        if (tcpListener != null)
            tcpListener.interrupt();
    }

    public void startUdp() {
    }

    public void stopUdp() {
    }

    public void disconnect() {
        if (tcpListener != null) {
            stopTcp();
            stopUdp();
            try {
                tcpOut.close();
                tcpIn.close();
                tcpSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTcpConnection(User user, Command.Action action) {
        try {
            tcpSocket = new Socket(user.getAddress(), user.getTcpPort());
            tcpIn = new ObjectInputStream(tcpSocket.getInputStream());
            tcpOut = new ObjectOutputStream(tcpSocket.getOutputStream());

            tcpOut.writeObject(new Command(action, new Message(user)));
            tcpOut.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startTcp();
    }

    public void createUdpConnection() {

    }

    public void sendTcpCommand(Command command) {
        try {
            tcpOut.writeObject(command);
            tcpOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}