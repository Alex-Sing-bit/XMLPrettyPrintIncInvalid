package ru.vsu.cs.baklanova;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
                    tree.fromStringXML(textPaneInputFromFile.getText());
                    arr1 = tree.xmlTreeToStrings();
                    textPaneSaveToFile.setText("");
                    for (String s : arr1) {
                        textPaneSaveToFile.setText(textPaneSaveToFile.getText() + s);
                    }
                } catch (Exception e) {
                    //SwingUtils.showErrorMessageBox(e);
                }
            }
        });
    }
}
