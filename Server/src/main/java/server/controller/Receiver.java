package server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import chatroomlibrary.Command;
import chatroomlibrary.User;
import server.model.Client;

public class Receiver implements Runnable {
    private ServerController controller;
    boolean testing = true;

    public Receiver(ServerController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(controller.getPort());

            while (true) {
                // accepts a new client
                Socket socket = server.accept();

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                Command command = (Command) in.readObject();
                User user = command.getMessage().getUser();

                if (!testing) {
                    if (command.getAction() == Command.Action.LOGIN) {
                        if (!controller.authenticate(user) && !testing) {
                            System.out.println("User not autenticated");
                            out.writeObject(new Command(Command.Action.LOGIN_FAILED));
                            out.flush();
                            continue;
                        }
                    } else if (command.getAction() == Command.Action.REGISTER) {
                        if (!controller.register(user)) {
                            out.writeObject(new Command(Command.Action.LOGIN_FAILED));
                            out.flush();
                            continue;
                        }
                    }
                }

                System.out.println("New Client: " + user.getUsername() + "\n\t     Host:"
                        + socket.getInetAddress().getHostAddress());

                Client newClient = new Client(user, socket, in, out);

                controller.addClient(newClient);

                newClient.getObjectOutputStream().writeObject(new Command(Command.Action.LOGGED));
                newClient.getObjectOutputStream().flush();

                controller.handleClient(newClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (server != null)
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}