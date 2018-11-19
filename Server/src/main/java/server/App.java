package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
//import java.util.Set;

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

        System.out.println("The chat server is running.");

        try {
            new ChatServer(new ServerSocket(PORT), dbHelper).run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Close connection with database
         */
        dbHelper.close();
    }

}
