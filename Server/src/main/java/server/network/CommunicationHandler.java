package server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import server.controller.ServerController;
import server.model.Client;
import server.network.rmi.RmiService;
import server.network.tcp.TCPListener;
import server.network.udp.UDPListener;
import server.network.udp.UDPMessageSender;

public class CommunicationHandler {
    private ServerController controller;
    public RmiService rmiService;
    private UDPMessageSender udpMessageSender;

    public CommunicationHandler(ServerController controller) {
        this.controller = controller;
        udpMessageSender = new UDPMessageSender();
        registerRmiService();
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

    public void startUdp() {
        try {
            Thread thread = new Thread(new UDPListener(controller, controller.getUdpPort()));
            thread.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendUDPMessage(Message message, User user) {
        try {
            udpMessageSender.sendMessage(message, user.getAddress(), user.getUdpPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTCPMessage(Client client, Message message) {
        try {
            client.getTcpOut().writeObject(message);
            client.getTcpOut().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeRmi() {
        try {
            Naming.unbind("rmi://127.0.0.1/ObservacaoSistema");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
			e.printStackTrace();
		}
    }
}