package com.venafan;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author sidney
 * @version V1.0
 * @date Created in 2020/4/10 10:37
 */
public class MyFileChooser {
    private JFileChooser fileChooser;

    private FileData fileData;

    public MyFileChooser() {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(false);
    }

    public void showFileOpenDialog(Component parent, JTextArea textArea) {
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            textArea.append("打开文件：" + file + "\n\n");
            FileData data = new FileData();
            data.setFileName(file.getName());
            try {
                InputStream inputStream = new FileInputStream(file);
                data.setData(inputStream.readAllBytes());
                fileData = data;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FileData getFileData() {
        return fileData;
    }
}
