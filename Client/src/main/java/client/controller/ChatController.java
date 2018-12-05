package client.controller;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.Command;
import chatroomlibrary.FileInfo;
import chatroomlibrary.Message;
import chatroomlibrary.SharedFiles;
import chatroomlibrary.User;
import client.model.Chat;
import client.model.Conversation;

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

    private Socket server = null;
    private ObjectOutputStream out = null;
    ObjectInputStream in = null;

    private Thread threadReceiver = null;

    boolean logged = false;

    /**
     * <p>
     * Constructor for ChatController.
     * </p>
     *
     * @param model a {@link client.model.Chat} object.
     */
    public ChatController(Chat model) {
        this.model = model;
    }

    private void startReceiver() {
        if (in == null)
            return;

        threadReceiver = new Thread(new Receiver(this));
        threadReceiver.start();
    }

    private void stopReceiver() {
        if (threadReceiver != null)
            threadReceiver.interrupt();
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
                System.out.println("private msg : " + text);
                int firstSpace = text.indexOf(" ");
                String to = text.substring(1, firstSpace);
                message.setData(text.substring(firstSpace + 1, text.length()));
                message.setTo(to);
            }
        }

        Command command = new Command(Command.Action.MESSAGE, message);

        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * disconnect.
     * </p>
     */
    public void disconnect() {
        if (threadReceiver != null) {
            stopReceiver();
            try {
                out.close();
                in.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * connect.
     * </p>
     *
     * @param username a {@link java.lang.String} object.
     * @param host     a {@link java.lang.String} object.
     * @param port     a int.
     */
    public void connect(String username, String password, String host, int port, Command.Action action) {
        model.getUser().setUsername(username);
        model.getUser().setPassword(password);
        model.getUser().setPort(port);

        try {
            server = new Socket(host, port);
            in = new ObjectInputStream(server.getInputStream());
            out = new ObjectOutputStream(server.getOutputStream());

            out.writeObject(new Command(action, new Message(model.getUser())));
            out.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startReceiver();
    }

    /**
     * <p>
     * getRemoteSocketAddress.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getRemoteSocketAddress() {
        return server.getRemoteSocketAddress().toString();
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
        System.out.println(fileInfo.getName() + "\n" + fileInfo.getPath() + "\n" + fileInfo.getSize());
        Message message = new Message(model.getUser(), fileInfo, username);
        Command command = new Command(Command.Action.REQUEST_FILE, message);

        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        System.out.println(message.getUser().getUsername() + model.getUser().getUsername());

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

        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                SharedFiles sharedFiles = new SharedFiles(model.getUser().getUsername(), file);
                model.getUser().setFiles(sharedFiles);
                if (logged)
                    sendSharedFiles(sharedFiles);
            }
        });
        thread.start();
    }

    private void sendSharedFiles(DefaultMutableTreeNode sharedFiles) {
        Message message = new Message(model.getUser(), sharedFiles);
        Command command = new Command(Command.Action.SEND_SHARED_FILES, message);

        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
