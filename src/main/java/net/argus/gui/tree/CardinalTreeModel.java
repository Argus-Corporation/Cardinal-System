package net.argus.gui.tree;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import net.argus.cjson.CJSON;

public class CardinalTreeModel implements TreeModel {
	
	private CardinalTreeNode root;
	
	public CardinalTreeModel(CJSON cjson) {
		this(cjson, "root");
	}
	
	public CardinalTreeModel(CJSON cjson, String rootName) {
		root = CardinalTreeNode.nextNode(rootName, null, false, cjson.getMainObject().getValue());
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((TreeNode) parent).getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((TreeNode) parent).getChildCount();
	}

	@Override
	public boolean isLeaf(Object node) {
		if(node instanceof TreeNode)
			return ((TreeNode) node).isLeaf();
		else {
			return false;
		}
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
        return ((TreeNode)parent).getIndex((TreeNode)child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {}

}
