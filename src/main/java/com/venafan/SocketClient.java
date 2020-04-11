package com.venafan;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author sidney
 * @version V1.0
 * @date Created in 2020/4/10 11:24
 */
public class SocketClient {
    private static final int PORT = 9190;
    private Socket socket;
    private static final String DEFAULT_HOST = "192.168.31.107";
    private static final int BUFFER_SIZE = 4096;

    private List<File> fileList;

    public SocketClient() {

    }

    public void connection() {
        connection(DEFAULT_HOST);
    }

    public void connection(String host) {
        try {
            socket = new Socket(host, PORT);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void send(File file) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        if (file.isDirectory()) {
            fileList = new ArrayList<>();
            getAllChild(file);
            outputStream.writeInt(fileList.size());
            String rootPath = file.getParent() + "/";

            for (File singleFile : fileList) {
                String path = "";
                String parent = singleFile.getParent();
                if (parent.length() >= rootPath.length()) {
                    path = parent.replace(rootPath, "");
                }
                sendSingleFile(singleFile, path, outputStream);
            }
        } else {
            outputStream.writeInt(1);
            sendSingleFile(file, "", outputStream);
        }
        socket.shutdownOutput();
    }

    private void sendSingleFile(File file, String path, DataOutputStream outputStream) throws IOException {

        outputStream.writeInt(path.getBytes().length);
        System.out.println("目录长度: " + path.getBytes().length);
        if (path.length() > 0) {
            outputStream.write(path.getBytes());
            System.out.println("目录名: " + path);
        }

        outputStream.writeInt(file.getName().getBytes().length);
        System.out.println("文件名长度: " + file.getName().length());

        outputStream.write(file.getName().getBytes());
        System.out.println("文件名: " + file.getName());

        outputStream.writeLong(file.length());
        System.out.println("文件长度: " + file.length());

        InputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[BUFFER_SIZE];
        int length;
        int i = 0;
        while ((length = inputStream.read(data)) != -1) {
            outputStream.write(data, 0, length);
            outputStream.flush();
            i++;
            if (i % 10 == 0) {
                outputStream.flush();
            }
        }
        outputStream.flush();
        inputStream.close();
    }

    private void getAllChild(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files == null || files.length <= 0) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                getAllChild(files[i]);
            }
        } else {
            if (!file.isHidden()) {
                fileList.add(file);
            }
        }

    }
}
