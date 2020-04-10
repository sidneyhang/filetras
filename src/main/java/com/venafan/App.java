package com.venafan;

import javax.swing.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ServerListen serverListen = new ServerListen();
        Thread thread = new Thread(serverListen);
        thread.start();

        JFrame jf = new JFrame("测试窗口");
        jf.setSize(500,500);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

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
            fileChooser.getFileData();
        });
        panel.add(sendBtn);


        jf.setContentPane(panel);
        jf.setVisible(true);
    }

}
