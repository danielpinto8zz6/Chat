package client.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.FileInfo;
import chatroomlibrary.Message;
import chatroomlibrary.Message.Type;
import chatroomlibrary.SharedFiles;
import chatroomlibrary.User;
import chatroomlibrary.interfaces.IClientListener;
import client.model.Chat;
import client.model.Conversation;
import client.network.CommunicationHandler;
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
    private CommunicationHandler client;

    public boolean logged = false;

    // private Thread monitor;

    /**
     * <p>
     * Constructor for ChatController.
     * </p>
     *
     * @param model a {@link client.model.Chat} object.
     */
    public ChatController(Chat model) {
        this.model = model;
        this.client = new CommunicationHandler(this);
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

        client.sendTCPMessage(message);
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
     * @param file     a {@link java.io.File} object.
     * @param username a {@link java.lang.String} object.
     */
    public void sendFile(File file, String username) {
        FileInfo fileInfo = new FileInfo(file);
        Message message = new Message(Message.Type.SEND_FILE, model.getUser(), fileInfo, username);

        client.sendTCPMessage(message);
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
     * @param path     a {@link java.lang.String} object.
     * @param user     a {@link chatroomlibrary.User} object.
     * @param fileInfo a {@link chatroomlibrary.FileInfo} object.
     */
    public void acceptFile(String path, User user, FileInfo fileInfo) {
        Message message = new Message(Message.Type.FILE_ACCEPTED, model.getUser(), fileInfo);
        message.setTo(user.getUsername());

        client.sendTCPMessage(message);

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

    /**
     * <p>
     * getUsernames.
     * </p>
     *
     * @return an array of {@link java.lang.String} objects.
     */
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

    /**
     * <p>
     * fileSent.
     * </p>
     *
     * @param filename a {@link java.lang.String} object.
     * @param receiver a {@link java.lang.String} object.
     */
    public void fileSent(String filename, String receiver) {
        String username = model.getUser().getUsername();

        setChanged();
        notifyObservers(new String[] { "file-sent", username, receiver, filename });
    }

    /**
     * <p>
     * fileReceived.
     * </p>
     *
     * @param filename a {@link java.lang.String} object.
     * @param sender   a {@link java.lang.String} object.
     */
    public void fileReceived(String filename, String sender) {
        String username = model.getUser().getUsername();

        setChanged();
        notifyObservers(new String[] { "file-received", sender, username, filename });
    }

    /**
     * <p>
     * getFiles.
     * </p>
     *
     * @return a {@link javax.swing.tree.DefaultMutableTreeNode} object.
     */
    public DefaultMutableTreeNode getFiles() {
        return model.getFiles();
    }

    /**
     * <p>
     * setSharedFolder.
     * </p>
     *
     * @param file a {@link java.io.File} object.
     */
    public void setSharedFolder(File file) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                model.setSharedFolder(file);

                SharedFiles sharedFiles = new SharedFiles(file, model.getUser().getUsername());

                if (logged)
                    sendSharedFiles(sharedFiles);

                // monitor = new Thread(new Runnable() {
                //     @Override
                //     public void run() {
                //         try {
                //             MonitorDirectory.watch(ChatController.this, file.toPath());
                //         } catch (IOException | InterruptedException e) {
                //             e.printStackTrace();
                //         }
                //     }
                // });
                // monitor.start();
            }
        });
        thread.start();
    }

    /**
     * <p>updateSharedFolder.</p>
     */
    public void  updateSharedFolder() {
        setSharedFolder(model.getSharedFolder());
    }

    /**
     * <p>
     * setSaveLocation.
     * </p>
     *
     * @param file a {@link java.io.File} object.
     */
    public void setSaveLocation(File file) {
        model.setSaveLocation(file);
    }

    private void sendSharedFiles(DefaultMutableTreeNode files) {
        Message message = new Message(Message.Type.SEND_SHARED_FILES, model.getUser(), files);

        client.sendTCPMessage(message);
    }

    /**
     * <p>
     * fileSend.
     * </p>
     *
     * @param file a {@link java.io.File} object.
     * @param user a {@link chatroomlibrary.User} object.
     */
    public void fileSend(File file, User user) {
        FileInfo fileInfo = new FileInfo(file);

        Thread thread = new Thread(new FileSender(this, user, 9002, fileInfo));
        thread.start();
    }

    /**
     * <p>
     * requestFile.
     * </p>
     *
     * @param fileInfo a {@link chatroomlibrary.FileInfo} object.
     */
    public void requestFile(FileInfo fileInfo) {
        if (fileInfo.getOwner() == null) {
            return;
        }

        Message message = new Message(Message.Type.REQUEST_FILE, model.getUser(), fileInfo, fileInfo.getOwner());

        client.sendTCPMessage(message);
    }

    /**
     * <p>
     * acceptTransfer.
     * </p>
     *
     * @param username a {@link java.lang.String} object.
     * @param fileInfo a {@link chatroomlibrary.FileInfo} object.
     */
    public void acceptTransfer(String username, FileInfo fileInfo) {
        Message message = new Message(Message.Type.TRANSFER_ACCEPTED, model.getUser(), fileInfo, username);

        client.sendTCPMessage(message);
    }

    /**
     * <p>
     * createConnection.
     * </p>
     *
     * @param user a {@link chatroomlibrary.User} object.
     * @param type a {@link chatroomlibrary.Message.Type} object.
     */
    public void createConnection(User user, Type type) {
        model.setUser(user);
        client.createTcpConnection(user, type);
    }

    /**
     * <p>
     * close.
     * </p>
     */
    public void close() {
        client.disconnect();
    }

    /**
     * <p>
     * getAdress.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAdress() {
        return model.getUser().getAddress();
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public synchronized void onUserListReceived(List<User> users) {
        model.setUsers((ArrayList<User>) users);

        setChanged();
        notifyObservers("update-users");
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void onFilesListReceived(DefaultMutableTreeNode files) {
        model.setFiles(files);

        setChanged();
        notifyObservers("update-files");
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void onLoginFailed() {
        setChanged();
        notifyObservers("login-failed");
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void onLogged() {
        logged = true;

        client.startUdp();

        setChanged();
        notifyObservers("connected");
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void onFileRequested(Message message) {
        setChanged();
        notifyObservers(message);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void onTransferAccepted(Message message) {
        FileInfo fileInfo = (FileInfo) message.getData();
        User user = message.getUser();

        Thread thread = new Thread(
                new FileReceiver(this, 9002, fileInfo, model.getSaveLocation().getAbsolutePath(), user));
        thread.start();

        // Transfer accepted, start receiving thread and ask the user to send
        Message m = new Message(Message.Type.START_TRANSFER, model.getUser(), fileInfo, user.getUsername());

        client.sendTCPMessage(m);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void onStartTransfer(Message message) {
        FileInfo fileInfo = (FileInfo) message.getData();
        User user = message.getUser();

        Thread thread = new Thread(new FileSender(this, user, 9002, fileInfo));
        thread.start();
    }

    /**
     * <p>
     * getUdpPort.
     * </p>
     *
     * @return a int.
     */
    public int getUdpPort() {
        return model.getUser().getUdpPort();
    }

    /**
     * <p>
     * isRunning.
     * </p>
     *
     * @return a boolean.
     */
    public boolean isRunning() {
        return model.isRunning();
    }

    /**
     * <p>
     * getSaveLocation.
     * </p>
     *
     * @return a {@link java.io.File} object.
     */
    public File getSaveLocation() {
        return model.getSaveLocation();
    }

    /**
     * <p>
     * getUser.
     * </p>
     *
     * @return a {@link chatroomlibrary.User} object.
     */
    public User getUser() {
        return model.getUser();
    }

    /**
     * <p>
     * getCommunication.
     * </p>
     *
     * @return a {@link client.network.CommunicationHandler} object.
     */
    public CommunicationHandler getCommunication() {
        return client;
    }
}
