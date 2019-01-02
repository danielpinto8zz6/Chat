package server.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLException;

import chatroomlibrary.Message;
import chatroomlibrary.NotFoundException;
import chatroomlibrary.User;
import chatroomlibrary.UserDao;
import chatroomlibrary.Utils;
import server.controller.ServerController;
import server.database.DbHelper;

/**
 * <p>
 * KeepAlive class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class KeepAlive implements Runnable {
    private Message message;
    private byte[] buffer;
    private ServerController controller;
    private DatagramSocket socket;

    /**
     * <p>Constructor for KeepAlive.</p>
     *
     * @param controller a {@link server.controller.ServerController} object.
     */
    public KeepAlive(ServerController controller) {
        this.controller = controller;

        message = new Message(Message.Type.KEEP_ALIVE);
        try {
            buffer = Utils.convertToBytes(message);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        while (controller.isRunning()) {
            // Do the check every 15 seconds
            try {
                Thread.sleep(15 * 1000);
            } catch (InterruptedException ex) {
            }

            for (User user : controller.getLogggedUsers()) {
                try {
                    System.out.println("[DEBUG] Sending KEEPALIVE to : " + user.getUsername());

                    InetAddress address = InetAddress.getByName(user.getAddress());
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, user.getUdpPort());
                    socket.send(packet);

                    // response
                    byte[] incomingData = new byte[1024];
                    DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);

                    // wait 2 seconds for response
                    socket.setSoTimeout(2 * 1000);
                    socket.receive(incomingPacket);
                    Object readObject;

                    readObject = Utils.convertFromBytes(incomingPacket.getData());
                    Message message = (Message) readObject;
                    if (message.getType() == Message.Type.ACK) {
                        System.out.println("[DEBUG] " + message.getUser().getUsername() + " is alive");
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    user.setFailures(user.getFailures() + 1);
                    if (user.getFailures() == 3) {
                        user.setState(0);
                    }

                    try {
                        UserDao.save(DbHelper.getConnection(), user);
                    } catch (NotFoundException | SQLException e1) {
                        e1.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
