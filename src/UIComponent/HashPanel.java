package UIComponent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

public class HashPanel extends JPanel {
	private JComboBox<String> algorithmBox;
	private JTextArea inputArea;
	private JTextArea outputArea;
	private JButton hashBtn;

	private JButton chooseFileBtn;
	private JLabel selectedFileLabel;
	private JButton hashFileBtn;
	public HashPanel() {
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

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

		hashBtn = new JButton("Hash Text");
		hashFileBtn = new JButton("Hash File");

		panel.add(hashBtn);
		panel.add(new JLabel(" | "));
		panel.add(hashFileBtn);
		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

		inputArea = new JTextArea(5, 20);
		outputArea = new JTextArea(5, 20);
		outputArea.setEditable(false);

		panel.add(createTextPanel("Input", inputArea));
		panel.add(createTextPanel("Hash Output", outputArea));
		return panel;
	}

	private JPanel createTextPanel(String title, JTextArea area) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.add(new JScrollPane(area), BorderLayout.CENTER);
		return panel;
	}

	private JPanel createTopPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
		panel.setBorder(BorderFactory.createTitledBorder("Hàm băm"));

		panel.add(new JLabel("Thuật toán:"));
		algorithmBox = new JComboBox<>(new String[]{"SHA-256", "SHA-1", "SHA-512", "SHA-384", "SHA3-256", "MD5", "MD4", "CRC-32"});
		panel.add(algorithmBox);

		chooseFileBtn = new JButton("Chọn File...");
		selectedFileLabel = new JLabel("Chưa chọn file nào.");
		panel.add(chooseFileBtn);
		panel.add(selectedFileLabel);
		return panel;
	}
	public void setSelectedAlgorithm(String algorithm) {
		algorithmBox.setSelectedItem(algorithm);
	}

	public String getSelectedAlgorithm() {
		return (String) algorithmBox.getSelectedItem();
	}

	public String getInputText() {
		return inputArea.getText();
	}

	public void setOutputText(String text) {
		outputArea.setText(text);
	}

	public void addHashListener(ActionListener listener) {
		hashBtn.addActionListener(listener);
	}

	public void setSelectedFile(String path) {
		selectedFileLabel.setText(path);
	}

	public void addChooseFileListener(ActionListener listener) {
		chooseFileBtn.addActionListener(listener);
	}

	public void addHashFileListener(ActionListener listener) {
		hashFileBtn.addActionListener(listener);
	}

}
