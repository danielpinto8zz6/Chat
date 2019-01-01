package server.database;

import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.cj.jdbc.Driver;

/**
 * <p>DbHelper class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class DbHelper {
    private static String jdbcDriver = "jdbc:mysql";
    private static String jdbcUrl = "127.0.0.1/chatroom";
    private static String jdbcLogin = "root";
    private static String jdbcPasswd = "";
    private static Connection conn = null;

    /**
     * <p>getConnection.</p>
     *
     * @return a {@link java.sql.Connection} object.
     */
    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        } else {
            open();
            return conn;
        }
    }

    /**
     * <p>open.</p>
     */
    public static void open() {
        try {
            DriverManager.registerDriver(new Driver());
            conn = DriverManager.getConnection(jdbcDriver + "://" + jdbcUrl, jdbcLogin, jdbcPasswd);
        } catch (Exception error) {
            System.out.println("Database connection can not be created!");
            System.out.println("Error description: " + error.toString());
        }
    }

    /**
     * <p>close.</p>
     */
    public static void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ignore) {
            }
        }
    }
}
