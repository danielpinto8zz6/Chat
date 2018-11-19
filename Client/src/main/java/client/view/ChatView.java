package client.view;

import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chatroomlibrary.Message;

public class ChatView {
    private JFrame frame = new JFrame("Chatter");
    private JTextField textField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 40);

    public ChatView() {
        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.getContentPane().add(textField, "South");
        frame.pack();
    }

    public JTextField getTextField() {
        return textField;
    }

    public void addMessage(Message message) {
        messageArea.append(message.getUsername() + " : " + message.getText() + "\n"
                + message.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString() + "\n\n");
    }

    public String getServerAddress() {
        return JOptionPane.showInputDialog(frame, "Enter IP Address of the Server:", "Welcome to the Chatter",
                JOptionPane.QUESTION_MESSAGE);
    }

    public Message getLogin() {
        JTextField username = new JTextField();
        JTextField password = new JPasswordField();
        Object[] message = { "Username:", username, "Password:", password };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new Message(Message.Action.LOGIN, username.getText(), password.getText());
        }

        return null;
    }

    public void setEditable() {
        textField.setEditable(true);
    }

    public void showMessage(String string) {
        JOptionPane.showMessageDialog(frame, "Login failed!");
    }

}