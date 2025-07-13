package com.ittia.gds.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * Simplified GDS EMR interface focusing on central text area panel.
 */
public class GDSEMRFrame {
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;
    private static final String[] TEXT_AREA_TITLES = {
            "CC>", "PI>", "ROS>", "PMH>", "S>",
            "O>", "Physical Exam>", "A>", "P>", "Comment>"
    };
    private JFrame frame;
    private static JTextArea[] textAreas;
    private JTextArea outputArea;

    /**
     * Initializes the EMR frame.
     */
    public GDSEMRFrame() {
        frame = new JFrame("Simplified GDS EMR Interface");
        textAreas = new JTextArea[TEXT_AREA_TITLES.length];
        outputArea = new JTextArea();
    }

    /**
     * Creates and displays the GUI.
     */
    public void createAndShowGUI() {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createCenterPanel(), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * Creates the center panel with text areas in a grid.
     * @return JPanel The center panel.
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new JTextArea(TEXT_AREA_TITLES[i] + "\t");
            setupTextArea(textAreas[i], i);
            JScrollPane scrollPane = new JScrollPane(textAreas[i]);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            centerPanel.add(scrollPane);
        }
        return centerPanel;
    }

    /**
     * Sets up properties and listeners for a text area.
     * @param textArea The JTextArea to configure.
     * @param index The index of the text area.
     */
    private void setupTextArea(JTextArea textArea, int index) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.getDocument().addDocumentListener(new SimpleDocumentListener());
        textArea.addMouseListener(new DoubleClickMouseListener());
        textArea.addKeyListener(new FunctionKeyListener());
    }

    /**
     * Updates a specific text area with text.
     * @param index The index of the text area.
     * @param text The text to append.
     */
    public static void setTextAreaText(int index, String text) {
        if (textAreas == null || index < 0 || index >= textAreas.length || textAreas[index] == null) {
            System.err.println("Invalid text area index or text areas not initialized.");
            return;
        }
        textAreas[index].append(text);
    }

    /**
     * Updates the output area with text.
     * @param text The text to set.
     */
    public void updateOutputArea(String text) {
        if (outputArea != null) {
            outputArea.setText(text);
        }
    }

    /**
     * Document listener to update output area on text changes.
     */
    private class SimpleDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateOutput();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateOutput();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateOutput();
        }

        private void updateOutput() {
            StringBuilder allText = new StringBuilder();
            for (JTextArea textArea : textAreas) {
                allText.append(textArea.getText()).append("\n");
            }
            updateOutputArea(allText.toString());
        }
    }

    /**
     * Handles function key presses.
     */
    private class FunctionKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F12) {
                String message = "F" + (keyCode - KeyEvent.VK_F1 + 1) + " key pressed.";
                updateOutputArea(message);
            }
        }
    }

    /**
     * Handles double-click events on text areas.
     */
    private class DoubleClickMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                JTextArea source = (JTextArea) e.getSource();
                updateOutputArea("Double-clicked: " + source.getText());
            }
        }
    }

    /**
     * Launches the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GDSEMRFrame emrFrame = new GDSEMRFrame();
            emrFrame.createAndShowGUI();
        });
    }
}