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
    public void update(Observable o, Object arg) {
        if (arg instanceof String[]) {
            String[] str = (String[]) arg;
            switch (str[0]) {
            case "user-joined":
                System.out.println("User joined : " + str[1] + "\n\t     Host : " + str[2]);
                break;
            case "user-exited":
                System.out.println("User exited : " + str[1] + "\n\t     Host : " + str[2]);
                break;
            case "login-failed":
                System.out.println("[" + str[1] + "]" + " - (" + str[2] + ") tried to login but failed");
                break;
            }
        }

    }
}