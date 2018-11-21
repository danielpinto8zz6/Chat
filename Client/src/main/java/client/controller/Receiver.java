package client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import chatroomlibrary.Message;

class Receiver implements Runnable {
    private final ChatController controller;

    public Receiver(ChatController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = (Message) controller.in.readObject();

                if (message == null) {
                    continue;
                }

                switch (message.getAction()) {
                case MESSAGE:
                    if (message.getText() != null) {
                        controller.appendMessage(message);
                    }
                    break;
                case BROADCAST_USERS:
                    String users = message.getText().substring(1, message.getText().length() - 1);
                    controller.updateUsersList(new ArrayList<>(Arrays.asList(users.split(", "))));
                    break;
                case REQUEST_FILE:
                    controller.fileRequest(message);
                    break;
                case FILE_ACCEPTED:
                    controller.fileAccepted(message.getHost());
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