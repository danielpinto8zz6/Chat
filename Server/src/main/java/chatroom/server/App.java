/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroom.server;

/**
 *
 * @author daniel
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBHelper dbHelper = new DBHelper();
        dbHelper.open();
        // Success login
        dbHelper.login("daniel", "qwerty");

        // Failed login
        dbHelper.login("daniel", "12345");

        dbHelper.close();
    }
    
}
