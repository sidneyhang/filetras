package com.venafan;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

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

    public SocketClient() {

    }

    public void connection() throws IOException {
        connection(DEFAULT_HOST);
    }

    public void connection(String host) throws IOException {
        socket = new Socket(host, PORT);
    }

    public void send(FileData fileData) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        String data = JSON.toJSONString(fileData);
        outputStream.write(data.getBytes());
        socket.shutdownOutput();
    }
}
