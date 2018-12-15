package server;

import server.controller.ServerController;
import server.database.DbHelper;
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
        DbHelper.open();
        Server server = new Server(Constants.TCP_PORT, Constants.UDP_PORT);
        ServerController controller = new ServerController(server);

        controller.startServer();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerView(controller).setVisible(true);
            }
        });
    }

}
