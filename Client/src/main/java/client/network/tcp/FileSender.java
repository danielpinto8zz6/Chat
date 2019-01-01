package client.network.tcp;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import chatroomlibrary.FileInfo;
import chatroomlibrary.User;
import client.controller.ChatController;

/**
 * <p>FileSender class.</p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class FileSender implements Runnable {

    private Socket socket = null;
    private FileInfo fileInfo;
    private ChatController controller;
    private User user;

    /**
     * <p>Constructor for FileSender.</p>
     *
     * @param controller a {@link client.controller.ChatController} object.
     * @param user a {@link chatroomlibrary.User} object.
     * @param port a int.
     * @param fileInfo a {@link chatroomlibrary.FileInfo} object.
     */
    public FileSender(ChatController controller, User user, int port, FileInfo fileInfo) {
        this.controller = controller;
        this.fileInfo = fileInfo;
        this.user = user;

        try {
            socket = new Socket(user.getAddress(), port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        DataOutputStream dos = null;
        FileInputStream fis = null;
        long startTime = System.currentTimeMillis();
        long endTime;
        byte[] buffer = new byte[fileInfo.getSize()];
        int count = 0;
        int totalRead = 0;

        try {
            dos = new DataOutputStream(socket.getOutputStream());
            fis = new FileInputStream(fileInfo.getPath());

            while ((count = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, count);
                totalRead += count;
            }

            fis.close();
            dos.close();
            socket.close();

            if (totalRead == fileInfo.getSize()) {
                endTime = System.currentTimeMillis();
                printTransferDetails(startTime, endTime, totalRead);
                controller.fileSent(fileInfo.getName(), user.getUsername());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * <p>printTransferDetails.</p>
     *
     * @param startTime a long.
     * @param endTime a long.
     * @param totalRead a int.
     */
    public void printTransferDetails(long startTime, long endTime, int totalRead) {
        System.out.println("Transfer begun......");
        System.out.println(totalRead + " bytes written in " + (endTime - startTime) + " ms.");
    }

}
