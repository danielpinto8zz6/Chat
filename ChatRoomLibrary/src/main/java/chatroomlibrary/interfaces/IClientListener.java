package chatroomlibrary.interfaces;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Message;
import chatroomlibrary.User;

public interface IClientListener {
    public void onMessageReceived(Message message);

    public void onUserListReceived(List<User> users);

    public void onFilesListReceived(DefaultMutableTreeNode files);

    public void onLoginFailed();

    public void onLogged();

    public void onFileRequested(Message message);

    public void onTransferAccepted(Message message);

    public void onStartTransfer(Message message);
}