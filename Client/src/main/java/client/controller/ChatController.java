package client.controller;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;

import chatroomlibrary.Command;
import chatroomlibrary.Message;
import chatroomlibrary.User;
import client.model.Chat;
import client.model.Conversation;

/**
 * <p>ChatController class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class ChatController extends Observable {
    private final Chat model;

    private Socket server = null;
    private ObjectOutputStream out = null;
    ObjectInputStream in = null;

    private Thread threadReceiver = null;

    /**
     * <p>Constructor for ChatController.</p>
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
     * <p>addMessage.</p>
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
     * <p>updateUsers.</p>
     *
     * @param users a {@link java.util.ArrayList} object.
     */
    public void updateUsers(ArrayList<User> users) {
        model.setUsers(users);

        setChanged();
        notifyObservers(users);
    }

    /**
     * <p>sendMessage.</p>
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
                message.setText(text.substring(firstSpace + 1, text.length()));
                message.setTo(to);
            }
        }

        Command command = new Command(Command.Action.MESSAGE, message);

        try {
            out.writeObject(command);
            out.flush();
        } catch (Exception ex) {
            System.exit(0);
        }
    }

    /**
     * <p>getUsersList.</p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<User> getUsersList() {
        return model.getUsers();
    }

    /**
     * <p>getLastMessage.</p>
     *
     * @return a {@link chatroomlibrary.Message} object.
     */
    public Message getLastMessage() {
        return model.getLastMessage();
    }

    /**
     * <p>disconnect.</p>
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
     * <p>connect.</p>
     *
     * @param username a {@link java.lang.String} object.
     * @param host a {@link java.lang.String} object.
     * @param port a int.
     */
    public void connect(String username, String host, int port) {
        model.getUser().setUsername(username);
        model.getUser().setHost(host);
        model.getUser().setPort(port);

        try {
            server = new Socket(host, port);
            in = new ObjectInputStream(server.getInputStream());
            out = new ObjectOutputStream(server.getOutputStream());

            // send nickname to server
            out.writeObject(new Command(Command.Action.LOGIN, new Message(model.getUser())));
            out.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startReceiver();

        setChanged();
        notifyObservers("connected");
    }

    /**
     * <p>getRemoteSocketAddress.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getRemoteSocketAddress() {
        return server.getRemoteSocketAddress().toString();
    }

    /**
     * <p>sendFile.</p>
     *
     * @param file a {@link java.io.File} object.
     */
    public void sendFile(File file) {
        Command command = new Command(Command.Action.REQUEST_FILE, new Message(model.getUser(), file.getName()));

        try {
            out.writeObject(command);
            out.flush();
        } catch (Exception ex) {
            System.exit(0);
        }
    }

    /**
     * <p>fileRequest.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void fileRequest(Message message) {
        // Do not ask to the sender
        if (message.getUser().equals(model.getUser()))
            return;

        System.out.println(message.getUser().getUsername() + model.getUser().getUsername());

        setChanged();
        notifyObservers("filerequest");
    }

    /**
     * <p>acceptFile.</p>
     *
     * @param absolutePath a {@link java.lang.String} object.
     */
    public void acceptFile(String absolutePath) {
        Command command = new Command(Command.Action.FILE_ACCEPTED, new Message(model.getUser()));

        model.setSaveLocation(absolutePath);

        try {
            out.writeObject(command);
            out.flush();
        } catch (Exception ex) {
            System.exit(0);
        }
    }

    /**
     * <p>fileAccepted.</p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void fileAccepted(Message message) {
        /**
         * Create connection with client and send the file.
         */
    }

    /**
     * <p>authenticate.</p>
     *
     * @param username a {@link java.lang.String} object.
     * @param password a {@link java.lang.String} object.
     * @return a boolean.
     */
    public boolean authenticate(String username, String password) {
        return true;
    }
}
