package server.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;

import chatroomlibrary.Message;
import server.controller.ServerController;

/**
 * <p>KeepAlive class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class KeepAlive implements Runnable {

    /** Constant <code>RECEBIDO="RECEBIDO"</code> */
    public static final String RECEBIDO = "RECEBIDO";

    /** Constant <code>MAX_SIZE=1000</code> */
    public static final int MAX_SIZE = 1000;

    private static final int TIMER = 15;
    private DatagramPacket packet;
    private DatagramSocket socket;
    private ByteArrayOutputStream bOut;
    private ObjectOutputStream out;
    private Message message;
    private ObjectInputStream in;
    private ServerSocket serverSocket;
    private ServerController controller;

    /**
     * <p>Constructor for KeepAlive.</p>
     *
     * @param controller a {@link server.controller.ServerController} object.
     * @param packet a {@link java.net.DatagramPacket} object.
     * @param socket a {@link java.net.DatagramSocket} object.
     * @param TCPPort a int.
     * @param nome a {@link java.lang.String} object.
     * @param serverSocket a {@link java.net.ServerSocket} object.
     * @throws java.io.IOException if any.
     */
    public KeepAlive(ServerController controller, DatagramPacket packet, DatagramSocket socket, int TCPPort,
            String nome, ServerSocket serverSocket) throws IOException {
        this.controller = controller;
        this.serverSocket = serverSocket;
        this.packet = packet;
        this.socket = socket;
        bOut = new ByteArrayOutputStream();
        out = new ObjectOutputStream(bOut);

        message = new Message(Message.Type.KEEP_ALIVE);
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        while (controller.isRunning()) {
            try {
                Thread.sleep(TIMER * 1000);
            } catch (InterruptedException ex) {
            }

            try {
                out.writeObject(message);
                out.flush();

                packet.setData(bOut.toByteArray());
                packet.setLength(bOut.size());

                socket.send(packet);

                socket.setSoTimeout(TIMER * 1000);
                packet = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
                socket.receive(packet);
                in = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));

                Message response = (Message) in.readObject();
                if (response.getType() == Message.Type.IM_ALIVE) {
                    System.out.println("Yesss");
                }

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                }
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
