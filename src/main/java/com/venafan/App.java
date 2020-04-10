package com.venafan;

import javax.swing.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(250,250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JButton button = new JButton("测试按钮");
        panel.add(button);

        jf.setContentPane(panel);
        jf.setVisible(true);
    }
}
