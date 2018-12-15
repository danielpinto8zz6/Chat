package chatroomlibrary;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {
    public static String getaddress() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}