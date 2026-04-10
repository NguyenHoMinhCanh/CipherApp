package UIComponent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SymmetricPanel extends JPanel {
	
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

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());		
		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		return panel;
	}

	private JPanel createTopPanel() {
		JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Khóa RSA"));
		return panel;
	}

}
