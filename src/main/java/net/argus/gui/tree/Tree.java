package net.argus.gui.tree;

import javax.swing.JTree;

public class Tree extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1496939304492998804L;
	
	private CardinalTree tree;
	
	public Tree(CardinalTree treeNode) {
		super(treeNode);
		this.tree = treeNode;
		addTreeSelectionListener(tree.getTreeSelectionListener());
		
	}
	
	public void addTreeListener(TreeListener listener) {tree.addTreeListener(listener);}

}
