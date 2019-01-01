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

public class KeepAlive implements Runnable {

    public static final String RECEBIDO = "RECEBIDO";

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