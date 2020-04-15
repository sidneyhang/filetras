package com.venafan;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * <p>
 *
 * </p>
 *
 * @author sidney
 * @version V1.0
 * @date Created in 2020/4/11 23:18
 */
public class UDPServer {

    private static final int PORT = 9191;
    private final DatagramSocket socket;
    public UDPServer() throws SocketException {
        socket = new DatagramSocket(PORT);
    }

    public void accept() throws IOException {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        byte[] receive = packet.getData();
        System.out.println(new String(receive));
        socket.close();
    }
}
