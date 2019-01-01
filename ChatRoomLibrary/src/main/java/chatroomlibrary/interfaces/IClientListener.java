package chatroomlibrary.interfaces;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Message;
import chatroomlibrary.User;

/**
 * <p>IClientListener interface.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public interface IClientListener {
    /**
     * <p>onMessageReceived.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void onMessageReceived(Message message);

    /**
     * <p>onUserListReceived.</p>
     *
     * @param users a {@link java.util.List} object.
     */
    public void onUserListReceived(List<User> users);

    /**
     * <p>onFilesListReceived.</p>
     *
     * @param files a {@link javax.swing.tree.DefaultMutableTreeNode} object.
     */
    public void onFilesListReceived(DefaultMutableTreeNode files);

    /**
     * <p>onLoginFailed.</p>
     */
    public void onLoginFailed();

    /**
     * <p>onLogged.</p>
     */
    public void onLogged();

    /**
     * <p>onFileRequested.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void onFileRequested(Message message);

    /**
     * <p>onTransferAccepted.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void onTransferAccepted(Message message);

    /**
     * <p>onStartTransfer.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void onStartTransfer(Message message);
}
