package com.vino.convert_pdf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WordToPDFConverterGUI extends JFrame {
    private JTextField outputPathField;
    private JButton selectInputButton;
    private JButton selectOutputButton;
    private JButton convertButton;
    private JFileChooser fileChooser;
    private File[] selectedFiles;

    public WordToPDFConverterGUI() {
        setTitle("Word to PDF Converter");
        setSize(550, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Set the background color to white
        getContentPane().setBackground(Color.WHITE);

        // Prevent resizing
        setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize components
        outputPathField = new JTextField(20);
        selectInputButton = new JButton("Select Word Files");
        selectOutputButton = new JButton("Select Save Directory");
        convertButton = new JButton("Convert");

        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        // Style buttons
        selectInputButton.setBackground(Color.LIGHT_GRAY);
        selectInputButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        selectInputButton.setPreferredSize(new Dimension(120, 30)); // Adjust width and height

        selectOutputButton.setBackground(Color.LIGHT_GRAY);
        selectOutputButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        selectOutputButton.setPreferredSize(new Dimension(120, 30)); // Adjust width and height

        convertButton.setBackground(new Color(34, 139, 34)); // Green color
        convertButton.setForeground(Color.WHITE);
        convertButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        convertButton.setPreferredSize(new Dimension(120, 30)); // Adjust width and height

        // Add components with constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(new JLabel("Save Directory:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(outputPathField, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(selectOutputButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(selectInputButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(convertButton, gbc);

        // Action Listeners
        selectInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFiles = fileChooser.getSelectedFiles();
                }
            }
        });

        selectOutputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    outputPathField.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String outputPath = outputPathField.getText();
                if (selectedFiles != null && selectedFiles.length > 0 && !outputPath.isEmpty()) {
                    for (File file : selectedFiles) {
                        try {
                            String inputPath = file.getPath();
                            String outputFilePath = outputPath + File.separator + getFileNameWithoutExtension(file) + ".pdf";
                            WordToPDFConverter.convert(inputPath, outputFilePath);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "An error occurred during conversion.");
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Conversion completed successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select input files and output directory.");
                }
            }
        });
    }

    private String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordToPDFConverterGUI().setVisible(true);
            }
        });
    }
}
