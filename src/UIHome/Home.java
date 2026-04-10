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

import UIComponent.ASymmetricPanel;
import UIComponent.Hash;
import UIComponent.Signature;
import UIComponent.SymmetricPanel;

public class Home extends JFrame {
	private SymmetricPanel symmetricPanel;
	private ASymmetricPanel aSymmetricPanel;
	private Hash hash;
	private Signature signature;
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
        aSymmetricPanel = new ASymmetricPanel();
        hash = new Hash();
        signature = new Signature();
        
        // create right panel contains component panel with cardlayout
        rightPanel = new JPanel();
        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);
        rightPanel.add(symmetricPanel, "symmetric");
        rightPanel.add(aSymmetricPanel, "asymmetric");
        rightPanel.add(hash, "hash");
        rightPanel.add(signature, "signature");
        
        
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
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		DefaultMutableTreeNode symmetric = new DefaultMutableTreeNode("Ma hoa doi xung");
		symmetric.add(new DefaultMutableTreeNode("AES"));
		symmetric.add(new DefaultMutableTreeNode("DES"));
		symmetric.add(new DefaultMutableTreeNode("Blowfish"));
		symmetric.add(new DefaultMutableTreeNode("TripleDES"));
		
		DefaultMutableTreeNode asymmetric = new DefaultMutableTreeNode("Ma hoa bat doi xung");
		asymmetric.add(new DefaultMutableTreeNode("RSA"));

		
		DefaultMutableTreeNode hash = new DefaultMutableTreeNode("Ham bam");
        hash.add(new DefaultMutableTreeNode("SHA-256"));
        hash.add(new DefaultMutableTreeNode("SHA-1"));
        hash.add(new DefaultMutableTreeNode("SHA-512"));
        hash.add(new DefaultMutableTreeNode("SHA-384"));
        hash.add(new DefaultMutableTreeNode("SHA3-256"));
        hash.add(new DefaultMutableTreeNode("MD5"));

        DefaultMutableTreeNode signature = new DefaultMutableTreeNode("Chu ky so");
        signature.add(new DefaultMutableTreeNode("SHA256withRSA"));
        
        root.add(symmetric);
        root.add(asymmetric);
        root.add(hash);
        root.add(signature);

        JTree algorithmTree = new JTree(root);
        algorithmTree.setRootVisible(false);
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
