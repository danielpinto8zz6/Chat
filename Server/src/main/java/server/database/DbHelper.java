package server.database;

import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.cj.jdbc.Driver;

public class DbHelper {
    private static String jdbcDriver = "jdbc:mysql";
    private static String jdbcUrl = "127.0.0.1/chatroom";
    private static String jdbcLogin = "root";
    private static String jdbcPasswd = "";
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }

        try {
            System.out.println("Creating database connection:");

            // Connect to database. We need only one connection, -> no pooling here.
            // If you do not use MySQL database, you need to change the MySQL driver
            // to your own JDBC database driver. (See the line below:)
            DriverManager.registerDriver(new Driver());

            // Now we use the DriverManager to get the connection to Database:
            conn = DriverManager.getConnection(jdbcDriver + "://" + jdbcUrl, jdbcLogin, jdbcPasswd);

            System.out.println("Database connection ready.\n");

            return conn;
        } catch (Exception error) {
            System.out.println("Database connection can not be created!");
            System.out.println("Error description: " + error.toString());
            return null;
        }

    }

    public static Connection getConnection(String jdbcUrl, String jdbcLogin, String jdbcPasswd) {
        if (conn != null) {
            return conn;
        }

        try {
            System.out.println("Creating database connection:");

            DriverManager.registerDriver(new Driver());

            conn = DriverManager.getConnection(jdbcDriver + "://" + jdbcUrl, jdbcLogin, jdbcPasswd);

            System.out.println("Database connection ready.\n");

            return conn;
        } catch (Exception error) {
            System.out.println("Database connection can not be created!");
            System.out.println("Error description: " + error.toString());
            return null;
        }

    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ignore) {
            }
        }
    }
}
