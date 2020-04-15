package com.venafan;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.*;

/**
 * <p>
 *
 * </p>
 *
 * @author sidney
 * @version V1.0
 * @date Created in 2020/4/15 22:37
 */
public class BroadcastIPTask implements Runnable {

    @Override
    public void run() {
        try {
            UDPClient udpClient = new UDPClient();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
            executorService.scheduleAtFixedRate(() -> {
                try {
                    udpClient.send();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 1, 1, TimeUnit.SECONDS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
