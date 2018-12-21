package client.network.udp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import chatroomlibrary.interfaces.IClientListener;
import client.controller.ChatController;

public class UDPListener implements Runnable {
    private IClientListener clientListener;
    private DatagramSocket socket;

    public UDPListener(ChatController controller, DatagramSocket socket) {
        this.socket = socket;
        clientListener = controller;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            byte[] buffer = new byte[1024];
            try {
                socket.receive(new DatagramPacket(buffer, 1024));

                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = new ObjectInputStream(bais);

                try {
                    Object readObject = ois.readObject();
                    if (readObject instanceof Message) {
                        Message message = (Message) readObject;

                        switch (message.getType()) {
                        case MESSAGE:
                            if (message.getData() != null) {
                                clientListener.onMessageReceived(message);
                            }
                            break;
                        case BROADCAST_USERS:
                            @SuppressWarnings("unchecked")
                            List<User> users = (List<User>) message.getData();
                            clientListener.onUserListReceived(users);
                            break;
                        case LOGGED:
                            clientListener.onLogged();
                            break;
                        case LOGIN_FAILED:
                            clientListener.onLoginFailed();
                            break;
                        case BROADCAST_FILES:
                            DefaultMutableTreeNode files = (DefaultMutableTreeNode) message.getData();
                            clientListener.onFilesListReceived(files);
                            break;
                        case REQUEST_FILE:
                            clientListener.onFileRequested(message);
                            break;
                        case TRANSFER_ACCEPTED:
                            clientListener.onTransferAccepted(message);
                            break;
                        case START_TRANSFER:
                            clientListener.onStartTransfer(message);
                            break;
                        default:
                            break;
                        }
                    } else {
                        System.err.println("Received unrecognized data on UDP socket! Ignoring...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}