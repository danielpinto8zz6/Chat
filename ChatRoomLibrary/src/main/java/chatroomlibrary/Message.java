package chatroomlibrary;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    String sender;
    String text;
    LocalDateTime date;

    public Message(String user, String text){
        this.date = LocalDateTime.now();
        this.sender = user;
        this.text = text;
    }




}