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
 * @date Created in 2020/4/10 11:44
 */
public class ServerListen implements Runnable {


    @Override
    public void run() {
        try {
            SocketServer server = new SocketServer();
            while (true) {
                Socket socket = server.accept();
                Thread thread = new Thread(new ServerTask(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
