package UIComponent;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

		styleButton(hashBtn, new Color(212, 245, 221), new Color(39, 174, 96));
		styleButton(hashFileBtn, new Color(252, 243, 207), new Color(243, 156, 18));

		panel.add(hashBtn);
		panel.add(new JLabel(" | "));
		panel.add(hashFileBtn);
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
		algorithmBox = new JComboBox<>(new String[]{"SHA-256", "SHA-1", "SHA-512", "SHA-384", "SHA3-256", "MD5", "CRC-32"});
		panel.add(algorithmBox);

		chooseFileBtn = new JButton("Chọn File...");
		selectedFileLabel = new JLabel("Chưa chọn file nào.");
		panel.add(chooseFileBtn);
		panel.add(selectedFileLabel);
		return panel;
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
