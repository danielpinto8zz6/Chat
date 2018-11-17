/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 *
 * @author daniel
 */
public class App {

    public static int MAX_SIZE = 256;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DatagramSocket s = null;
        DatagramPacket pkt = null;
        ObjectInputStream oin;
        
        Socket toMaster;
        int tcpPortNumber;
        Thread t;
        
        try{
            
            s = new DatagramSocket(5001);
            
            pkt = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE); 
            s.receive(pkt);
            
            System.out.println("Recebi um packet!");
            
            oin = new ObjectInputStream(new ByteArrayInputStream(pkt.getData()));
            tcpPortNumber = (Integer) oin.readObject();
            
            toMaster = new Socket(pkt.getAddress(), tcpPortNumber);
//            t = new Thread(new App(), "Thread 1");
//            t.start();
            
            
        } catch (IOException ex) {
            System.out.println("Erro com o socket!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Nao existe a class pretendida");
        }
    }
    
}
