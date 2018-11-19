package client.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import chatroomlibrary.Message;
import client.model.Chat;
import client.view.ChatView;

public class ChatController {
    private final Chat model;
    private final ChatView view;

    private Socket server = null;
    private ObjectOutputStream out = null;
    ObjectInputStream in = null;

    private Thread threadReceiver;

    public ChatController(Chat model, ChatView view) {
        this.model = model;
        this.view = view;

        setupListeners();
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
        view.appendMessage(message);
    }

    public void updateUsersList(ArrayList<String> users) {
        view.updateUsersList(users);
    }

    private void sendMessage() {
        try {
            Message message = new Message(model.getUsername(), view.getChatInput());

            if (message.getText().equals("")) {
                return;
            }

            model.appendHistory(message.getText());

            out.writeObject(message);
            out.flush();

            view.clearChatInput();
        } catch (Exception ex) {
            view.showMessage(ex.toString());
            System.exit(0);
        }
    }

    private void setupListeners() {
        view.getJtextInputChat().addKeyListener(new KeyAdapter() {
            // send message on Enter
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }

                // Get last message typed
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    view.getJtextInputChat().setText(model.getLastMessage());
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    view.getJtextInputChat().setText(model.getLastMessage());
                }
            }
        });

        // Click on send button
        view.getJsbtn().addActionListener(ae -> sendMessage());

        // On connect
        view.getJcbtn().addActionListener(ae -> {
            try {
                model.setUsername(view.getJtfName().getText());
                model.setHost(view.getJtfAddr().getText());
                model.setPort(Integer.parseInt(view.getJtfport().getText()));

                view.appendText(
                        "<span>Connecting to " + model.getHost() + " on port " + model.getHost() + "...</span>");
                server = new Socket(model.getHost(), model.getPort());

                view.appendText("<span>Connected to " + server.getRemoteSocketAddress() + "</span>");

                in = new ObjectInputStream(server.getInputStream());
                out = new ObjectOutputStream(server.getOutputStream());

                // send nickname to server
                out.writeObject(new Message(Message.Action.LOGIN, model.getUsername(), "password"));
                out.flush();

                startReceiver();
                view.connect();
            } catch (Exception ex) {
                view.appendText("<span>Could not connect to Server</span>");
                view.showMessage(ex.getMessage());
            }
        });

        // On disconnect
        view.getJsbtndeco().addActionListener(ae -> {
            view.disconnect();
            stopReceiver();
            try {
                out.close();
                in.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}