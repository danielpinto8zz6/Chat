package client;

import client.controller.ChatController;
import client.model.Chat;
import client.view.ChatView;

/**
 * @author daniel
 */
class App {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Chat chat = new Chat();
        ChatController chatController = new ChatController(chat);
        ChatView chatView = new ChatView(chatController);
    }
}