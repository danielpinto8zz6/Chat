package client.controller;

import java.io.IOException;
import java.util.ArrayList;

import chatroomlibrary.Command;
import chatroomlibrary.User;

class Receiver implements Runnable {
    private final ChatController controller;

    /**
     * <p>Constructor for Receiver.</p>
     *
     * @param controller a {@link client.controller.ChatController} object.
     */
    public Receiver(ChatController controller) {
        this.controller = controller;
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Command command = (Command) controller.in.readObject();

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
                    ArrayList<User> users = (ArrayList<User>) command.getExtraParameters();
                    controller.updateUsers(users);
                    break;
                case REQUEST_FILE:
                    controller.fileRequest(command.getMessage());
                    break;
                case FILE_ACCEPTED:
                    controller.fileAccepted(command.getMessage());
                    break;
                default:
                    break;
                }
            } catch (IOException ex) {
                System.err.println("Failed to parse incoming message");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
