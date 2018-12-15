package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientReceiver implements Runnable {
    private ServerController controller;

    public ClientReceiver(ServerController controller) {
        this.controller = controller;
    }

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