/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import chatroomlibrary.FileInfo;
import chatroomlibrary.Message;
import chatroomlibrary.User;
import client.controller.ChatController;

/**
 * <p>
 * ChatView class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class ChatView extends javax.swing.JPanel implements Observer {
    private static final long serialVersionUID = 1L;
    private ChatController controller;
    private String title = null;

    /**
     * Creates new form NewJPanel
     *
     * @param controller a {@link client.controller.ChatController} object.
     * @param title      a {@link java.lang.String} object.
     */
    public ChatView(ChatController controller, String title) {
        this.controller = controller;
        this.title = title;
        controller.addObserver(this);

        initComponents();
    }

    /**
     * <p>
     * Constructor for ChatView.
     * </p>
     *
     * @param controller a {@link client.controller.ChatController} object.
     */
    public ChatView(ChatController controller) {
        this.controller = controller;
        controller.addObserver(this);

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jsbtn = new javax.swing.JButton();
        jtextInputChatSP = new javax.swing.JScrollPane();
        jtextInputChat = new javax.swing.JTextField();
        jubtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtextFilDiscu = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListUsers = new javax.swing.JList<>();

        jsbtn.setText("Send");
        jsbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsbtnActionPerformed(evt);
            }
        });

        jtextInputChat.setBorder(null);
        jtextInputChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtextInputChatKeyPressed(evt);
            }
        });
        jtextInputChatSP.setViewportView(jtextInputChat);

        jubtn.setText("Upload");
        jubtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jubtnActionPerformed(evt);
            }
        });

        jtextFilDiscu.setEditable(false);
        jtextFilDiscu.setContentType("text/html"); // NOI18N
        jScrollPane2.setViewportView(jtextFilDiscu);

        jListUsers.setModel(jListUsers.getModel());
        jListUsers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jListUsers);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(jtextInputChatSP, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addComponent(jubtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jsbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                        layout.createSequentialGroup().addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 420,
                                                Short.MAX_VALUE)
                                        .addComponent(jScrollPane1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jtextInputChatSP)
                                        .addComponent(jubtn, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(jsbtn, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents

    private void jListUsersMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jListUsersMouseClicked
        String selectedItem = (String) jListUsers.getSelectedValue();
        jtextInputChat.setText("@" + selectedItem + " ");
        jtextInputChat.grabFocus();
    }// GEN-LAST:event_jListUsersMouseClicked

    private void jubtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jubtnActionPerformed
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();

            String username = getUsernameDialog(controller.getUsernames());

            controller.sendFile(selectedFile, username);
        }
    }// GEN-LAST:event_jubtnActionPerformed

    private String getUsernameDialog(String[] usernames) {
        String input = (String) JOptionPane.showInputDialog(this, "Choose user...", "Send File to:",
                JOptionPane.QUESTION_MESSAGE, null, usernames, null);
        return input;
    }

    private void jsbtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jsbtnActionPerformed
        String message = jtextInputChat.getText().trim();

        if (title != null) {
            message = "@" + title + " " + message;
        }

        controller.sendMessage(message);
        jtextInputChat.requestFocus();
        jtextInputChat.setText(null);
    }// GEN-LAST:event_jsbtnActionPerformed

    private void jtextInputChatKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jtextInputChatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String message = jtextInputChat.getText().trim();

            if (title != null) {
                message = "@" + title + " " + message;
            }

            controller.sendMessage(message);
            jtextInputChat.requestFocus();
            jtextInputChat.setText(null);
        }
    }// GEN-LAST:event_jtextInputChatKeyPressed

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

    /**
     * <p>
     * appendMessage.
     * </p>
     *
     * @param message a {@link chatroomlibrary.Message} object.
     */
    public void appendMessage(Message message) {
        if (message.getTo() != null)
            appendToPane(jtextFilDiscu,
                    "(<b>Private</b>)" + "@" + message.getUser().getUsername() + "<span> : " + message.getData()
                            + "</span>" + " <small><font color='gray'>(" + message.getTime().getHour() + ":"
                            + message.getTime().getMinute() + ")</font></small>");
        else
            appendToPane(jtextFilDiscu, "@" + message.getUser().getUsername() + " : " + message.getData() + " <small><font color='gray'>(" + message.getTime().getHour() + ":"
            + message.getTime().getMinute() + ")</font></small>");
    }

    /**
     * <p>
     * appendText.
     * </p>
     *
     * @param text a {@link java.lang.String} object.
     */
    public void appendText(String text) {
        appendToPane(jtextFilDiscu, text);
    }

    /**
     * <p>
     * showMessage.
     * </p>
     *
     * @param text a {@link java.lang.String} object.
     */
    public void showMessage(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    /** {@inheritDoc} */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Message) {
            Message message = (Message) arg;

            if (message.getData() instanceof String)
                appendMessage(message);
            else if (message.getType() == Message.Type.REQUEST_FILE) {
                FileInfo fileInfo = (FileInfo) message.getData();
                String username = message.getUser().getUsername();
                fileRequested(username, fileInfo);
            } else if (message.getData() instanceof FileInfo) {
                fileRequest((FileInfo) message.getData(), message.getUser());
            }
        } else if (arg instanceof String) {
            String str = (String) arg;
            switch (str) {
            case "update-users":
                setUsersList(controller.getUsernames());
                break;
            default:
                break;
            }
        } else if (arg instanceof String[]) {
            String str[] = (String[]) arg;
            switch (str[0]) {
            case "file-sent":
                JOptionPane.showMessageDialog(getParent(), "File " + str[3] + " sent!");
                break;
            case "file-received":
                JOptionPane.showMessageDialog(getParent(), "File " + str[3] + " received!");
                break;
            default:
                break;
            }
        }
    }

    /**
     * <p>
     * fileRequest.
     * </p>
     *
     * @param fileInfo a {@link chatroomlibrary.FileInfo} object.
     * @param user a {@link chatroomlibrary.User} object.
     */
    public void fileRequest(FileInfo fileInfo, User user) {
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Would You Like to accept file (" + fileInfo.getName() + ") from " + user.getUsername() + "?",
                "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setAcceptAllFileFilterUsed(false);

            int returnValue = jfc.showSaveDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();

                try {
                    controller.acceptFile(selectedFile.getCanonicalPath(), user, fileInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {

        }
    }

    /**
     * <p>fileRequested.</p>
     *
     * @param username a {@link java.lang.String} object.
     * @param fileInfo a {@link chatroomlibrary.FileInfo} object.
     */
    public void fileRequested(String username, FileInfo fileInfo) {
        int dialogResult = JOptionPane.showConfirmDialog(this,
                username + "Requested (" + fileInfo.getName() + ")! Send?", "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            controller.acceptTransfer(username, fileInfo);
        }
    }

    /**
     * <p>
     * setUsersList.
     * </p>
     *
     * @param usernames an array of {@link java.lang.String} objects.
     */
    public void setUsersList(String[] usernames) {
        jListUsers.setListData(usernames);
    }

    /**
     * <p>
     * Getter for the field <code>title</code>.
     * </p>
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>
     * Setter for the field <code>title</code>.
     * </p>
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> jListUsers;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jsbtn;
    private javax.swing.JTextPane jtextFilDiscu;
    private javax.swing.JTextField jtextInputChat;
    private javax.swing.JScrollPane jtextInputChatSP;
    private javax.swing.JButton jubtn;
    // End of variables declaration//GEN-END:variables
}
