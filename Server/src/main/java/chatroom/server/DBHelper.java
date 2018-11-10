package chatroom.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author daniel
 */
public class DBHelper {
    // JDBC driver name and database URL
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://sql2.freemysqlhosting.net/sql2264793";

    // Database credentials
    private static final String USER = "sql2264793";
    private static final String PASS = "jG1%xX8!";

    private Connection conn = null;
    private Statement stmt = null;

    public DBHelper() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean login(String username, String password) {
        try {
            String pass = null;
            String sql;
            sql = "SELECT password FROM `users` WHERE username = '" + username + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                pass = rs.getString("password");
            }

            rs.close();

            if (password.equals(pass)) {
                System.out.println("Login succeed");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Login failed");

        return false;
    }
}