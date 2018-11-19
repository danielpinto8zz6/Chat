package client;

import client.controller.ChatController;
import client.model.Chat;
import client.view.ChatView;

/**
 *
 * @author daniel
 */
public class App {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Chat chat = new Chat();
        ChatView chatView = new ChatView();
        ChatController chatController = new ChatController(chat, chatView);
    }
}