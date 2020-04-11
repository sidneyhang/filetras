package com.venafan;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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

    private File file;

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
            this.file = file;
        }
    }

    public File getFile() {
        return file;
    }
}
