package ru.vsu.cs.baklanova;

import javax.swing.*;
import java.awt.*;

public class FrameMain extends JFrame{
    private JFrame Form;
    private JPanel mainPanel;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    public FrameMain() {
        this.setTitle("FrameMain");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();


    }
}
