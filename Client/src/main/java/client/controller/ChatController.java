package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import chatroomlibrary.Message;
import client.model.Chat;
import client.view.ChatView;

public class ChatController {
    public Chat model;
    public ChatView view;

    ObjectOutputStream out = null;
    ObjectInputStream in = null;

    public ChatController(Chat model, ChatView view) {
        this.model = model;
        this.view = view;

        view.getTextField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(new Message(model.getUsername(), view.getTextField().getText()));
                view.getTextField().setText("");
            }
        });
    }

    public void startReceiver() {
        Thread thread = new Thread(new Receiver());
        thread.start();
    }

    public class Receiver implements Runnable {
        @Override
        public void run() {
            String serverAddress = view.getServerAddress();
            Socket socket = null;
            try {
                socket = new Socket(serverAddress, 9001);
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());

                while (true) {
                    Message message = (Message) in.readObject();

                    switch (message.getAction()) {
                    case REQUEST_LOGIN:
                        out.writeObject(view.getLogin());
                        out.flush();
                        break;
                    case LOGGED:
                        view.setEditable();
                        model.setUsername(getUsername());
                        break;
                    case MESSAGE:
                        view.addMessage(message);
                        model.addMessage(message);
                        break;
                    case LOGIN_FAILED:
                        view.showMessage("Login failed");
                        break;
                    default:
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void sendMessage(Message message) {
        if (out == null)
            return;

        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return model.getUsername();
    }
}