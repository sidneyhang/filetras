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
        Thread udpClient = new Thread(new BroadcastIPTask());
        udpServer.start();
        udpClient.start();

        JFrame jf = new JFrame("测试窗口");
        jf.setSize(500, 500);
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

        SpringLayout.Constraints openBtnCons = layout.getConstraints(openBtn);



        jf.setContentPane(panel);
        jf.setVisible(true);
    }

}
