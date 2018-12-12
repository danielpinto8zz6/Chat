package server.view;

import java.util.Observable;
import java.util.Observer;

import server.controller.ServerController;

@SuppressWarnings("deprecation")
public class ServerView implements Observer {
    private ServerController controller;

    public ServerView(ServerController controller) {
        this.controller = controller;
        controller.addObserver(this);
    }

    @Override
    public void update(Observable arg0, Object arg1) {

    }
}