package com.venafan;

import com.alibaba.fastjson.JSON;

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
    private ServerSocket serverSocket;

    public SocketServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    public static void receive(Socket socket) throws IOException {
        InputStream inputStream;
        OutputStream outputStream;

        inputStream = socket.getInputStream();
        byte[] data = inputStream.readAllBytes();

        FileData fileData = JSON.parseObject(data, FileData.class);

        String filename = fileData.getFileName();
        File file = new File(System.getProperty("user.home") + filename);
        outputStream = new FileOutputStream(file);
        outputStream.write(fileData.getData());

        inputStream.close();
        outputStream.close();
    }
}
