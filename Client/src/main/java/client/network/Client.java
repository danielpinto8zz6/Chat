package client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import chatroomlibrary.Command;
import chatroomlibrary.Message;
import chatroomlibrary.User;
import chatroomlibrary.rmi.UserSensor;
import client.controller.ChatController;
import client.network.rmi.Rmi;
import client.network.tcp.TcpListener;

public class Client {
    private Socket tcpSocket = null;
    private ObjectOutputStream tcpOut = null;
    private ObjectInputStream tcpIn = null;

    private Thread tcpListener = null;
    private ChatController controller;

    private Rmi rmi;
    private UserSensor sensor;

    public Client(ChatController controller) {
        this.controller = controller;
    }

    public void startTcp() {
        if (tcpIn == null)
            return;

        tcpListener = new Thread(new TcpListener(controller, tcpIn));
        tcpListener.start();
    }

    public void stopTcp() {
        if (tcpListener != null)
            tcpListener.interrupt();
        try {
            tcpOut.close();
            tcpIn.close();
            tcpSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startUdp() {
    }

    public void stopUdp() {
    }

    public void disconnect() {
        stopTcp();
        stopUdp();
        stopRmi();
    }

    public void createTcpConnection(User user, Command.Action action) {
        try {
            tcpSocket = new Socket(user.getAddress(), user.getTcpPort());
            tcpIn = new ObjectInputStream(tcpSocket.getInputStream());
            tcpOut = new ObjectOutputStream(tcpSocket.getOutputStream());

            tcpOut.writeObject(new Command(action, new Message(user)));
            tcpOut.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startTcp();
    }

    public void createUdpConnection() {

    }

    public void sendTcpCommand(Command command) {
        try {
            tcpOut.writeObject(command);
            tcpOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void startRmi() {
        try {
            Registry r = LocateRegistry.getRegistry();
            Remote remoteService = r.lookup("ObservacaoSistema");

            sensor = (UserSensor) remoteService;

            rmi = new Rmi(controller);
            sensor.addListener(rmi);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            controller.updateUsers((ArrayList<User>) sensor.getUsers());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopRmi() {
        try {
            sensor.removeListener(rmi);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}