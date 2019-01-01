package chatroomlibrary.rmi;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.User;

/**
 * <p>UserSensor interface.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public interface UserSensor extends java.rmi.Remote {

    /**
     * <p>getUsers.</p>
     *
     * @return a {@link java.util.List} object.
     * @throws java.rmi.RemoteException if any.
     */
    public List<User> getUsers() throws java.rmi.RemoteException;

    /**
     * <p>getSharedFiles.</p>
     *
     * @return a {@link javax.swing.tree.DefaultMutableTreeNode} object.
     * @throws java.rmi.RemoteException if any.
     */
    public DefaultMutableTreeNode getSharedFiles() throws java.rmi.RemoteException;

    /**
     * <p>addListener.</p>
     *
     * @param listener a {@link chatroomlibrary.rmi.UserListener} object.
     * @throws java.rmi.RemoteException if any.
     */
    public void addListener(UserListener listener) throws java.rmi.RemoteException;

    /**
     * <p>removeListener.</p>
     *
     * @param listener a {@link chatroomlibrary.rmi.UserListener} object.
     * @throws java.rmi.RemoteException if any.
     */
    public void removeListener(UserListener listener) throws java.rmi.RemoteException;
}
