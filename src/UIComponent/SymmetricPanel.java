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
	        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
	        panel.setBorder(BorderFactory.createTitledBorder("Mã hóa đối xứng"));

	        panel.add(new JLabel("Thuật toán:"));
	        algorithmBox = new JComboBox<>(new String[]{"AES", "DES","Blowfish","TripleDES"});
	        panel.add(algorithmBox);

	        panel.add(new JLabel("Key:"));
	        keyField = new JTextField();
	        panel.add(keyField);

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

	        encryptBtn = new JButton("Encrypt");
	        decryptBtn = new JButton("Decrypt");

	        panel.add(encryptBtn);
	        panel.add(decryptBtn);

	        return panel;
	    }

	    public void setSelectedAlgorithm(String algorithm) {
	        algorithmBox.setSelectedItem(algorithm);
	    }

}
