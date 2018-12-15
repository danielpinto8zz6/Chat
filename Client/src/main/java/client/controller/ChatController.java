package client.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Command;
import chatroomlibrary.Command.Action;
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
@SuppressWarnings("deprecation")
public class ChatController extends Observable {
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

    /**
     * <p>
     * addMessage.
     * </p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void addMessage(Message message) {
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
     * updateUsers.
     * </p>
     *
     * @param users a {@link java.util.ArrayList} object.
     */
    public void updateUsers(ArrayList<User> users) {
        model.setUsers(users);

        setChanged();
        notifyObservers("update-users");
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

        Message message = new Message(model.getUser(), text);

        if (text.charAt(0) == '@') {
            if (text.contains(" ")) {
                int firstSpace = text.indexOf(" ");
                String to = text.substring(1, firstSpace);
                message.setData(text.substring(firstSpace + 1, text.length()));
                message.setTo(to);
            }
        }

        Command command = new Command(Command.Action.MESSAGE, message);

        client.sendTcpCommand(command);
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
        Message message = new Message(model.getUser(), fileInfo, username);
        Command command = new Command(Command.Action.SEND_FILE, message);

        client.sendTcpCommand(command);
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
        Command command = new Command(Command.Action.FILE_ACCEPTED, new Message(model.getUser(), fileInfo));
        command.getMessage().setTo(user.getUsername());

        client.sendTcpCommand(command);

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
        setChanged();
        notifyObservers(new String[] { "file-received", sender, model.getUser().getUsername(), filename });
    }

    public void logged() {
        logged = true;

        setChanged();
        notifyObservers("connected");
    }

    public void login_failed() {
        setChanged();
        notifyObservers("login-failed");
    }

    public void updateFiles(DefaultMutableTreeNode files) {
        model.setFiles(files);

        setChanged();
        notifyObservers("update-files");
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
        Message message = new Message(model.getUser(), sharedFiles);
        Command command = new Command(Command.Action.SEND_SHARED_FILES, message);

        client.sendTcpCommand(command);
    }

    public void fileRequested(Command command) {
        setChanged();
        notifyObservers(command);
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

        Message message = new Message(model.getUser(), fileInfo, fileInfo.getOwner());
        Command command = new Command(Command.Action.REQUEST_FILE, message);

        client.sendTcpCommand(command);
    }

    public void acceptTransfer(String username, FileInfo fileInfo) {
        Message message = new Message(model.getUser(), fileInfo, username);
        Command command = new Command(Command.Action.TRANSFER_ACCEPTED, message);

        client.sendTcpCommand(command);
    }

    public void transferAccepted(Message message) {
        FileInfo fileInfo = (FileInfo) message.getData();
        User user = message.getUser();

        Thread thread = new Thread(
                new FileReceiver(this, 9002, fileInfo, model.getSaveLocation().getAbsolutePath(), user));
        thread.start();

        // Transfer accepted, start receiving thread and ask the user to send
        Message m = new Message(model.getUser(), fileInfo, user.getUsername());
        Command command = new Command(Command.Action.START_TRANSFER, m);

        client.sendTcpCommand(command);
    }

    public void startTransfer(Message message) {
        FileInfo fileInfo = (FileInfo) message.getData();
        User user = message.getUser();

        Thread thread = new Thread(new FileSender(this, user, 9002, fileInfo));
        thread.start();
    }

    public void createConnection(User user, Action action) {
        model.setUser(user);
        client.createTcpConnection(user, action);
    }

    public void close() {
        client.disconnect();
    }
}
