package chatroom.server;

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

    }

}
