/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import client.controller.ChatController;

/**
 * <p>
 * MainView class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
@SuppressWarnings("deprecation")
public class MainView extends javax.swing.JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    private ChatController controller;
    private ChatView chatView;

    /**
     * Creates new form MainView
     *
     * @param controller a {@link client.controller.ChatController} object.
     */
    public MainView(ChatController controller) {
        initComponents();
        this.controller = controller;
        controller.addObserver(this);
        chatView = new ChatView(controller);

        jTabbedPane.addTab("Login", new LoginView(controller));

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                controller.close();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuSetDownloads = new javax.swing.JMenu();
        jMenuItemSharedFolder = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jMenuSetDownloads.setText("File");

        jMenuItemSharedFolder.setText("Set shared folder");
        jMenuItemSharedFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSharedFolderActionPerformed(evt);
            }
        });
        jMenuSetDownloads.add(jMenuItemSharedFolder);

        jMenuItem1.setText("Set downloads folder");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuSetDownloads.add(jMenuItem1);

        jMenuItemExit.setAccelerator(
                javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuSetDownloads.add(jMenuItemExit);

        jMenuBar1.add(jMenuSetDownloads);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                jTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 700,
                Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);

        int returnValue = jfc.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();

            controller.setSaveLocation(selectedFile);
        }
    }// GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItemSharedFolderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItemSharedFolderActionPerformed
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);

        int returnValue = jfc.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            controller.setSharedFolder(selectedFile);
        }
    }// GEN-LAST:event_jMenuItemSharedFolderActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItemExitActionPerformed
        System.exit(0);
    }// GEN-LAST:event_jMenuItemExitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemSharedFolder;
    private javax.swing.JMenu jMenuSetDownloads;
    private javax.swing.JTabbedPane jTabbedPane;
    // End of variables declaration//GEN-END:variables

    /** {@inheritDoc} */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String str = (String) arg;
            switch (str) {
            case "connected":
                if (controller.logged) {
                    jTabbedPane.removeAll();
                    jTabbedPane.addTab("Chat", chatView);
                    jTabbedPane.addTab("Transfer history", new HistoryView(controller));
                    jTabbedPane.add("Files", new FilesView(controller));
                }
                break;
            default:
                break;
            }
        }
    }
}
