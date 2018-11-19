package server;

import java.io.IOException;

/**
 *
 * @author daniel
 */
public class App {

    /**
     * The port that the server listens on.
     */
    private static final int PORT = 9001;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            new Server(PORT).run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
