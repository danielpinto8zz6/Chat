package chatroomlibrary.rmi;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.User;

public interface UserSensor extends java.rmi.Remote {

    public List<User> getUsers() throws java.rmi.RemoteException;

    public DefaultMutableTreeNode getSharedFiles() throws java.rmi.RemoteException;

    public void addListener(UserListener listener) throws java.rmi.RemoteException;

    public void removeListener(UserListener listener) throws java.rmi.RemoteException;
}
