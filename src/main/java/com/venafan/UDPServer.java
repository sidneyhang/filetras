package com.venafan;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

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
    private static Set<String> remoteIps = new HashSet<>();

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
        String remoteIp = new String(receive);
        String localIp = ip;
        if (ip == null || "".equals(ip)) {
            localIp = getIPAddress();
        }
        if (!remoteIp.equals(localIp)) {
            System.out.println(remoteIp);
            remoteIps.add(remoteIp);
        }
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

    public static Set<String> getRemoteIps() {
        return remoteIps;
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
                    if (inetAddress instanceof Inet4Address && inetAddress.isSiteLocalAddress()) {
                        ip = inetAddress.getHostAddress();
                        System.out.println("IP: " + ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

}
