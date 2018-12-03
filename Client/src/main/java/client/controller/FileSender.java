package client.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import chatroomlibrary.FileInfo;
import chatroomlibrary.User;

public class FileSender implements Runnable {

    private Socket socket = null;
    private FileInfo fileInfo;
    private ChatController controller;
    private User user;

    public FileSender(ChatController controller, User user, int port, FileInfo fileInfo) {
        this.controller = controller;
        this.fileInfo = fileInfo;
        this.user = user;

        try {
            socket = new Socket(user.getHost(), port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        FileInputStream fis = null;

        try {
            int read = 0;

            dos = new DataOutputStream(socket.getOutputStream());
            fis = new FileInputStream(fileInfo.getPath());
            byte[] buffer = new byte[4096];

            while ((read = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                dos.close();

                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                controller.fileSent(fileInfo.getName(), user.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}