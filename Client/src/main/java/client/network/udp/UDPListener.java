package client.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import chatroomlibrary.Utils;
import chatroomlibrary.interfaces.IClientListener;
import client.controller.ChatController;

/**
 * <p>UDPListener class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class UDPListener implements Runnable {
    private IClientListener clientListener;
    private final static int BUFFER = 4096;

    DatagramSocket socket = null;
    private ChatController controller;

    /**
     * <p>Constructor for UDPListener.</p>
     *
     * @param controller a {@link client.controller.ChatController} object.
     * @param port a int.
     * @throws java.net.SocketException if any.
     */
    public UDPListener(ChatController controller, int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        clientListener = controller;
        this.controller = controller;
    }

    /** {@inheritDoc} */
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
                    case SEND_FILE:
                        controller.fileRequest(message);
                        break;
                    case FILE_ACCEPTED:
                        controller.fileAccepted(message);
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
                    case KEEP_ALIVE:
                        Message m = new Message(Message.Type.IM_ALIVE, controller.getUser());
                        controller.getCommunication().sendUDPMessage(m, socket.getInetAddress().getHostAddress(),
                                socket.getPort());
                        System.out.println("Keep alive");
                        break;
                    default:
                        break;
                    }
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
