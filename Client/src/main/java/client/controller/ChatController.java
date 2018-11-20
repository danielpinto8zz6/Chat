package client.controller;

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

    private Thread threadReceiver;

    /** hack */
    public boolean checked = false;
    public boolean newmsg = false;

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
        newmsg = true;

        setChanged();
        notifyObservers();
    }

    public void updateUsersList(ArrayList<String> users) {
        model.setUsersList(users);

        setChanged();
        notifyObservers();
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
        stopReceiver();
        try {
            out.close();
            in.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        checked = false;
        newmsg = false;
    }

    public void connect(String host, int port, String username) {
        model.setHost(host);
        model.setPort(port);
        model.setUsername(username);

        try {
            server = new Socket(host, port);
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
        model.setConnected(true);

        setChanged();
        notifyObservers();
    }

    public boolean isConnected() {
        return model.isConnected();
    }

    public String getRemoteSocketAddress() {
        return server.getRemoteSocketAddress().toString();
    }
}