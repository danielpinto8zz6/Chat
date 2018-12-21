package monitor;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import chatroomlibrary.User;
import chatroomlibrary.rmi.UserSensor;
import monitor.rmi.Rmi;

/**
 * @author daniel
 */
class App {
    private UserSensor sensor;
    private Rmi rmi;

    public void startRmi() {
        try {
            Registry r = LocateRegistry.getRegistry();
            Remote remoteService = r.lookup("ObservacaoSistema");

            sensor = (UserSensor) remoteService;

            rmi = new Rmi();
            sensor.addListener(rmi);
        } catch (NotBoundException e) {
            e.printStackTrace();
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

    /**
     * <p>
     * main.
     * </p>
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String registration = "127.0.0.1";

            if (args.length > 0) {
                registration = args[0];
            }

            Registry r = LocateRegistry.getRegistry(registration);
            String[] services = r.list();
            if (services.length == 0) {
                System.out.println("No services found");
                return;
            }

            registration = "rmi://" + registration + "/" + services[0];
            Remote remoteService = r.lookup(services[0]);
            System.out.println(registration);
            UserSensor sensor = (UserSensor) remoteService;

            List<User> users = sensor.getUsers();
            System.out.println("Users:");
            for (User user : users) {
                System.out.println("Address -> " + user.getAddress());
                System.out.println("TCP Port -> " + user.getTcpPort());
                System.out.println("UDP Port -> " + user.getUdpPort());
            }

            RmiMonitor monitor = new RmiMonitor();
            sensor.addListener(monitor);

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
