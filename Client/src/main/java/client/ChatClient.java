package client;

import chatroomlibrary.Command;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient {

    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    private Thread thread;

    public ChatClient() {

        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.getContentPane().add(textField, "South");
        frame.pack();

        // Add Listeners
        textField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield by sending the contents
             * of the text field to the server. Then clear the text area in preparation for
             * the next message.
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    out.writeObject(new Command(textField.getText()));
                    out.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                textField.setText("");
            }
        });
    }

    /**
     * Prompt for and return the address of the server.
     */
    private String getServerAddress() {
        return JOptionPane.showInputDialog(frame, "Enter IP Address of the Server:", "Welcome to the Chatter",
                JOptionPane.QUESTION_MESSAGE);
    }

    private Command getLogin() {
        JTextField username = new JTextField();
        JTextField password = new JPasswordField();
        Object[] message = { "Username:", username, "Password:", password };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new Command(Command.Action.LOGIN, username.getText(), password.getText());
        }

        return null;

    }

    public void start() {
        thread = new Thread(new Receiver());
        thread.start();
    }

    public class Receiver implements Runnable {
        @Override
        public void run() {
            String serverAddress = getServerAddress();
            Socket socket = null;
            try {
                socket = new Socket(serverAddress, 9001);
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());

                while (true) {
                    Command message = (Command) in.readObject();

                    switch (message.getAction()) {
                    case REQUEST_LOGIN:
                        out.writeObject(getLogin());
                        out.flush();
                        break;
                    case LOGGED:
                        textField.setEditable(true);
                        break;
                    case MESSAGE:
                        messageArea.append(message.getText().substring(8) + "\n");
                        break;
                    case LOGIN_FAILED:
                        JOptionPane.showMessageDialog(frame, "Login failed!");
                        break;
                    default:
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
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
}