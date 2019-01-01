package server.network.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.controller.ServerController;

/**
 * <p>TCPListener class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class TCPListener implements Runnable {
    private ServerController controller;

    /**
     * <p>Constructor for TCPListener.</p>
     *
     * @param controller a {@link server.controller.ServerController} object.
     */
    public TCPListener(ServerController controller) {
        this.controller = controller;
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(controller.getTcpPort());

            while (controller.isRunning()) {
                // accepts a new client
                Socket socket;
                try {
                    socket = server.accept();

                    new Thread(new Authentication(controller, socket)).start();
                } catch (IOException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null)
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
