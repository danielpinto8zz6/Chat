package chatroom.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Handler;

/**
 *
 * @author daniel
 */
public class App {

    /**
     * The port that the server listens on.
     */
    private static final int PORT = 9001;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        InetAddress IP;

        try {
            IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := " + IP.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        /**
         * Start dbHelper with default values
         */
        DBHelper dbHelper = new DBHelper("sql2264793", "jG1%xX8!", "sql2.freemysqlhosting.net", "sql2264793");

        if (args.length > 3) {
            dbHelper.setUsername(args[0]);
            dbHelper.setPassword(args[1]);
            dbHelper.setServer(args[2]);
            dbHelper.setDatabase(args[3]);
        }

        /**
         * Open connection with database
         */
        dbHelper.open();

        /**
         * This is the login, so if the returned user is null login isn't succeed,
         * otherwise it will return the user
         */
        User user = dbHelper.getUserByUserNameAndPassword("daniel", "qwerty");
        if (user != null) {
            System.out.println("Login succeed");
        } else {
            System.out.println("Login failed");
        }

        /**
         * Show all users in database
         */
        Set<User> users = dbHelper.getAllUsers();
        for (User u : users) {
            System.out.println(u.getId() + " " + u.getUsername());
        }

        /**
         * Close connection with database
         */
        dbHelper.close();

        System.out.println("The chat server is running.");

        try {
            new ChatServer(new ServerSocket(PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
