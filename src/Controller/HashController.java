package Controller;

import Model.HashModel;
import UIComponent.HashPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HashController {
    private HashPanel view;
    private HashModel model;

    public HashController(HashPanel view, HashModel model ) {
        this.view = view;
        this.model = model;

        this.view.addHashListener(new HashListener());
        this.view.addChooseFileListener(new ChooseFileListener());
        this.view.addHashFileListener(new HashFileListener());
    }

    class HashListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = view.getInputText();
            String algo = view.getSelectedAlgorithm();

            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập văn bản cần băm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String result = model.hash(input, algo);
                view.setOutputText(result);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi băm dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private File selectFile = null;

    class ChooseFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(view);
            if (option == JFileChooser.APPROVE_OPTION) {
                selectFile = fileChooser.getSelectedFile();
                view.setSelectedFile(selectFile.getAbsolutePath());
            }
        }
    }

    class HashFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectFile == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn file trước!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String algo = view.getSelectedAlgorithm();

            try {
                String result = model.hashFile(selectFile, algo);
                view.setOutputText(result);
                JOptionPane.showMessageDialog(view, "Băm file thành công!", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex)  {
                JOptionPane.showMessageDialog(view, "Lỗi bm file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
