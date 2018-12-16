package client.network.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.User;
import chatroomlibrary.rmi.UserListener;
import client.controller.ChatController;

public class Rmi extends UnicastRemoteObject implements UserListener {

    private static final long serialVersionUID = 1L;
    private ChatController controller;

    public Rmi(ChatController controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void usersChanged(List<User> users) throws RemoteException {
        System.exit(0);
    }

    @Override
    public void sharedFilesChanged(DefaultMutableTreeNode files) throws RemoteException {
        System.exit(0);
    };

}
