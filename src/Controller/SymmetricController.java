package Controller;


import Model.SymmetricModel;
import UIComponent.SymmetricPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SymmetricController {
    private SymmetricPanel view;
    private SymmetricModel model;

    public SymmetricController(SymmetricPanel view, SymmetricModel model ) {
        this.view = view;
        this.model = model;

        this.view.addEncryptListener(new EncryptListener());
        this.view.addDecryptListener(new DecryptListener());
        this.view.addChooseFileListener(new ChooseFileListener());
        this.view.addEncryptFileListener(new EncryptFileListener());
        this.view.addDecryptFileListener(new DecryptFileListener());
        this.view.addGenerateKeyListener(new GenerateKeyListener());
    }

    class GenerateKeyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String algo = view.getSelectedAlgorithm();
            try {
                String generatedKey = model.generateKey(algo);
                view.setKey(generatedKey);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi tạo khóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class EncryptListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = view.getInputText();
            String key = view.getKey();
            String algo = view.getSelectedAlgorithm();

            if (input.isEmpty() || key.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập văn bản và khóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String result = model.encrypt(input, key, algo);
                view.setOutputText(result);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi mã hóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DecryptListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = view.getInputText();
            String key = view.getKey();
            String algo = view.getSelectedAlgorithm();

            if (input.isEmpty() || key.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập văn bản mã hóa và khóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String result = model.decrypt(input, key, algo);
                view.setOutputText(result);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi giải mã: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private java.io.File selectedFile = null;

    class ChooseFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(view);
            if (option == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                view.setSelectedFile(selectedFile.getAbsolutePath());
            }
        }
    }

    class EncryptFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedFile == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn file trước!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String key = view.getKey();
            String algo = view.getSelectedAlgorithm();
            if (key.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập khóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu file đã mã hóa");
            int option = fileChooser.showSaveDialog(view);
            if (option == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();
                try {
                    model.encryptFile(selectedFile, outputFile, key, algo);
                    JOptionPane.showMessageDialog(view, "Mã hóa file thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi mã hóa file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class DecryptFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedFile == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn file trước!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String key = view.getKey();
            String algo = view.getSelectedAlgorithm();
            if (key.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập khóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu file đã giải mã");
            int option = fileChooser.showSaveDialog(view);
            if (option == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();
                try {
                    model.decryptFile(selectedFile, outputFile, key, algo);
                    JOptionPane.showMessageDialog(view, "Giải mã file thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi giải mã file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
