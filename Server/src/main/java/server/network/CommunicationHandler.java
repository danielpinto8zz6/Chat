package server.network;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.controller.ServerController;
import server.network.rmi.RmiService;

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
}