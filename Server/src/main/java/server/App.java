package server;

/**
 * @author daniel
 */
class App {

    /**
     * The port that the server listens on.
     */
    private static final int PORT = 9001;

    /**
     * <p>main.</p>
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Server(PORT).run();
    }

}
