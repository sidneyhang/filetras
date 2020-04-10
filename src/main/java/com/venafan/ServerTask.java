package com.venafan;

import java.io.IOException;
import java.net.Socket;

/**
 * <p>
 *
 * </p>
 *
 * @author sidney
 * @version V1.0
 * @date Created in 2020/4/10 11:47
 */
public class ServerTask implements Runnable {
    private Socket socket;

    public ServerTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            SocketServer.receive(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
