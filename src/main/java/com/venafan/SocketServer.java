package com.venafan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 *
 * </p>
 *
 * @author sidney
 * @version V1.0
 * @date Created in 2020/4/10 10:57
 */
public class SocketServer {

    private static final int PORT = 9190;
    private static final int BUFFER_SIZE = 4096;
    private ServerSocket serverSocket;

    public SocketServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    public static void receive(Socket socket) throws IOException {
        DataInputStream inputStream;
        OutputStream outputStream;

        inputStream = new DataInputStream(socket.getInputStream());

        int fileCount = inputStream.readInt();
        if (fileCount > 1) {

        } else {
            int filenameLength = inputStream.readInt();
            print("文件名长度: " + filenameLength);
            byte[] filenameBytes = new byte[filenameLength];
            inputStream.read(filenameBytes);
            print("文件名: " + new String(filenameBytes));

            long fileLength = inputStream.readLong();
            print("文件长度: " + fileLength);

            byte[] fileBytes = new byte[BUFFER_SIZE];

            String filename = new String(filenameBytes);
            File file = new File(System.getProperty("user.home") + "/" + filename);
            outputStream = new FileOutputStream(file);

            int length;
            while ((length = inputStream.read(fileBytes)) != -1) {

                outputStream.write(fileBytes, 0, length);
            }

            print("接收完毕");
            outputStream.close();
        }
        inputStream.close();
        socket.close();
    }

    private static void print(String str) {
        System.out.println(str);
    }
}
