/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view;

import java.util.Observable;
import java.util.Observer;

import server.controller.ServerController;

/**
 *
 * @author daniel
 */
public class ServerView extends javax.swing.JFrame implements Observer {
    private static final long serialVersionUID = 1L;

    public ServerView(ServerController controller) {
        controller.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String[]) {
            String[] str = (String[]) arg;
            switch (str[0]) {
            case "user-joined":
                System.out.println("User joined " + str[1]);
                System.out.println("Host " + str[2]);
                System.out.println();
                break;
            case "user-exited":
                System.out.println("User exited " + str[1]);
                System.out.println("Host " + str[2]);
                System.out.println();
                break;
            case "login-failed":
                System.out.println(str[1] + " (" + str[2] + ") tried to login but failed");
                System.out.println();
                break;
            }
        }
    }
}
