package client.network.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import chatroomlibrary.interfaces.IClientListener;
import client.controller.ChatController;

public class TCPListener implements Runnable {
    private final ChatController controller;
    private ObjectInputStream in = null;
    private IClientListener clientListener;

    /**
     * <p>
     * Constructor for Receiver.
     * </p>
     *
     * @param controller a {@link client.controller.ChatController} object.
     */
    public TCPListener(ChatController controller, ObjectInputStream in) {
        this.controller = controller;
        this.in = in;
        clientListener = controller;
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        while (controller.isRunning()) {
            try {
                Object readObject = in.readObject();
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
                    default:
                        break;
                    }
                } else {
                    System.err.println("Received unrecognized data on TCP socket! Ignoring...");
                }
            } catch (IOException ex) {
                // System.err.println("Failed to parse incoming message");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
