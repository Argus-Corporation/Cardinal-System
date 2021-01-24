package net.argus.gui.tree;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import net.argus.util.ArrayManager;

public class Tree extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1496939304492998804L;
	
	private CardinalTree tree;
	
	private int oldId = -1;
	
	public Tree(CardinalTree treeNode) {
		super(treeNode);
		this.tree = treeNode;
		addTreeSelectionListener(tree.getTreeSelectionListener());
		
	}
	
	@Override
	public void setSelectionPath(TreePath path) {
		int id = tree.getSelectedId(getSelectionPath());
		if(id != -1)
			oldId = id;
			
		super.setSelectionPath(path);
	}
	
	public void setSelection(int id) {
		if(id != -1) {
			setSelectionPath(new TreePath(getPath(id)));
			tree.getTreePath(1, null);
		}
	}
	
	public void setSelectionOldItem() {
		setSelection(oldId);
	}
	
	public Object[] getPath(int id) {
		return ArrayManager.add(tree.getPath(id, null), tree, 0);
	}
	
	public void addTreeListener(TreeListener listener) {tree.addTreeListener(listener);}

}
