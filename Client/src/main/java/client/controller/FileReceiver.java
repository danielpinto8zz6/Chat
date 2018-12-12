package client.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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

    private void saveFile(Socket socket) throws IOException {
        String newFile = path + File.separator + fileInfo.getName();
        FileOutputStream fileWritter;
        byte[] buffer = new byte[fileInfo.getSize()];
        long startTime = System.currentTimeMillis();

        int count;
        int totalRead = 0;

        try {
            fileWritter = new FileOutputStream(newFile);
            InputStream inputStream = socket.getInputStream();

            while ((count = inputStream.read(buffer)) > 0) {
                totalRead += count;
                fileWritter.write(buffer, 0, count);
            }

            fileWritter.close();

            if (totalRead == fileInfo.getSize()) {
                controller.fileReceived(fileInfo.getName(), user.getUsername());
                long endTime = System.currentTimeMillis();
                printTransferDetails(startTime, endTime, totalRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            saveFile(socket);
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printTransferDetails(long startTime, long endTime, int totalRead) {
        System.out.println("Transfer begun......");
        System.out.println(totalRead + " bytes written in " + (endTime - startTime) + " ms.");
    }
}