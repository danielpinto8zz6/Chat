package client.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Message.Type;
import chatroomlibrary.interfaces.IClientListener;
import chatroomlibrary.FileInfo;
import chatroomlibrary.Message;
import chatroomlibrary.SharedFiles;
import chatroomlibrary.User;
import client.model.Chat;
import client.model.Conversation;
import client.network.Client;
import client.network.tcp.FileReceiver;
import client.network.tcp.FileSender;

/**
 * <p>
 * ChatController class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class ChatController extends Observable implements IClientListener {
    private final Chat model;
    private Client client;

    public boolean logged = false;

    /**
     * <p>
     * Constructor for ChatController.
     * </p>
     *
     * @param model a {@link client.model.Chat} object.
     */
    public ChatController(Chat model) {
        this.model = model;
        this.client = new Client(this);
    }

    private Conversation getConversation(String to) {
        for (Conversation conversation : model.getConversations()) {
            if (conversation.getUser().getUsername() == to) {
                return conversation;
            }
        }

        return null;
    }

    /**
     * <p>
     * sendMessage.
     * </p>
     *
     * @param text a {@link java.lang.String} object.
     */
    public void sendMessage(String text) {
        if (text == "")
            return;

        Message message = new Message(Message.Type.MESSAGE, model.getUser(), text);

        if (text.charAt(0) == '@') {
            if (text.contains(" ")) {
                int firstSpace = text.indexOf(" ");
                String to = text.substring(1, firstSpace);
                message.setData(text.substring(firstSpace + 1, text.length()));
                message.setTo(to);
            }
        }

        client.sendTcpMessage(message);
    }

    /**
     * <p>
     * getUsersList.
     * </p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<User> getUsersList() {
        return model.getUsers();
    }

    /**
     * <p>
     * getLastMessage.
     * </p>
     *
     * @return a {@link chatroomlibrary.Message} object.
     */
    public Message getLastMessage() {
        return model.getLastMessage();
    }

    /**
     * <p>
     * sendFile.
     * </p>
     *
     * @param file a {@link java.io.File} object.
     */
    public void sendFile(File file, String username) {
        FileInfo fileInfo = new FileInfo(file);
        Message message = new Message(Message.Type.SEND_FILE, model.getUser(), fileInfo, username);

        client.sendTcpMessage(message);
    }

    /**
     * <p>
     * fileRequest.
     * </p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void fileRequest(Message message) {
        // Do not ask to the sender
        if (message.getUser().equals(model.getUser()))
            return;

        setChanged();
        notifyObservers(message);
    }

    /**
     * <p>
     * acceptFile.
     * 
     * </p>
     *
     * @param absolutePath a {@link java.lang.String} object.
     */
    public void acceptFile(String path, User user, FileInfo fileInfo) {
        Message message = new Message(Message.Type.FILE_ACCEPTED, model.getUser(), fileInfo);
        message.setTo(user.getUsername());

        client.sendTcpMessage(message);

        // File accepted, create FileReceiver thread and wait for user to connect and
        // send the file
        Thread thread = new Thread(new FileReceiver(this, 9002, fileInfo, path, user));
        thread.start();
    }

    /**
     * <p>
     * fileAccepted.
     * </p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void fileAccepted(Message message) {
        /**
         * Create connection with client and send the file.
         */
        FileInfo fileInfo = (FileInfo) message.getData();

        Thread thread = new Thread(new FileSender(this, message.getUser(), 9002, fileInfo));
        thread.start();
    }

    public String[] getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for (User user : model.getUsers()) {
            if (!user.equals(model.getUser()))
                usernames.add(user.getUsername());
        }
        String[] usernamesArr = new String[usernames.size()];
        usernamesArr = usernames.toArray(usernamesArr);
        return usernamesArr;
    }

    public void fileSent(String filename, String receiver) {
        setChanged();
        notifyObservers(new String[] { "file-sent", model.getUser().getUsername(), receiver, filename });
    }

    public void fileReceived(String filename, String sender) {
        String username = model.getUser().getUsername();

        setChanged();
        notifyObservers(new String[] { "file-received", sender, username, filename });
    }

    public DefaultMutableTreeNode getFiles() {
        return model.getFiles();
    }

    public void setSharedFolder(File file) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                SharedFiles sharedFiles = new SharedFiles(file, model.getUser().getUsername());

                if (logged)
                    sendSharedFiles(sharedFiles);
            }
        });
        thread.start();
    }

    public void setSaveLocation(File file) {
        model.setSaveLocation(file);
    }

    private void sendSharedFiles(DefaultMutableTreeNode sharedFiles) {
        Message message = new Message(Message.Type.SEND_SHARED_FILES, model.getUser(), sharedFiles);

        client.sendTcpMessage(message);
    }

    public void fileSend(File file, User user) {
        FileInfo fileInfo = new FileInfo(file);

        Thread thread = new Thread(new FileSender(this, user, 9002, fileInfo));
        thread.start();
    }

    public void requestFile(FileInfo fileInfo) {
        if (fileInfo.getOwner() == null) {
            return;
        }

        Message message = new Message(Message.Type.REQUEST_FILE, model.getUser(), fileInfo, fileInfo.getOwner());

        client.sendTcpMessage(message);
    }

    public void acceptTransfer(String username, FileInfo fileInfo) {
        Message message = new Message(Message.Type.TRANSFER_ACCEPTED, model.getUser(), fileInfo, username);

        client.sendTcpMessage(message);
    }

    public void createConnection(User user, Type type) {
        model.setUser(user);
        client.createTcpConnection(user, type);
    }

    public void close() {
        client.disconnect();
    }

    public String getAdress() {
        return model.getUser().getAddress();
    }

    @Override
    public synchronized void onMessageReceived(Message message) {
        if (message.getTo() == null) {
            model.addMessage(message);
        } else {
            Conversation conversation = getConversation(message.getTo());
            if (conversation == null) {
                conversation = new Conversation(message.getUser());
                model.addConversation(conversation);
            }
            conversation.addMessage(message);
        }

        setChanged();
        notifyObservers(message);
    }

    @Override
    public synchronized void onUserListReceived(List<User> users) {
        model.setUsers((ArrayList<User>) users);

        setChanged();
        notifyObservers("update-users");
    }

    @Override
    public synchronized void onFilesListReceived(DefaultMutableTreeNode files) {
        model.setFiles(files);

        setChanged();
        notifyObservers("update-files");
    }

    @Override
    public synchronized void onLoginFailed() {
        setChanged();
        notifyObservers("login-failed");
    }

    @Override
    public synchronized void onLogged() {
        logged = true;

        client.createUdpConnection();

        setChanged();
        notifyObservers("connected");
    }

    @Override
    public synchronized void onFileRequested(Message message) {
        setChanged();
        notifyObservers(message);
    }

    @Override
    public synchronized void onTransferAccepted(Message message) {
        FileInfo fileInfo = (FileInfo) message.getData();
        User user = message.getUser();

        Thread thread = new Thread(
                new FileReceiver(this, 9002, fileInfo, model.getSaveLocation().getAbsolutePath(), user));
        thread.start();

        // Transfer accepted, start receiving thread and ask the user to send
        Message m = new Message(Message.Type.START_TRANSFER, model.getUser(), fileInfo, user.getUsername());

        client.sendTcpMessage(m);
    }

    @Override
    public synchronized void onStartTransfer(Message message) {
        FileInfo fileInfo = (FileInfo) message.getData();
        User user = message.getUser();

        Thread thread = new Thread(new FileSender(this, user, 9002, fileInfo));
        thread.start();
    }

    public int getUdpPort() {
        return model.getUser().getUdpPort();
    }
}
