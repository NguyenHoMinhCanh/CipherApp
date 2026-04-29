package UIHome;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

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
import UIComponent.SignaturePanel;
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
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root"); // 
		DefaultMutableTreeNode symmetric = new DefaultMutableTreeNode("Ma hoa doi xung");
		symmetric.add(new DefaultMutableTreeNode("AES"));
		symmetric.add(new DefaultMutableTreeNode("DES"));
		symmetric.add(new DefaultMutableTreeNode("Blowfish"));
		symmetric.add(new DefaultMutableTreeNode("TripleDES"));
		
		DefaultMutableTreeNode asymmetric = new DefaultMutableTreeNode("Ma hoa bat doi xung");
		asymmetric.add(new DefaultMutableTreeNode("RSA (PKCS1Padding)"));

		
		DefaultMutableTreeNode hash = new DefaultMutableTreeNode("Ham bam");
        hash.add(new DefaultMutableTreeNode("SHA-256"));
        hash.add(new DefaultMutableTreeNode("SHA-1"));
        hash.add(new DefaultMutableTreeNode("SHA-512"));
        hash.add(new DefaultMutableTreeNode("SHA-384"));
        hash.add(new DefaultMutableTreeNode("SHA3-256"));
        hash.add(new DefaultMutableTreeNode("MD5"));
        
        root.add(symmetric);
        root.add(asymmetric);
        root.add(hash);

        JTree algorithmTree = new JTree(root);
        algorithmTree.setRootVisible(false);// "root" vẫn tồn tại nhưng bị ẩn đi
        algorithmTree.setShowsRootHandles(true);
        
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) algorithmTree.getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
        
        algorithmTree.addTreeSelectionListener(e -> {
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode) algorithmTree.getLastSelectedPathComponent();
        	if (node == null) {
        		return;
        	}
        	String selected = node.getUserObject().toString();
        	DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
            String parent = parentNode != null ? parentNode.getUserObject().toString() : "";

            if ("Ma hoa doi xung".equals(selected) || "Ma hoa doi xung".equals(parent)) {
                cardLayout.show(rightPanel, "symmetric");
                if ("Ma hoa doi xung".equals(parent)) {
                    symmetricPanel.setSelectedAlgorithm(selected);
                }
                return;
            }

            if ("Ma hoa bat doi xung".equals(selected) || "Ma hoa bat doi xung".equals(parent)) {
                cardLayout.show(rightPanel, "asymmetric");
                if ("Ma hoa bat doi xung".equals(parent)) {
                    aSymmetricPanel.setSelectedAlgorithm(selected);
                }
                return;
            }

            if ("Ham bam".equals(selected) || "Ham bam".equals(parent)) {
            	cardLayout.show(rightPanel, "hash");
            	if ("Ham bam".equals(parent)) {
            		hashPanel.setSelectedAlgorithm(selected);
            	}
            	
            }
        	
        });
        
        panel.add(new JScrollPane(algorithmTree), BorderLayout.CENTER);

        algorithmTree.setSelectionPath(new TreePath(symmetric.getPath()));
        
		return panel;
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
