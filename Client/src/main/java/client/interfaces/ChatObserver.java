package client.interfaces;

import java.util.ArrayList;
import java.util.Observer;

public interface ChatObserver extends Observer {
    void updateUsersList(ArrayList<String> users);
}