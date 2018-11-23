package client;

import client.controller.ChatController;
import client.model.Chat;
import client.view.LoginView;
import client.view.MainView;

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
        MainView mainView = new MainView(chatController);
        mainView.setVisible(true);
    }
}