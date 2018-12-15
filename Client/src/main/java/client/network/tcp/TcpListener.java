package client.network.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Command;
import chatroomlibrary.User;
import client.controller.ChatController;

public class TcpListener implements Runnable {
    private final ChatController controller;
    private ObjectInputStream in = null;

    /**
     * <p>
     * Constructor for Receiver.
     * </p>
     *
     * @param controller a {@link client.controller.ChatController} object.
     */
    public TcpListener(ChatController controller, ObjectInputStream in) {
        this.controller = controller;
        this.in = in;
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Command command = (Command) in.readObject();

                if (command == null) {
                    continue;
                }

                switch (command.getAction()) {
                case MESSAGE:
                    if (command.getMessage().getData() != null) {
                        controller.addMessage(command.getMessage());
                    }
                    break;
                case BROADCAST_USERS:
                    @SuppressWarnings("unchecked")
                    ArrayList<User> users = (ArrayList<User>) command.getMessage().getData();
                    controller.updateUsers(users);
                    break;
                case SEND_FILE:
                    controller.fileRequest(command.getMessage());
                    break;
                case FILE_ACCEPTED:
                    controller.fileAccepted(command.getMessage());
                    break;
                case LOGGED:
                    controller.logged();
                    break;
                case LOGIN_FAILED:
                    controller.login_failed();
                    break;
                case BROADCAST_FILES:
                    DefaultMutableTreeNode files = (DefaultMutableTreeNode) command.getMessage().getData();
                    controller.updateFiles(files);
                    break;
                case REQUEST_FILE:
                    controller.fileRequested(command);
                    break;
                case TRANSFER_ACCEPTED:
                    controller.transferAccepted(command.getMessage());
                    break;
                case START_TRANSFER:
                    controller.startTransfer(command.getMessage());
                    break;
                default:
                    break;
                }
            } catch (IOException ex) {
                // System.err.println("Failed to parse incoming message");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
