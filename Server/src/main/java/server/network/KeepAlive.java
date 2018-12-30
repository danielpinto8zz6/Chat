package server.network;

import server.controller.ServerController;

public class KeepAlive implements Runnable {
    private ServerController controller;

    public KeepAlive(ServerController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {

    }

}