package server.network.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import chatroomlibrary.Message;
import chatroomlibrary.User;
import server.Constants;
import server.controller.ServerController;
import server.model.Client;

class Authentication implements Runnable {

    private final ServerController controller;
    private Socket socket;

    /**
     * <p>Constructor for Authentication.</p>
     *
     * @param controller a {@link server.controller.ServerController} object.
     * @param socket a {@link java.net.Socket} object.
     */
    public Authentication(ServerController controller, Socket socket) {
        this.controller = controller;
        this.socket = socket;
    }

    /**
     * <p>run.</p>
     */
    public void run() {
        try {
            ObjectOutputStream out;
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Message message = (Message) in.readObject();
            User user = message.getUser();

            if (message == null || user == null) {
                return;
            }

            if (!Constants.DEBUG) {
                if (message.getType() == Message.Type.LOGIN) {
                    if (!controller.authenticate(user)) {
                        controller.loginFailed(user);
                        out.writeObject(new Message(Message.Type.LOGIN_FAILED));
                        out.flush();
                        return;
                    }
                } else if (message.getType() == Message.Type.REGISTER) {
                    if (!controller.register(user)) {
                        controller.loginFailed(user);
                        out.writeObject(new Message(Message.Type.LOGIN_FAILED));
                        out.flush();
                        return;
                    }
                }
            }

            Client client = new Client(user, socket, in, out);

            client.getTcpOut().writeObject(new Message(Message.Type.LOGGED));
            client.getTcpOut().flush();

            controller.addClient(client);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
