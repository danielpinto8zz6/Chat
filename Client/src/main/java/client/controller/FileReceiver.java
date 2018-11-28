package client.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver implements Runnable {

    private ServerSocket serverSocket;
    private File file;
    private String path;

    public FileReceiver(int port, File file, String path) {
        this.file = file;
        this.path = path;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile(Socket clientSock) throws IOException {
        String newFile = path + File.separator + file.getName();
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream(newFile);
        byte[] buffer = new byte[4096];

        int filesize = (int) file.length();
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }

        fos.close();
        dis.close();

        System.out.println("File received! Exiting!");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSock = serverSocket.accept();
                saveFile(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}