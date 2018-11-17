package chatroom.client;

import java.io.IOException;

import javax.swing.JFrame;

/**
 *
 * @author daniel
 */
public class App {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        try {
            client.run();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

}
