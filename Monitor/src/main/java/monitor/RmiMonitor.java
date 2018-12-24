package monitor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import chatroomlibrary.User;
import chatroomlibrary.rmi.UserListener;

public class RmiMonitor extends UnicastRemoteObject implements UserListener {

    private static final long serialVersionUID = 1L;

    public RmiMonitor() throws RemoteException {
    }

    @Override
    public void usersChanged(List<User> users) throws RemoteException {
        System.out.println("Users : ");
        for (User user : users) {
            System.out.println("[" + user.getAddress() + "]" + " " + user.getUsername());
        }
        System.out.println();
    }

    @Override
    public void sharedFilesChanged(DefaultMutableTreeNode files) throws RemoteException {
        System.out.println("Files : " + files.toString());
        Enumeration<DefaultMutableTreeNode> en = files.preorderEnumeration();
        while (en.hasMoreElements()) {
            DefaultMutableTreeNode node = en.nextElement();
            TreeNode[] path = node.getPath();
            System.out.println((node.isLeaf() ? "  - " : "+ ") + path[path.length - 1]);
        }
    }
}
