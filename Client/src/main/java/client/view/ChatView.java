package client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import chatroomlibrary.Message;
import client.controller.ChatController;

public class ChatView implements Observer {

    private final JTextPane jtextFilDiscu;
    private final JTextPane jtextListUsers;

    private final JTextField jtextInputChat;

    private final JButton jsbtn;
    private final JButton jsbtndeco;
    private final JButton jcbtn;
    private final JButton jubtn;

    private final JFrame jfr;

    private final JTextField jtfName;
    private final JTextField jtfport;
    private final JTextField jtfAddr;

    private final JScrollPane jtextInputChatSP;

    private ChatController controller;

    public ChatView(ChatController controller) {
        this.controller = controller;
        controller.addObserver(this);

        jtextFilDiscu = new JTextPane();
        jtextListUsers = new JTextPane();

        jtextInputChat = new JTextField();

        jsbtn = new JButton("Send");
        jsbtndeco = new JButton("Disconnect");
        jcbtn = new JButton("Connect");
        jubtn = new JButton("Upload");

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

        jubtn.setFont(font);
        jubtn.setBounds(450, 410, 100, 35);

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

        jtextInputChat.addKeyListener(new KeyAdapter() {
            // send message on Enter
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    controller.sendMessage(jtextInputChat.getText().trim());
                    jtextInputChat.requestFocus();
                    jtextInputChat.setText(null);
                }
            }
        });

        // Click on send button
        jsbtn.addActionListener(ae -> {
            controller.sendMessage(jtextInputChat.getText().trim());
            jtextInputChat.requestFocus();
            jtextInputChat.setText(null);
        });

        // Click on upload button
        jubtn.addActionListener(ae -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(jfr);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());
                controller.sendFile(selectedFile);
            }
        });

        // On connect
        jcbtn.addActionListener(ae -> {
            String username = jtfName.getText();
            String host = jtfAddr.getText();
            int port = Integer.parseInt(jtfport.getText());

            controller.connect(host, port, username);

            appendText("<span>Connecting to " + host + " on port " + host + "...</span>");

            // appendText("<span>Could not connect to Server</span>");
            // showMessage(ex.getMessage());
        });

        // On disconnect
        jsbtndeco.addActionListener(ae -> {
            disconnect();
            controller.disconnect();
        });

        appendToPane(jtextFilDiscu, "<h4>Welcome:</h4>");
    }

    private void appendToPane(JTextPane tp, String msg) {
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

    public void showMessage(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    public void disconnect() {
        jfr.add(jtfName);
        jfr.add(jtfport);
        jfr.add(jtfAddr);
        jfr.add(jcbtn);
        jfr.remove(jsbtn);
        jfr.remove(jubtn);
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
        appendText("<span>Connected to " + controller.getRemoteSocketAddress() + "</span>");

        jfr.remove(jtfName);
        jfr.remove(jtfport);
        jfr.remove(jtfAddr);
        jfr.remove(jcbtn);
        jfr.add(jsbtn);
        jfr.add(jubtn);
        jfr.add(jtextInputChatSP);
        jfr.add(jsbtndeco);
        jfr.revalidate();
        jfr.repaint();
        jtextFilDiscu.setBackground(Color.WHITE);
        jtextListUsers.setBackground(Color.WHITE);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Message) {
            Message message = (Message) arg;

            if (message != null)
                appendMessage(message);

            return;
        } else if (arg instanceof ArrayList) {
            updateUsersList(controller.getUsersList());
        } else if (arg instanceof String) {
            String str = (String) arg;
            switch (str) {
            case "connected":
                connect();
                break;
            case "filerequest":
                fileRequest();
                break;
            default:
                break;
            }
        }
    }

    private void fileRequest() {
        int dialogResult = JOptionPane.showConfirmDialog(jfr, "Would You Like to accept file?", "Warning",
                JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setAcceptAllFileFilterUsed(false);

            int returnValue = jfc.showSaveDialog(jfr);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());

                controller.acceptFile(selectedFile.getAbsolutePath());
            }
        } else {

        }
    }

    public void updateUsersList(ArrayList<String> users) {
        jtextListUsers.setText(null);
        for (String user : users) {
            appendToPane(jtextListUsers, "@" + user);
        }
    }
}