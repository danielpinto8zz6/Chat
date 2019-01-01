package chatroomlibrary.rmi;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.User;

/**
 * <p>UserListener interface.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public interface UserListener extends java.rmi.Remote {
    /**
     * <p>usersChanged.</p>
     *
     * @param users a {@link java.util.List} object.
     * @throws java.rmi.RemoteException if any.
     */
    public void usersChanged(List<User> users) throws java.rmi.RemoteException;

    /**
     * <p>sharedFilesChanged.</p>
     *
     * @param files a {@link javax.swing.tree.DefaultMutableTreeNode} object.
     * @throws java.rmi.RemoteException if any.
     */
    public void sharedFilesChanged(DefaultMutableTreeNode files) throws java.rmi.RemoteException;
}
