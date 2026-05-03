package UIComponent;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class SymmetricPanel extends JPanel {

    private JComboBox<String> algorithmBox;
    private JTextField keyField;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton encryptBtn;
    private JButton decryptBtn;

    private JButton chooseFileBtn;
    private JLabel selectedFileLabel;
    private JButton encryptFileBtn;
    private JButton decryptFileBtn;

    public SymmetricPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(createWorkPanel(), BorderLayout.CENTER);
    }

    private JPanel createWorkPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.add(createTopPanel(), BorderLayout.NORTH);
        panel.add(createCenterPanel(), BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Mã hóa đối xứng"));

        panel.add(new JLabel("Thuật toán:"));
        algorithmBox = new JComboBox<>(new String[]{"AES", "DES", "Blowfish", "TripleDES", "CAST6", "Twofish", "Hill", "Vigenere", "DESede"});
        panel.add(algorithmBox);

        panel.add(new JLabel("Key:"));
        keyField = new JTextField();
        panel.add(keyField);

        chooseFileBtn = new JButton("Chọn File");
        selectedFileLabel = new JLabel("Chưa chọn file nào.");
        panel.add(chooseFileBtn);
        panel.add(selectedFileLabel);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

        inputArea = new JTextArea(5, 20);
        outputArea = new JTextArea(5, 20);

        panel.add(createTextPanel("Input", inputArea));
        panel.add(createTextPanel("Output", outputArea));

        return panel;
    }

    private JPanel createTextPanel(String title, JTextArea area) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        encryptBtn = new JButton("Encrypt Text");
        decryptBtn = new JButton("Decrypt Text");
        encryptFileBtn = new JButton("Encrypt File");
        decryptFileBtn = new JButton("Decrypt File");

        panel.add(encryptBtn);
        panel.add(decryptBtn);
        panel.add(new JLabel(" | "));
        panel.add(encryptFileBtn);
        panel.add(decryptFileBtn);

        return panel;
    }

    public void setSelectedAlgorithm(String algorithm) {
        algorithmBox.setSelectedItem(algorithm);
    }

    public String getSelectedAlgorithm() {
        return (String) algorithmBox.getSelectedItem();
    }

    public String getKey() {
        return keyField.getText();
    }

    public String getInputText() {
        return inputArea.getText();
    }

    public void setOutputText(String text) {
        outputArea.setText(text);
    }

    public void addEncryptListener(java.awt.event.ActionListener listener) {
        encryptBtn.addActionListener(listener);
    }

    public void addDecryptListener(java.awt.event.ActionListener listener) {
        decryptBtn.addActionListener(listener);
    }

    public void setSelectedFile(String path) {
        selectedFileLabel.setText(path);
    }

    public void addChooseFileListener(java.awt.event.ActionListener listener) {
        chooseFileBtn.addActionListener(listener);
    }

    public void addEncryptFileListener(java.awt.event.ActionListener listener) {
        encryptFileBtn.addActionListener(listener);
    }

    public void addDecryptFileListener(java.awt.event.ActionListener listener) {
        decryptFileBtn.addActionListener(listener);
    }
}
