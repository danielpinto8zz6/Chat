package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {
    public static String getAdress() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}