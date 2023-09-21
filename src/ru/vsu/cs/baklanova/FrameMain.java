package ru.vsu.cs.baklanova;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

public class FrameMain extends JFrame{
    private JFrame Form;
    private JPanel panelMain;
    private JButton buttonSaveOutputIntoFile;
    private JButton button3;
    private JButton buttonPrettyPrint;
    private JButton buttonLoadInputFromFile;
    private JTextPane textPaneInputFromFile;
    private JTextPane textPaneSaveToFile;

    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;

    private ArrayList<String> arr1;

    public FrameMain() {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setSize(1000, 800);
        this.pack();

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("XML files", "xml");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        buttonLoadInputFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        String[] arr = Program.readLinesFromFile(fileChooserOpen.getSelectedFile().getPath());
                        textPaneInputFromFile.setText("");
                        for (String s : arr) {
                            textPaneInputFromFile.setText(textPaneInputFromFile.getText() + '\n' + s);
                        }
                        int n = 999;
                    }
                } catch (Exception e) {
                    //SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        buttonSaveOutputIntoFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        PrintStream out = new PrintStream(file);
                        //for (String s : arr1) {
                        out.println(textPaneSaveToFile.getText());
                        //}
                        //out.close();
                    }
                } catch (Exception e) {
                    //SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        buttonPrettyPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    XMLTree<String> tree = new XMLTree<>();
                    tree.fromStringXML(textPaneInputFromFile.getText().replace('\n', ' '));
                    arr1 = tree.xmlTreeToStrings();
                    textPaneSaveToFile.setText("");

                    for (String s : arr1) {
                        //createTXT(s);
                        textPaneSaveToFile.setText(textPaneSaveToFile.getText() + s);
                    }
                } catch (Exception e) {
                    //SwingUtils.showErrorMessageBox(e);
                }
            }
        });
    }

    private void createTXT(String s) {
        for (char c : s.toCharArray()) {
            if (c == 'C') {
                Style style = textPaneSaveToFile.addStyle("I'm a Style", null);
                StyleConstants.setForeground(style, new Color(250, 0,0));
                int from = textPaneSaveToFile.getSelectionStart();
                int to = textPaneSaveToFile.getSelectionEnd();
                textPaneSaveToFile.getStyledDocument().setCharacterAttributes(4, 4, style, true);
            } else {
                textPaneSaveToFile.setForeground(new Color(0, 0, 250));
            }
            //textPaneSaveToFile.setForeground(new Color(0, 250, 0));
            textPaneSaveToFile.setText(textPaneSaveToFile.getText() + c);
        }
    }
}
