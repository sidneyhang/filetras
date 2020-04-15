package com.venafan;

import java.io.IOException;
import java.net.*;

/**
 * <p>
 *
 * </p>
 *
 * @author sidney
 * @version V1.0
 * @date Created in 2020/4/15 22:30
 */
public class UDPClient {
    private static final int PORT = 9191;
    private final DatagramSocket socket;

    public UDPClient() throws SocketException {
        socket = new DatagramSocket(PORT);
    }

    public void send() throws IOException {
        byte[] ipAddress = InetAddress.getLocalHost().getAddress();

        DatagramPacket packet = new DatagramPacket(ipAddress, ipAddress.length,
                InetAddress.getByName("192.168.31.255"), PORT);
        socket.send(packet);
    }

    public void close() {
        socket.close();
    }


}
