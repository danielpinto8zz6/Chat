package client.controller;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;

import chatroomlibrary.Message;
import client.model.Chat;

public class ChatController extends Observable {
    private final Chat model;

    private Socket server = null;
    private ObjectOutputStream out = null;
    ObjectInputStream in = null;

    private Thread threadReceiver = null;

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

    public void appendMessage(Message message) {
        model.appendMessage(message);

        setChanged();
        notifyObservers(message);
    }

    public void updateUsersList(ArrayList<String> users) {
        model.setUsersList(users);

        setChanged();
        notifyObservers(users);
    }

    public void sendMessage(String text) {
        try {
            Message message = new Message(model.getUsername(), text);

            if (message.getText().equals("")) {
                return;
            }

            out.writeObject(message);
            out.flush();
        } catch (Exception ex) {
            System.exit(0);
        }
    }

    public ArrayList<String> getUsersList() {
        return model.getUsersList();
    }

    public Message getLastMessage() {
        return model.getLastMessage();
    }

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

    public void connect(String host, String username) {
        model.setHost(host);
        model.setUsername(username);

        try {
            server = new Socket(host, model.getPort());
            in = new ObjectInputStream(server.getInputStream());
            out = new ObjectOutputStream(server.getOutputStream());

            // send nickname to server
            out.writeObject(new Message(Message.Action.LOGIN, username, "password"));
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

    public String getRemoteSocketAddress() {
        return server.getRemoteSocketAddress().toString();
    }

    public void sendFile(File file) {
        Message message = new Message(Message.Action.REQUEST_FILE);
        message.setText(file.getName());
        message.setUsername(model.getUsername());

        try {
            out.writeObject(message);
            out.flush();
        } catch (Exception ex) {
            System.exit(0);
        }
    }

    public void fileRequest(Message message) {
        // Do not ask to the sender
        if (message.getUsername().equals(model.getUsername()))
            return;

        System.out.println(message.getUsername() + model.getUsername());

        setChanged();
        notifyObservers("filerequest");
    }

    public void acceptFile(String absolutePath) {
        Message message = new Message(Message.Action.FILE_ACCEPTED);
        message.setUsername(model.getUsername());

        // send your ip to make connection
        message.setHost(model.getHost());

        model.setSaveLocation(absolutePath);

        try {
            out.writeObject(message);
            out.flush();
        } catch (Exception ex) {
            System.exit(0);
        }
    }

    public void fileAccepted(String host) {
        /**
         * Create connection with client and send the file.
         */
    }

    public boolean authenticate(String username, String password) {
        return true;
    }
}