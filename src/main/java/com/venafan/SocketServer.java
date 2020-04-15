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
    private final ServerSocket serverSocket;

    public SocketServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    public static void receive(Socket socket) throws IOException {
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
//        String rootPath = System.getProperty("user.home") + "/";
        String rootPath = "D:\\Temp\\";

        String currentPath = rootPath;

        int fileCount = inputStream.readInt();
        print("文件数: " + fileCount);

        for (int i = 0; i < fileCount; i++) {

            int dirLength = inputStream.readInt();
            print("目录名长度: " + dirLength);
            if (dirLength > 0) {
                byte[] dirBytes = new byte[dirLength];
                inputStream.read(dirBytes);
                String dirPath = new String(dirBytes);
                print("目录路径: " + dirPath);
                File dir = new File(rootPath + dirPath);
                if (!dir.exists() && !dir.mkdirs()) {
                    throw new IOException("创建目录失败：" + dir.getPath());
                }
                currentPath = dir.getPath() + "/";
            }
            int filenameLength = inputStream.readInt();
            print("文件名长度: " + filenameLength);
            byte[] filenameBytes = new byte[filenameLength];
            inputStream.read(filenameBytes);
            print("文件名: " + new String(filenameBytes));

            long fileLength = inputStream.readLong();
            print("文件长度: " + fileLength);

            byte[] fileBytes;
            if (fileLength < BUFFER_SIZE) {
                fileBytes = new byte[(int)fileLength];
            } else {
                fileBytes = new byte[BUFFER_SIZE];
            }

            String filename = new String(filenameBytes);

            File file = new File(currentPath + filename);
            OutputStream outputStream = new FileOutputStream(file);

            int length;
            long remaining = fileLength;
            while (remaining > 0 && (length = inputStream.read(fileBytes)) != -1) {
                outputStream.write(fileBytes, 0, length);
                remaining -= length;
                if (remaining < BUFFER_SIZE && remaining > 0) {
                    fileBytes = new byte[(int) remaining];
                }
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
