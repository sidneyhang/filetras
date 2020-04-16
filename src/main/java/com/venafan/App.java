package com.venafan;

import javax.swing.*;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ServerListen serverListen = new ServerListen();
        Thread thread = new Thread(serverListen);
        thread.start();

        Thread udpServer = new Thread(new UDPListen());
        udpServer.start();
        Thread udpClient = new Thread(new BroadcastIPTask());
        udpClient.start();

        JFrame jf = new JFrame("测试窗口");
        jf.setSize(380, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel(layout);

        final JTextArea textArea = new JTextArea(10, 30);
        textArea.setLineWrap(true);
        panel.add(textArea);

        MyFileChooser fileChooser = new MyFileChooser();

        JButton openBtn = new JButton("打开");
        openBtn.addActionListener((event) -> {
            fileChooser.showFileOpenDialog(jf, textArea);
        });

        panel.add(openBtn);

        JButton sendBtn = new JButton("发送");
        sendBtn.addActionListener((event) -> {
            SocketClient client = new SocketClient();
            client.connection();
            try {
                client.send(fileChooser.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        panel.add(sendBtn);

        JButton refreshBtn = new JButton("刷新");
        refreshBtn.addActionListener((event) -> {
            String remoteIps = UDPServer.getRemoteIps().toString();
            textArea.append(remoteIps);
            System.out.println(remoteIps);
        });
        panel.add(refreshBtn);

        SpringLayout.Constraints openBtnCons = layout.getConstraints(openBtn);

        openBtnCons.setX(Spring.constant(10));
        openBtnCons.setY(Spring.constant(10));

        SpringLayout.Constraints sendBtnCons = layout.getConstraints(sendBtn);
        sendBtnCons.setX(Spring.sum(openBtnCons.getConstraint(SpringLayout.EAST), Spring.constant(10)));
        sendBtnCons.setY(Spring.constant(10));

        SpringLayout.Constraints refreshBtnCons = layout.getConstraints(refreshBtn);
        refreshBtnCons.setX(Spring.sum(sendBtnCons.getConstraint(SpringLayout.EAST), Spring.constant(10)));
        refreshBtnCons.setY(Spring.constant(10));

        SpringLayout.Constraints txtCons = layout.getConstraints(textArea);
        txtCons.setX(Spring.constant(10));
        txtCons.setY(Spring.sum(openBtnCons.getConstraint(SpringLayout.BASELINE), Spring.constant(20)));

        jf.setContentPane(panel);
        jf.setVisible(true);
    }

}
