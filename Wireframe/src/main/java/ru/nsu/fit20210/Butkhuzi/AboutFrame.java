package ru.nsu.fit20210.Butkhuzi;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AboutFrame extends JFrame {
    private String text;
    private MainFrame mainFrame;

    public AboutFrame(MainFrame mainFrame) {
        try {
            setMainFrame(mainFrame);
            Path path = Paths.get("src/main/resources/about.txt");
            setText(Files.readString(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        onAbout();
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(mainFrame, this.getText(), "About program", JOptionPane.INFORMATION_MESSAGE);
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
