package server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import chatroomlibrary.Command;
import chatroomlibrary.User;
import server.Constants;
import server.model.Client;

class Authentication implements Runnable {

    private final ServerController controller;
    private Socket socket;

    public Authentication(ServerController controller, Socket socket) {
        this.controller = controller;
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectOutputStream out;
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Command command = (Command) in.readObject();
            User user = command.getMessage().getUser();

            if (command == null || user == null) {
                return;
            }

            if (!Constants.DEBUG) {
                if (command.getAction() == Command.Action.LOGIN) {
                    if (!controller.authenticate(user)) {
                        controller.loginFailed(user);
                        out.writeObject(new Command(Command.Action.LOGIN_FAILED));
                        out.flush();
                    }
                } else if (command.getAction() == Command.Action.REGISTER) {
                    if (!controller.register(user)) {
                        controller.loginFailed(user);
                        out.writeObject(new Command(Command.Action.LOGIN_FAILED));
                        out.flush();
                    }
                }
            }

            Client client = new Client(user, socket, in, out);

            client.getObjectOutputStream().writeObject(new Command(Command.Action.LOGGED));
            client.getObjectOutputStream().flush();

            controller.addClient(client);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
