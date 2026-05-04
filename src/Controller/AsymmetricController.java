package Controller;

import Model.AsymmetricModel;
import UIComponent.ASymmetricPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;

public class AsymmetricController {
    private ASymmetricPanel view;
    private AsymmetricModel model;

    public AsymmetricController(ASymmetricPanel view, AsymmetricModel model ) {
        this.view = view;
        this.model = model;

        this.view.addGenerateKeyListener(new GenerateKeyListener());
        this.view.addEncryptListener(new EncryptListener());
        this.view.addDecryptListener(new DecryptListener());
        this.view.addChooseFileListener(new ChooseFileListener());
        this.view.addEncryptFileListener(new EncryptFileListener());
        this.view.addDecryptFileListener(new DecryptFileListener());
    }
    class GenerateKeyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int keySize = view.getKeySize();
                KeyPair keyPair = model.generateRSAKeyPair(keySize);
                String pubKey = model.getPublicKeyString(keyPair.getPublic());
                String privKey = model.getPrivateKeyString(keyPair.getPrivate());

                view.setPublicKey(pubKey);
                view.setPrivateKey(privKey);
                JOptionPane.showMessageDialog(view, "Tạo cặp khóa RSA thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi tạo khóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class EncryptListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = view.getInputText();
            String pubKey = view.getPublicKey();

            if (input.isEmpty() || pubKey.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập văn bản và Khóa công khai (Public Key)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String result = model.encryptRSA(input, pubKey);
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
            String privKey = view.getPrivateKey();

            if (input.isEmpty() || privKey.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập văn bản mã hóa và Khóa riêng tư (Private Key)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String result = model.decryptRSA(input, privKey);
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
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            int option = fileChooser.showOpenDialog(view);
            if (option == javax.swing.JFileChooser.APPROVE_OPTION) {
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
            String pubKey = view.getPublicKey();
            if (pubKey.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập Khóa công khai (Public Key)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Lưu file đã mã hóa");
            java.io.File suggestedFile = new java.io.File(selectedFile.getAbsolutePath() + ".enc");
            fileChooser.setSelectedFile(suggestedFile);
            int option = fileChooser.showSaveDialog(view);
            if (option == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File outputFile = fileChooser.getSelectedFile();
                try {
                    model.encryptFileRSA(selectedFile, outputFile, pubKey);
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
            String privKey = view.getPrivateKey();
            if (privKey.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập Khóa riêng tư (Private Key)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Lưu file đã giải mã");
            String originalPath = selectedFile.getAbsolutePath();
            java.io.File suggestedFile;
            if (originalPath.endsWith(".enc")) {
                suggestedFile = new java.io.File(originalPath.substring(0, originalPath.length() - 4));
            } else {
                suggestedFile = new java.io.File(originalPath + ".dec");
            }
            fileChooser.setSelectedFile(suggestedFile);
            int option = fileChooser.showSaveDialog(view);
            if (option == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File outputFile = fileChooser.getSelectedFile();
                try {
                    model.decryptFileRSA(selectedFile, outputFile, privKey);
                    JOptionPane.showMessageDialog(view, "Giải mã file thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi giải mã file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
