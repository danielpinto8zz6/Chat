package monitor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.User;
import chatroomlibrary.rmi.UserListener;

public class RmiMonitor extends UnicastRemoteObject implements UserListener {

    private static final long serialVersionUID = 1L;

    public RmiMonitor() throws RemoteException {
    }

    @Override
    public void usersChanged(List<User> users) throws RemoteException {
        System.out.println("Users changed");
    }

    @Override
    public void sharedFilesChanged(DefaultMutableTreeNode files) throws RemoteException {
        System.out.println("Shared files changed");
    }

}
