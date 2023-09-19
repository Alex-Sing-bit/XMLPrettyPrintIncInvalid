package ru.vsu.cs.baklanova;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FrameMain extends JFrame{
    private JFrame Form;
    private JPanel panelMain;
    private JTextArea textAreaInput;
    private JTextArea textAreaOutput;
    private JButton buttonSaveOutputIntoFile;
    private JButton button3;
    private JButton buttonPrettyPrint;
    private JButton buttonLoadInputFromFile;

    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;

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
                        for (String s : arr) {
                            textAreaInput.setText(textAreaInput.getText() + '\n' + s);
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
                        //int[][] matrix = JTableUtils.readIntMatrixFromJTable(tableOutput);
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        //ArrayUtils.writeArrayToFile(file, matrix);
                    }
                } catch (Exception e) {
                    //SwingUtils.showErrorMessageBox(e);
                }
            }
        });
    }
}
