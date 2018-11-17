package chatroom.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;
/**
 *
 * @author daniel
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        InetAddress IP;
        int port;
        
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
        
        
        Socket toClient;
        ServerSocket ss = null;
        DatagramSocket ds = null;
        DatagramPacket dpk;
        ByteArrayOutputStream bout;
        ObjectOutputStream oout;
        
        try{
            
            System.out.println("Vou mandar o meu porto para o cliente!\n");
            
            ss = new ServerSocket(0);
            
            bout = new ByteArrayOutputStream();
            oout = new ObjectOutputStream(bout);
            oout.writeObject(ss.getLocalPort());
            oout.flush();
            
            dpk = new DatagramPacket(bout.toByteArray(), bout.size(), InetAddress.getByName("255.255.255.255"), 5001);
            ds = new DatagramSocket();
            ds.send(dpk);
            
            System.out.println("A aguardar a resposta do Cliente");
            
            try{
                while(true){
                    toClient = ss.accept();
                    System.out.println("Recebi uma resposta!");
                }
            }catch(IOException ex){
                System.out.println("Erro ao aceder ao socket!");
            }
        } catch (IOException ex) {
            System.out.println("Nao consegui abrir o socket!");
        }
        

        /**
         * Close connection with database
         */
        dbHelper.close();

    }

}
