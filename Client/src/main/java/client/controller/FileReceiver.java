package client.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import chatroomlibrary.FileInfo;
import chatroomlibrary.User;

public class FileReceiver implements Runnable {

    private ServerSocket serverSocket;
    private String path;
    private ChatController controller;
    private User user;
    private FileInfo fileInfo;

    public FileReceiver(ChatController controller, int port, FileInfo fileInfo, String path, User user) {
        this.controller = controller;
        this.fileInfo = fileInfo;
        this.path = path;
        this.user = user;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile(Socket clientSock) throws IOException {
        String newFile = path + File.separator + fileInfo.getName();
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream(newFile);
        byte[] buffer = new byte[4096];

        int filesize = (int) fileInfo.getSize();
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

        if (totalRead == fileInfo.getSize()) {
            System.out.println("File received! Exiting!");
            controller.fileReceived(fileInfo.getName(), user.getUsername());
        }
    }

    @Override
    public void run() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                saveFile(socket);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}