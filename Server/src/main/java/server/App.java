package server;

import server.controller.ServerController;
import server.database.DbHelper;
import server.model.Server;
import server.network.CommunicationHandler;
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
        int tcpPort = Constants.TCP_PORT;
        int udpPort = Constants.UDP_PORT;

        if (args.length > 1) {
            tcpPort = Integer.parseInt(args[0]);
            udpPort = Integer.parseInt(args[1]);
        }

        DbHelper.open();
        Server server = new Server(tcpPort, udpPort);
        ServerController controller = new ServerController(server);

        CommunicationHandler handler = new CommunicationHandler(controller);
        handler.startTCP();
        // handler.registerRmiService();

        new ServerView(controller);
    }

}
