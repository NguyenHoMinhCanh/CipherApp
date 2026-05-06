package UIComponent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ASymmetricPanel extends JPanel {
	private JComboBox<String> algorithmBox;
	private JComboBox<String> keySizeBox;
	private JTextArea publicKeyArea;
	private JTextArea privateKeyArea;
	private JTextArea inputArea;
	private JTextArea outputArea;

	private JButton generateKeyBtn;
	private JButton encryptBtn;
	private JButton decryptBtn;

	private JButton chooseFileBtn;
	private JLabel selectedFileLabel;
	private JButton encryptFileBtn;
	private JButton decryptFileBtn;
	public ASymmetricPanel() {
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
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createTitledBorder("Mã hóa bất đối xứng (RSA)"));

		JPanel algoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		algoPanel.add(new JLabel("Thuật toán:"));
		algorithmBox = new JComboBox<>(new String[]{"RSA (PKCS1Padding)"});
		algoPanel.add(algorithmBox);

		algoPanel.add(new JLabel("Key Size:"));
		keySizeBox = new JComboBox<>(new String[]{"1024", "2048", "3072", "4096"});
		keySizeBox.setSelectedItem("2048"); // default
		algoPanel.add(keySizeBox);

		generateKeyBtn = new JButton("Generate Key");
		algoPanel.add(generateKeyBtn);

		JPanel keysPanel = new JPanel(new GridLayout(2, 1, 5, 5));

		publicKeyArea = new JTextArea(3, 20);
		publicKeyArea.setLineWrap(true);
		JPanel pubPanel = new JPanel(new BorderLayout());
		pubPanel.add(new JLabel("Public Key (Base64):"), BorderLayout.NORTH);
		pubPanel.add(new JScrollPane(publicKeyArea), BorderLayout.CENTER);

		privateKeyArea = new JTextArea(3, 20);
		privateKeyArea.setLineWrap(true);
		JPanel privPanel = new JPanel(new BorderLayout());
		privPanel.add(new JLabel("Private Key (Base64):"), BorderLayout.NORTH);
		privPanel.add(new JScrollPane(privateKeyArea), BorderLayout.CENTER);

		keysPanel.add(pubPanel);
		keysPanel.add(privPanel);

		JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		chooseFileBtn = new JButton("Chọn File...");
		selectedFileLabel = new JLabel("Chưa chọn file nào.");
		filePanel.add(chooseFileBtn);
		filePanel.add(selectedFileLabel);

		panel.add(algoPanel, BorderLayout.NORTH);
		panel.add(keysPanel, BorderLayout.CENTER);
		panel.add(filePanel, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

		inputArea = new JTextArea(5, 20);
		outputArea = new JTextArea(5, 20);
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);

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

	public int getKeySize() {
		return Integer.parseInt((String) keySizeBox.getSelectedItem());
	}

	public String getPublicKey() {
		return publicKeyArea.getText();
	}

	public void setPublicKey(String key) {
		publicKeyArea.setText(key);
	}

	public String getPrivateKey() {
		return privateKeyArea.getText();
	}

	public void setPrivateKey(String key) {
		privateKeyArea.setText(key);
	}

	public String getInputText() {
		return inputArea.getText();
	}

	public void setOutputText(String text) {
		outputArea.setText(text);
	}

	public void addGenerateKeyListener(ActionListener listener) {
		generateKeyBtn.addActionListener(listener);
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
