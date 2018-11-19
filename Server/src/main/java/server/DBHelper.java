// package server;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.HashSet;
// import java.util.Set;
// import java.util.logging.Level;
// import java.util.logging.Logger;

// import com.mysql.cj.jdbc.Driver;

// /**
//  * @author daniel
//  */
// public class DBHelper {
//     private final String DB_PREFIX = "jdbc:mysql://";

//     private String username;
//     private String password;
//     private String server;
//     private String database;

//     private Connection conn = null;
//     private Statement stmt = null;

//     public DBHelper(String username, String password, String server, String database) {
//         this.username = username;
//         this.password = password;
//         this.server = server;
//         this.database = database;

//         try {
//             DriverManager.registerDriver(new Driver());
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     public void open() {
//         try {
//             conn = DriverManager.getConnection(DB_PREFIX + server + "/" + database, username, password);
//             stmt = conn.createStatement();
//         } catch (SQLException ex) {
//             Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
//         }
//     }

//     /**
//      * @param database the database to set
//      */
//     public void setDatabase(String database) {
//         this.database = database;
//     }

//     /**
//      * @param server the server to set
//      */
//     public void setServer(String server) {
//         this.server = server;
//     }

//     /**
//      * @param username the username to set
//      */
//     public void setUsername(String username) {
//         this.username = username;
//     }

//     /**
//      * @param password the password to set
//      */
//     public void setPassword(String password) {
//         this.password = password;
//     }

//     public void close() {
//         try {
//             stmt.close();
//             conn.close();
//         } catch (SQLException ex) {
//             Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
//         }
//     }

//     private User extractUserFromResultSet(ResultSet rs) throws SQLException {
//         User user = new User(null, null, null);
//         user.setId(rs.getInt("id"));
//         user.setUsername(rs.getString("username"));
//         user.setPassword(rs.getString("password"));
//         user.setState(rs.getInt("state"));
//         return user;
//     }

//     public User getUser(int id) {
//         try {
//             ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id=" + id);
//             if (rs.next()) {
//                 return extractUserFromResultSet(rs);
//             }
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }

//     public User getUserByUserNameAndPassword(String user, String pass) {
//         try {
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
//             ps.setString(1, user);
//             ps.setString(2, pass);
//             ResultSet rs = ps.executeQuery();
//             if (rs.next()) {
//                 return extractUserFromResultSet(rs);
//             }
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }

//     public Set<User> getAllUsers() {
//         try {
//             ResultSet rs = stmt.executeQuery("SELECT * FROM users");
//             Set<User> users = new HashSet<User>();
//             while (rs.next()) {
//                 User user = extractUserFromResultSet(rs);
//                 users.add(user);
//             }
//             return users;
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }

//     public boolean insertUser(User user) {
//         try {
//             PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?)");
//             ps.setString(1, user.getUsername());
//             ps.setString(2, user.getPassword());
//             ps.setInt(3, user.getState());
//             int i = ps.executeUpdate();
//             if (i == 1) {
//                 return true;
//             }
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//         return false;
//     }

//     public boolean updateUser(User user) {
//         try {
//             PreparedStatement ps = conn.prepareStatement("UPDATE users SET name=?, pass=?, age=? WHERE id=?");
//             ps.setString(1, user.getUsername());
//             ps.setString(2, user.getPassword());
//             ps.setInt(4, user.getId());
//             int i = ps.executeUpdate();
//             if (i == 1) {
//                 return true;
//             }
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//         return false;
//     }

//     public boolean deleteUser(int id) {
//         try {
//             int i = stmt.executeUpdate("DELETE FROM users WHERE id=" + id);
//             if (i == 1) {
//                 return true;
//             }
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//         return false;
//     }
// }