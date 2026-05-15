package UIHome;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import Controller.AsymmetricController;
import Controller.HashController;
import Controller.SymmetricController;
import Model.AsymmetricModel;
import Model.HashModel;
import Model.SymmetricModel;
import UIComponent.ASymmetricPanel;
import UIComponent.HashPanel;
import UIComponent.SymmetricPanel;

public class Home extends JFrame {
	private SymmetricPanel symmetricPanel;
	private ASymmetricPanel aSymmetricPanel;
	private HashPanel hashPanel;
	private JPanel rightPanel;
	private CardLayout cardLayout;
	
    public Home() {
        setTitle("CipherApp");
        setSize(800,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        // create panel 
        symmetricPanel = new SymmetricPanel();
        SymmetricModel symModel = new SymmetricModel();
        new SymmetricController(symmetricPanel, symModel);

        aSymmetricPanel = new ASymmetricPanel();
        AsymmetricModel asymModel = new AsymmetricModel();
        new AsymmetricController(aSymmetricPanel, asymModel);

        hashPanel = new HashPanel();
        HashModel hashModel = new HashModel();
        new HashController(hashPanel, hashModel);
        
        // create right panel contains component panel with cardlayout
        rightPanel = new JPanel();
        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);
        rightPanel.add(symmetricPanel, "symmetric");
        rightPanel.add(aSymmetricPanel, "asymmetric");
        rightPanel.add(hashPanel, "hash");

        // create left panel
        JPanel leftJPanel = createLeftPanel();

        // split pane chia UI 
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftJPanel, rightPanel);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.2);
        
        add(splitPane);
        
        
    }


    private JPanel createLeftPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		DefaultMutableTreeNode symmetric = new DefaultMutableTreeNode("Mã hóa đối xứng");
		symmetric.add(new DefaultMutableTreeNode("AES"));
		symmetric.add(new DefaultMutableTreeNode("DES"));
		symmetric.add(new DefaultMutableTreeNode("Blowfish"));
		symmetric.add(new DefaultMutableTreeNode("TripleDES"));
        symmetric.add(new DefaultMutableTreeNode("CAST6"));
        symmetric.add(new DefaultMutableTreeNode("Twofish"));
        symmetric.add(new DefaultMutableTreeNode("Hill"));
        symmetric.add(new DefaultMutableTreeNode("Vigenere"));
        symmetric.add(new DefaultMutableTreeNode("DESede"));
		
		DefaultMutableTreeNode asymmetric = new DefaultMutableTreeNode("Mã hóa bất đối xứng");
		asymmetric.add(new DefaultMutableTreeNode("RSA (PKCS1Padding)"));

		
		DefaultMutableTreeNode hash = new DefaultMutableTreeNode("Hàm băm");
        hash.add(new DefaultMutableTreeNode("SHA-256"));
        hash.add(new DefaultMutableTreeNode("SHA-1"));
        hash.add(new DefaultMutableTreeNode("SHA-512"));
        hash.add(new DefaultMutableTreeNode("SHA-384"));
        hash.add(new DefaultMutableTreeNode("SHA3-256"));
        hash.add(new DefaultMutableTreeNode("MD5"));
        hash.add(new DefaultMutableTreeNode("CRC-32"));
        
        root.add(symmetric);
        root.add(asymmetric);
        root.add(hash);

        JTree algorithmTree = new JTree(root);
        algorithmTree.setRootVisible(false);
        algorithmTree.setShowsRootHandles(true);
        
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) algorithmTree.getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
        renderer.setBackgroundSelectionColor(new Color(52, 152, 219));
        renderer.setTextSelectionColor(Color.WHITE);
        renderer.setBackgroundNonSelectionColor(new Color(245, 247, 250));
        renderer.setTextNonSelectionColor(Color.BLACK);
        renderer.setBorderSelectionColor(null);
        
        algorithmTree.addTreeSelectionListener(e -> {
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode) algorithmTree.getLastSelectedPathComponent();
        	if (node == null) {
        		return;
        	}
        	String selected = node.getUserObject().toString();
        	DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
            String parent = parentNode != null ? parentNode.getUserObject().toString() : "";

            if ("Mã hóa đối xứng".equals(selected) || "Mã hóa đối xứng".equals(parent)) {
                cardLayout.show(rightPanel, "symmetric");
                if ("Mã hóa đối xứng".equals(parent)) {
                    symmetricPanel.setSelectedAlgorithm(selected);
                }
                return;
            }

            if ("Mã hóa bất đối xứng".equals(selected) || "Mã hóa bất đối xứng".equals(parent)) {
                cardLayout.show(rightPanel, "asymmetric");
                if ("Mã hóa bất đối xứng".equals(parent)) {
                    aSymmetricPanel.setSelectedAlgorithm(selected);
                }
                return;
            }

            if ("Hàm băm".equals(selected) || "Hàm băm".equals(parent)) {
            	cardLayout.show(rightPanel, "hash");
            	if ("Hàm băm".equals(parent)) {
            		hashPanel.setSelectedAlgorithm(selected);
            	}
            	
            }
        	
        });
        algorithmTree.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                TreePath path = algorithmTree.getPathForLocation(e.getX(), e.getY());

                if (path != null) {
                    algorithmTree.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    algorithmTree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        algorithmTree.setSelectionPath(new TreePath(symmetric.getPath()));
        algorithmTree.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        algorithmTree.setRowHeight(20);
        algorithmTree.setBackground(new Color(245, 247, 250));
        algorithmTree.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(algorithmTree);

        symmetricPanel.addAlgorithmChangeListener(e -> {
            String selectedAlgo = symmetricPanel.getSelectedAlgorithm();

            selectNode(algorithmTree, selectedAlgo);
        });

        aSymmetricPanel.addAlgorithmChangeListener(e -> {
            String selectedAlgo = aSymmetricPanel.getSelectedAlgorithm();

            selectNode(algorithmTree, selectedAlgo);
        });

        hashPanel.addAlgorithmChangeListener(e -> {
            String selectedAlgo = hashPanel.getSelectedAlgorithm();

            selectNode(algorithmTree, selectedAlgo);
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBackground(new Color(240, 242, 245));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,
                new Color(220, 220, 220)
        ));

		return panel;
	}

    private void selectNode(JTree tree, String nodeName) {

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();

        Enumeration<?> enumeration = root.breadthFirstEnumeration();

        while (enumeration.hasMoreElements()) {

            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode) enumeration.nextElement();

            if (nodeName.equals(node.getUserObject().toString())) {

                TreePath path = new TreePath(node.getPath());

                tree.setSelectionPath(path);
                tree.scrollPathToVisible(path);

                break;
            }
        }
    }


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            Home app = new Home();
            app.setVisible(true);
        });
    }
}
