package server;

import java.io.IOException;

import chatroomlibrary.Message;

class UserHandler implements Runnable {

    private final Server server;
    private final User user;

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
        } catch (IOException e) {
            System.out.println(user.getUsername() + " : has disconnected!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                user.getSocket().close();
                user.getObjectInputStream().close();
                user.getObjectInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // end of Thread
        server.removeUser(user);
        this.server.broadcastAllUsers();
    }
}
