package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.cj.jdbc.Driver;

import chatroomlibrary.User;

/**
 * @author daniel
 */
public class DBHelper {
    private final String DB_PREFIX = "jdbc:mysql://";

    private String username;
    private String password;
    private String server;
    private String database;

    private Connection conn = null;
    private Statement stmt = null;

    public DBHelper(String username, String password, String server, String database) {
        this.username = username;
        this.password = password;
        this.server = server;
        this.database = database;

        try {
            DriverManager.registerDriver(new Driver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        try {
            conn = DriverManager.getConnection(DB_PREFIX + server + "/" + database, username, password);
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

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setHost(rs.getString("host"));
        user.setPort(rs.getInt("port"));
        user.setState(rs.getInt("state"));
        return user;
    }

    public User getUser(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id=" + id);
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User getUser(String username) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username=" + username);
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User getUserByUserNameAndPassword(String user, String pass) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int authenticate(String username, String password) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            return -1;
        }
        return -1;
    }

    public Set<User> getAllUsers() {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            Set<User> users = new HashSet<User>();
            while (rs.next()) {
                User user = extractUserFromResultSet(rs);
                users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertUser(User user) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getHost());
            ps.setInt(4, user.getPort());
            ps.setInt(5, user.getState());
            int i = ps.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET host=?, port=?, state=? WHERE username=?");
            ps.setString(1, user.getHost());
            ps.setInt(2, user.getPort());
            ps.setInt(3, user.getState());
            ps.setString(4, user.getUsername());
            int i = ps.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String username) {
        try {
            int i = stmt.executeUpdate("DELETE FROM users WHERE username=" + username);
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}