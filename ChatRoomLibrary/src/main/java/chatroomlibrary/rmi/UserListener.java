package chatroomlibrary.rmi;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.User;

public interface UserListener extends java.rmi.Remote {
    public void usersChanged(List<User> users) throws java.rmi.RemoteException;

    public void sharedFilesChanged(DefaultMutableTreeNode files) throws java.rmi.RemoteException;
}
