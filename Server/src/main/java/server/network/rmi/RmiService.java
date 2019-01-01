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

/**
 * <p>RmiService class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class RmiService extends UnicastRemoteObject implements UserSensor {

    private static final long serialVersionUID = 1L;

    private List<UserListener> listeners;
    private ServerController controller;

    /**
     * <p>Constructor for RmiService.</p>
     *
     * @param controller a {@link server.controller.ServerController} object.
     * @throws java.rmi.RemoteException if any.
     */
    public RmiService(ServerController controller) throws RemoteException {
        this.controller = controller;
        listeners = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized List<User> getUsers() throws RemoteException {
        return controller.getLogggedUsers();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized DefaultMutableTreeNode getSharedFiles() throws RemoteException {
        return controller.getFiles();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void addListener(UserListener listener) throws RemoteException {
        listeners.add(listener);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void removeListener(UserListener listener) throws RemoteException {
        listeners.remove(listener);
    }

    /**
     * <p>notifyUsersList.</p>
     *
     * @param users a {@link java.util.List} object.
     */
    public synchronized void notifyUsersList(List<User> users) {
        for (UserListener listener : listeners) {
            try {
                listener.usersChanged(users);
            } catch (RemoteException e) {
                listeners.remove(listener);
            }
        }
    }

    /**
     * <p>notifySharedFiles.</p>
     *
     * @param files a {@link javax.swing.tree.DefaultMutableTreeNode} object.
     */
    public synchronized void notifySharedFiles(DefaultMutableTreeNode files) {
        for (UserListener listener : listeners) {
            try {
                listener.sharedFilesChanged(files);
            } catch (RemoteException e) {
                listeners.remove(listener);
            }
        }
    }
}
