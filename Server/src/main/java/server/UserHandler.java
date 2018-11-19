package server;

import java.io.EOFException;
import java.io.IOException;

import chatroomlibrary.Message;

public class UserHandler implements Runnable {

    private Server server;
    private User user;

    public UserHandler(Server server, User user) {
        this.server = server;
        this.user = user;
        this.server.broadcastAllUsers();
    }

    public void run() {
        Message message;

        try {
            while ((message = (Message) this.user.getObjectInputStream().readObject()) != null) {
                server.broadcastMessages(message);
            }
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // end of Thread
        server.removeUser(user);
        this.server.broadcastAllUsers();
    }
}
