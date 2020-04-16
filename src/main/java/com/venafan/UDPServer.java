package com.venafan;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

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
    private volatile static DatagramSocket socket;
    private static String ip;
    private static final Object LOCK = new Object();

    public UDPServer() {

    }

    public void init() {
        try {
            if (socket == null) {
                synchronized (LOCK) {
                    if (socket == null) {
                        System.out.println("init");
                        socket = new DatagramSocket(PORT);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void accept() throws IOException {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        byte[] receive = packet.getData();
        System.out.println(new String(receive));
    }

    public void send() throws IOException {
        String localIp = ip;
        if (ip == null || "".equals(ip)) {
            localIp = getIPAddress();
        }
        byte[] ipAddress = localIp.getBytes();
        DatagramPacket packet = new DatagramPacket(ipAddress, ipAddress.length,
                InetAddress.getByName("192.168.31.255"), PORT);
        socket.send(packet);
    }

    public void close() {
        if (!socket.isClosed()) {
            socket.close();
        }
    }

    private String getIPAddress() {
        try {
            String ip = "";
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address) {
                        ip = inetAddress.getHostAddress();
                    }
                }
            }
            System.out.println("IP: " + ip);
            return ip;
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}
