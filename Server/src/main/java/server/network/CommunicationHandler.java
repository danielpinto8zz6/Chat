package server.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import chatroomlibrary.Message;
import server.controller.ServerController;
import server.network.rmi.RmiService;
import server.network.tcp.TCPListener;

public class CommunicationHandler {
    private ServerController controller;
    public RmiService rmiService;

    public CommunicationHandler(ServerController controller) {
        this.controller = controller;
    }

    public boolean registerRmiService() {
        try {
            Registry r;

            try {
                r = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getCanonicalHostName());
            } catch (RemoteException e) {
                e.printStackTrace();
                r = LocateRegistry.getRegistry();
            }

            rmiService = new RmiService(controller);

            r.bind("ObservacaoSistema", rmiService);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void startTCP() {
        Thread thread = new Thread(new TCPListener(controller));
        thread.start();
    }

    public void sendUDPMessage(Message message, String address, int port) {
        DatagramSocket socket;

        try {
            socket = new DatagramSocket();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(message);

            byte[] data = baos.toByteArray();
            InetAddress servAddr = InetAddress.getByName(address);

            DatagramPacket pkt;

            pkt = new DatagramPacket(data, data.length, servAddr, port);
            socket.send(pkt);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}