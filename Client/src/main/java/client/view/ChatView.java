package client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import chatroomlibrary.Message;

public class ChatView extends Thread {

    private final JTextPane jtextFilDiscu;
    private final JTextPane jtextListUsers;

    private final JTextField jtextInputChat;

    private final JButton jsbtn;
    private final JButton jsbtndeco;
    private final JButton jcbtn;

    private final JFrame jfr;

    private final JTextField jtfName;
    private final JTextField jtfport;
    private final JTextField jtfAddr;

    private final JScrollPane jtextInputChatSP;

    public ChatView() {
        jtextFilDiscu = new JTextPane();
        jtextListUsers = new JTextPane();

        jtextInputChat = new JTextField();

        jsbtn = new JButton("Send");
        jsbtndeco = new JButton("Disconnect");
        jcbtn = new JButton("Connect");

        jfr = new JFrame("Chat");

        jtfName = new JTextField("username");
        jtfport = new JTextField("9001");
        jtfAddr = new JTextField("localhost");

        jtextInputChatSP = new JScrollPane(jtextInputChat);

        String fontfamily = "Arial, sans-serif";
        Font font = new Font(fontfamily, Font.PLAIN, 15);

        jfr.getContentPane().setLayout(null);
        jfr.setSize(700, 500);
        jfr.setResizable(false);
        jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jtextFilDiscu.setBounds(25, 25, 490, 320);
        jtextFilDiscu.setFont(font);
        jtextFilDiscu.setMargin(new Insets(6, 6, 6, 6));
        jtextFilDiscu.setEditable(false);
        JScrollPane jtextFilDiscuSP = new JScrollPane(jtextFilDiscu);
        jtextFilDiscuSP.setBounds(25, 25, 490, 320);

        jtextFilDiscu.setContentType("text/html");
        jtextFilDiscu.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        jtextListUsers.setBounds(520, 25, 156, 320);
        jtextListUsers.setEditable(true);
        jtextListUsers.setFont(font);
        jtextListUsers.setMargin(new Insets(6, 6, 6, 6));
        jtextListUsers.setEditable(false);
        JScrollPane jsplistuser = new JScrollPane(jtextListUsers);
        jsplistuser.setBounds(520, 25, 156, 320);

        jtextListUsers.setContentType("text/html");
        jtextListUsers.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        jtextInputChat.setBounds(0, 350, 400, 50);
        jtextInputChat.setFont(font);
        jtextInputChat.setMargin(new Insets(6, 6, 6, 6));
        jtextInputChatSP.setBounds(25, 350, 650, 50);

        jsbtn.setFont(font);
        jsbtn.setBounds(575, 410, 100, 35);

        jsbtndeco.setFont(font);
        jsbtndeco.setBounds(25, 410, 130, 35);

        jtfName.getDocument().addDocumentListener(new TextListener(jtfName, jtfport, jtfAddr, jcbtn));
        jtfport.getDocument().addDocumentListener(new TextListener(jtfName, jtfport, jtfAddr, jcbtn));
        jtfAddr.getDocument().addDocumentListener(new TextListener(jtfName, jtfport, jtfAddr, jcbtn));

        jcbtn.setFont(font);
        jtfAddr.setBounds(25, 380, 135, 40);
        jtfName.setBounds(375, 380, 135, 40);
        jtfport.setBounds(200, 380, 135, 40);
        jcbtn.setBounds(575, 380, 100, 40);

        jtextFilDiscu.setBackground(Color.LIGHT_GRAY);
        jtextListUsers.setBackground(Color.LIGHT_GRAY);

        jfr.add(jcbtn);
        jfr.add(jtextFilDiscuSP);
        jfr.add(jsplistuser);
        jfr.add(jtfName);
        jfr.add(jtfport);
        jfr.add(jtfAddr);
        jfr.setVisible(true);

        appendToPane(jtextFilDiscu, "<h4>Welcome:</h4>");
    }

    public void appendToPane(JTextPane tp, String msg) {
        HTMLDocument doc = (HTMLDocument) tp.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit) tp.getEditorKit();
        try {
            editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
            tp.setCaretPosition(doc.getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appendMessage(Message message) {
        appendToPane(jtextFilDiscu, "@" + message.getUsername() + " : " + message.getText());
    }

    public void appendText(String text) {
        appendToPane(jtextFilDiscu, text);
    }

    public void updateUsersList(ArrayList<String> users) {
        jtextListUsers.setText(null);
        for (String user : users) {
            appendToPane(jtextListUsers, "@" + user);
        }
    }

    public String getChatInput() {
        return jtextInputChat.getText().trim();
    }

    public void clearChatInput() {
        jtextInputChat.requestFocus();
        jtextInputChat.setText(null);
    }

    public void showMessage(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    public JTextPane getJtextFilDiscu() {
        return this.jtextFilDiscu;
    }

    public JTextPane getJtextListUsers() {
        return this.jtextListUsers;
    }

    public JTextField getJtextInputChat() {
        return this.jtextInputChat;
    }

    public JButton getJsbtn() {
        return this.jsbtn;
    }

    public JButton getJsbtndeco() {
        return this.jsbtndeco;
    }

    public JButton getJcbtn() {
        return this.jcbtn;
    }

    public JTextField getJtfName() {
        return this.jtfName;
    }

    public JTextField getJtfport() {
        return this.jtfport;
    }

    public JTextField getJtfAddr() {
        return this.jtfAddr;
    }

    public void disconnect() {
        jfr.add(jtfName);
        jfr.add(jtfport);
        jfr.add(jtfAddr);
        jfr.add(jcbtn);
        jfr.remove(jsbtn);
        jfr.remove(jtextInputChatSP);
        jfr.remove(jsbtndeco);
        jfr.revalidate();
        jfr.repaint();
        jtextListUsers.setText(null);
        jtextFilDiscu.setBackground(Color.LIGHT_GRAY);
        jtextListUsers.setBackground(Color.LIGHT_GRAY);
        appendToPane(jtextFilDiscu, "<span>Connection closed.</span>");
    }

    public void connect() {
        jfr.remove(jtfName);
        jfr.remove(jtfport);
        jfr.remove(jtfAddr);
        jfr.remove(jcbtn);
        jfr.add(jsbtn);
        jfr.add(jtextInputChatSP);
        jfr.add(jsbtndeco);
        jfr.revalidate();
        jfr.repaint();
        jtextFilDiscu.setBackground(Color.WHITE);
        jtextListUsers.setBackground(Color.WHITE);
    }
}