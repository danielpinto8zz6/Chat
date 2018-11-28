package client.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileSender implements Runnable {

    private Socket socket;
    private File file;

    public FileSender(String host, int port, File file) {
        this.file = file;
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        FileInputStream fis = null;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            fis = new FileInputStream(file);
            byte[] buffer = new byte[4096];

            int read;
            while ((read = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}