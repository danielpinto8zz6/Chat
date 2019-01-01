/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import client.controller.ChatController;

/**
 * <p>
 * LoginView class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class LoginView extends javax.swing.JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    private ChatController controller;

    /**
     * Creates new form LoginView
     *
     * @param controller a {@link client.controller.ChatController} object.
     */
    public LoginView(ChatController controller) {
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButtonLogin = new javax.swing.JButton();
        jButtonRegister = new javax.swing.JButton();
        jTextFieldUsername = new javax.swing.JTextField();
        jTextFieldHost = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jPasswordField = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldTCPPort = new javax.swing.JTextField();
        jTextFieldUDPPort = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        jLabel1.setText("Username");

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jButtonRegister.setText("Register");
        jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegisterActionPerformed(evt);
            }
        });

        jLabel2.setText("Password");

        jLabel3.setText("Host");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel4.setText("Autentication");

        jLabel5.setText("TCP Port");

        jLabel6.setText("UDP Port");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup().addComponent(jSeparator1).addGap(12, 12, 12))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                        layout.createSequentialGroup().addComponent(jSeparator2).addContainerGap())))
                .addGroup(layout.createSequentialGroup().addGap(223, 223, 223).addComponent(jLabel4).addGap(0, 0,
                        Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                        layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(227, 227, 227))
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1).addComponent(jTextFieldUsername,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 330,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup().addComponent(jLabel2)
                                                        .addGap(265, 271, Short.MAX_VALUE))
                                                .addComponent(jPasswordField)))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup().addComponent(jLabel3)
                                                        .addGap(303, 303, 303))
                                                .addGroup(layout.createSequentialGroup().addComponent(jTextFieldHost)
                                                        .addGap(4, 4, 4)))
                                        .addGap(8, 8, 8)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextFieldTCPPort, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup().addComponent(jLabel6).addGap(0,
                                                        0, Short.MAX_VALUE))
                                                .addComponent(jTextFieldUDPPort))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(
                        jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1)
                        .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3)
                        .addComponent(jLabel5).addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldHost, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldTCPPort, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldUDPPort, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 75,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 75,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonRegisterActionPerformed
        String username = getUsername();
        String password = getPassword();
        String host = getHost();
        int tcpPort = getTcpPort();
        int udpPort = getUdpPort();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAddress(host);
        user.setTcpPort(tcpPort);
        user.setUdpPort(udpPort);

        controller.createConnection(user, Message.Type.REGISTER);
    }// GEN-LAST:event_jButtonRegisterActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonLoginActionPerformed
        String username = getUsername();
        String password = getPassword();
        String host = getHost();
        int tcpPort = getTcpPort();
        int udpPort = getUdpPort();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAddress(host);
        user.setTcpPort(tcpPort);
        user.setUdpPort(udpPort);

        controller.createConnection(user, Message.Type.LOGIN);
    }// GEN-LAST:event_jButtonLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextFieldHost;
    private javax.swing.JTextField jTextFieldTCPPort;
    private javax.swing.JTextField jTextFieldUDPPort;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables

    /**
     * <p>
     * getUsername.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUsername() {
        return jTextFieldUsername.getText().trim();
    }

    /**
     * <p>
     * getPassword.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPassword() {
        return new String(jPasswordField.getPassword());
    }

    /**
     * <p>
     * getHost.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getHost() {
        return jTextFieldHost.getText().trim();
    }

    /**
     * <p>getTcpPort.</p>
     *
     * @return a int.
     */
    public int getTcpPort() {
        return Integer.parseInt(jTextFieldTCPPort.getText().trim());
    }

    /**
     * <p>getUdpPort.</p>
     *
     * @return a int.
     */
    public int getUdpPort() {
        return Integer.parseInt(jTextFieldUDPPort.getText().trim());
    }

    /** {@inheritDoc} */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String str = (String) arg;
            switch (str) {
            case "login-failed":
                JOptionPane.showMessageDialog(this, "Login failed!");
                break;
            default:
                break;
            }
        }
    }
}
