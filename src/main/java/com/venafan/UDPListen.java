package com.venafan;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author sidney
 * @version V1.0
 * @date Created in 2020/4/15 22:48
 */
public class UDPListen implements Runnable {

    @Override
    public void run() {
        try {
            UDPServer server = new UDPServer();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
            executorService.scheduleAtFixedRate(() -> {
                try {
                    server.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 1, 1, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
