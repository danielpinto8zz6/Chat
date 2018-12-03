/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import client.controller.ChatController;

/**
 *
 * @author daniel
 */
public class HistoryView extends javax.swing.JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    private ChatController controller;

    /**
     * Creates new form HistoryView
     * 
     * @param controller
     */
    public HistoryView(ChatController controller) {
        this.controller = controller;
        controller.addObserver(this);

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneHistory = new javax.swing.JTextPane();

        jTextPaneHistory.setEditable(false);
        jTextPaneHistory.setContentType("text/html"); // NOI18N
        jScrollPane1.setViewportView(jTextPaneHistory);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
                        .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                        .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPaneHistory;

    // End of variables declaration//GEN-END:variables
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String[]) {
            String str[] = (String[]) arg;
            switch (str[0]) {
            case "file-sent":
                addTransfer(str[1], str[2], str[3]);
                break;
            case "file-received":
                addTransfer(str[1], str[2], str[3]);
                break;
            default:
                break;
            }
        }
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

    private void addTransfer(String sender, String receiver, String transfer) {
        appendToPane(jTextPaneHistory, "(<b>" + sender + " --> " + receiver + "</b>) : " + transfer);
    }
}
