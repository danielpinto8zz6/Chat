package client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import chatroomlibrary.Message;

public class Receiver implements Runnable {
    ChatController controller;

    public Receiver(ChatController controller) {
        this.controller = controller;
    }

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
                    controller.updateUsersList(new ArrayList<String>(Arrays.asList(message.getText().split(", "))));
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