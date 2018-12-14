package server;

import server.controller.ServerController;
import server.model.Server;
import server.view.ServerView;

/**
 * @author daniel
 */
class App {
    /**
     * <p>
     * main.
     * </p>
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server(Constants.PORT);
        ServerController controller = new ServerController(server);
        ServerView view = new ServerView(controller);

        controller.startServer();
        System.out.println("Port " + server.getPort() + " is now open at " + server.getHostAddress());
    }

}
