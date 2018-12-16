package server.network.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.User;
import chatroomlibrary.rmi.UserListener;
import chatroomlibrary.rmi.UserSensor;
import server.controller.ServerController;

public class RmiService extends UnicastRemoteObject implements UserSensor {

    private static final long serialVersionUID = 1L;

    private List<UserListener> listeners;
    private ServerController controller;

    public RmiService(ServerController controller) throws RemoteException {
        this.controller = controller;
        listeners = new ArrayList<>();
    }

    @Override
    public synchronized List<User> getUsers() throws RemoteException {
        return controller.getUsers();
    }

    @Override
    public synchronized DefaultMutableTreeNode getSharedFiles() throws RemoteException {
        return controller.getFiles();
    }

    @Override
    public synchronized void addListener(UserListener listener) throws RemoteException {
        listeners.add(listener);
    }

    @Override
    public synchronized void removeListener(UserListener listener) throws RemoteException {
        listeners.remove(listener);
    }

    public synchronized void notifyUsersList() {
        for (UserListener listener : listeners) {
            try {
                listener.usersChanged(getUsers());
            } catch (RemoteException e) {
                listeners.remove(listener);
            }
        }
    }

    public synchronized void notifySharedFiles() {
        for (UserListener listener : listeners) {
            try {
                listener.sharedFilesChanged(getSharedFiles());
            } catch (RemoteException e) {
                listeners.remove(listener);
            }
        }
    }
}