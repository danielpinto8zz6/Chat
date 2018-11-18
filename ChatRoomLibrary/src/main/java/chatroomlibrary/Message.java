package chatroomlibrary;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sender;
    private String text;
    private LocalDateTime date;

    public Message(String user, String text){
        this.date = LocalDateTime.now();
        this.sender = user;
        this.text = text;
    }


    public void setSender(String send){this.sender = send;}
    public String getSender(){return this.sender;}
    
    public void setText(String tex){this.text = tex;}
    public String getText(){return this.text;}
    
    public LocalDateTime getDate(){return this.date;}




}