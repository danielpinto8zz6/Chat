/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import client.controller.ChatController;

/**
 *
 * @author daniel
 */
public class ChatView extends javax.swing.JPanel implements Observer {
    private static final long serialVersionUID = 1L;
    private ChatController controller;

    /**
     * Creates new form NewJPanel
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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jsbtn = new javax.swing.JButton();
        jtextInputChatSP = new javax.swing.JScrollPane();
        jtextInputChat = new javax.swing.JTextField();
        jubtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtextFilDiscu = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtextListUsers = new javax.swing.JTextPane();

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

        jtextListUsers.setEditable(false);
        jtextListUsers.setContentType("text/html"); // NOI18N
        jScrollPane4.setViewportView(jtextListUsers);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(jtextInputChatSP, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup().addComponent(jubtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jsbtn,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, 82,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane4))
                .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jtextInputChatSP)
                                .addComponent(jubtn, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                .addComponent(jsbtn, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents

    private void jubtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jubtnActionPerformed
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(this);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            controller.sendFile(selectedFile);
        }
    }// GEN-LAST:event_jubtnActionPerformed

    private void jsbtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jsbtnActionPerformed
        controller.sendMessage(jtextInputChat.getText().trim());
        jtextInputChat.requestFocus();
        jtextInputChat.setText(null);
    }// GEN-LAST:event_jsbtnActionPerformed

    private void jtextInputChatKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jtextInputChatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            controller.sendMessage(jtextInputChat.getText().trim());
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

    public void appendMessage(Message message) {
        System.out.println(message.getText());
        if (message.getTo() != null)
            appendToPane(jtextFilDiscu, "(<b>Private</b>)" + "@" + message.getUser().getUsername() + "<span> : "
                    + message.getText() + "</span>");
        else
            appendToPane(jtextFilDiscu, "@" + message.getUser().getUsername() + " : " + message.getText());
    }

    public void appendText(String text) {
        appendToPane(jtextFilDiscu, text);
    }

    public void showMessage(String text) {
        JOptionPane.showMessageDialog(null, text);
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
            case "filerequest":
                fileRequest();
                break;
            default:
                break;
            }
        }
    }

    private void fileRequest() {
        int dialogResult = JOptionPane.showConfirmDialog(this, "Would You Like to accept file?", "Warning",
                JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setAcceptAllFileFilterUsed(false);

            int returnValue = jfc.showSaveDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());

                controller.acceptFile(selectedFile.getAbsolutePath());
            }
        } else {

        }
    }

    public void updateUsersList(ArrayList<User> users) {
        jtextListUsers.setText(null);
        for (User user : users) {
            appendToPane(jtextListUsers, "@" + user.getUsername());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jsbtn;
    private javax.swing.JTextPane jtextFilDiscu;
    private javax.swing.JTextField jtextInputChat;
    private javax.swing.JScrollPane jtextInputChatSP;
    private javax.swing.JTextPane jtextListUsers;
    private javax.swing.JButton jubtn;
    // End of variables declaration//GEN-END:variables
}
