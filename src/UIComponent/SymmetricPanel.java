package UIComponent;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class SymmetricPanel extends JPanel {

    private JComboBox<String> algorithmBox;
    private JTextField keyField;
    private JButton generateKeyBtn;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton encryptBtn;
    private JButton decryptBtn;

    private JButton chooseFileBtn;
    private JLabel selectedFileLabel;
    private JButton encryptFileBtn;
    private JButton decryptFileBtn;
    private JLabel separatorLabel;

    public SymmetricPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(createWorkPanel(), BorderLayout.CENTER);
        setupAlgorithmListener();
    }

    private void setupAlgorithmListener() {
        algorithmBox.addActionListener(e -> updateFileUIVisibility());
        updateFileUIVisibility();
    }

    private void updateFileUIVisibility() {
        String algo = (String) algorithmBox.getSelectedItem();
        boolean isFileSupported = !("Hill".equals(algo) || "Vigenere".equals(algo));

        chooseFileBtn.setVisible(isFileSupported);
        selectedFileLabel.setVisible(isFileSupported);
        encryptFileBtn.setVisible(isFileSupported);
        decryptFileBtn.setVisible(isFileSupported);
        if (separatorLabel != null) {
            separatorLabel.setVisible(isFileSupported);
        }
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
        JPanel keyPanel = new JPanel(new BorderLayout(5, 0));
        keyField = new JTextField();
        generateKeyBtn = new JButton("Tạo Key");
        keyPanel.add(keyField, BorderLayout.CENTER);
        keyPanel.add(generateKeyBtn, BorderLayout.EAST);
        panel.add(keyPanel);

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
        separatorLabel = new JLabel(" | ");

        styleButton(encryptBtn, new Color(212, 245, 221), new Color(39, 174, 96));
        styleButton(decryptBtn, new Color(214, 234, 248), new Color(41, 128, 185));
        styleButton(encryptFileBtn, new Color(252, 243, 207), new Color(243, 156, 18));
        styleButton(decryptFileBtn, new Color(245, 203, 167), new Color(192, 57, 43));

        panel.add(encryptBtn);
        panel.add(decryptBtn);
        panel.add(separatorLabel);
        panel.add(encryptFileBtn);
        panel.add(decryptFileBtn);

        return panel;
    }

    private void styleButton(JButton button, Color normalColor, Color hoverColor) {
        button.setBackground(normalColor);
        button.setForeground(Color.BLACK);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setFont(new Font("Arial", Font.PLAIN, 11));
        button.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        button.setMargin(new Insets(5, 10, 5, 10));
        button.setBorder(BorderFactory.createCompoundBorder(button.getBorder(), BorderFactory.createEmptyBorder(4,8,4,8)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }

    public void addAlgorithmChangeListener(ActionListener listener) {
        algorithmBox.addActionListener(listener);
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

    public void setKey(String key) {
        keyField.setText(key);
    }

    public void addGenerateKeyListener(ActionListener listener) {
        generateKeyBtn.addActionListener(listener);
    }

    public String getInputText() {
        return inputArea.getText();
    }

    public void setOutputText(String text) {
        outputArea.setText(text);
    }

    public void addEncryptListener(ActionListener listener) {
        encryptBtn.addActionListener(listener);
    }

    public void addDecryptListener(ActionListener listener) {
        decryptBtn.addActionListener(listener);
    }

    public void setSelectedFile(String path) {
        selectedFileLabel.setText(path);
    }

    public void addChooseFileListener(ActionListener listener) {
        chooseFileBtn.addActionListener(listener);
    }

    public void addEncryptFileListener(ActionListener listener) {
        encryptFileBtn.addActionListener(listener);
    }

    public void addDecryptFileListener(ActionListener listener) {
        decryptFileBtn.addActionListener(listener);
    }
}
