package client.view;

import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
        JPanel panel = new JPanel(new GridLayout(2, 2));

        final JTextField username = new JTextField(10);
        final JPasswordField password = new JPasswordField(10);

        panel.add(new JLabel("Username"));
        panel.add(username);
        panel.add(new JLabel("Password"));
        panel.add(password);

        JOptionPane pane = new JOptionPane(panel, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE) {
            private static final long serialVersionUID = 1L;

            @Override
            public void selectInitialValue() {
                username.requestFocusInWindow();
            }
        };

        pane.createDialog(null, "Login").setVisible(true);

        return username.getText().length() > 0 && password.getPassword().length > 0
                ? new Message(Message.Action.LOGIN, username.getText(), new String(password.getPassword()))
                : null;
    }

    public void setEditable() {
        textField.setEditable(true);
    }

    public void showMessage(String string) {
        JOptionPane.showMessageDialog(frame, "Login failed!");
    }

}