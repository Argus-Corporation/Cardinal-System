package net.argus.gui.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;

import net.argus.file.cjson.CJSON;
import net.argus.file.cjson.CJSONElement;
import net.argus.file.cjson.CJSONItem;
import net.argus.file.cjson.CJSONObject;
import net.argus.file.cjson.CJSONString;
import net.argus.util.CharacterManager;
import net.argus.util.ListenerManager;

public class CardinalTree implements TreeNode {
	
	protected CJSONItem tree;
	protected CJSONString name;
	
	protected List<CardinalTree> children = new ArrayList<CardinalTree>();
	
	private ListenerManager<TreeListener> listenerManager = new ListenerManager<TreeListener>();
	
	protected CardinalTree(CJSONItem obj) {
		this.tree = obj;
		this.name = obj.getName();
		
		nextTreeNode();
	}
	
	public CardinalTree(CJSON cjson, String mainName) {
		this.tree = cjson.getItem(mainName);
		this.name = tree.getName();
		
		nextTreeNode();
	}
	
	public void nextTreeNode() {
		for(int i = 0; i < tree.getValue().size(); i++) {
			if(!(tree.getValue() instanceof CJSONElement))
				children.add(new CardinalTree(tree.getValue().getItem(i)));
		}
	}
	
	public TreeSelectionListener getTreeSelectionListener() {
		return new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				
				if(e.getNewLeadSelectionPath() != null) {
					Object[] path = e.getNewLeadSelectionPath().getPath();
					CJSONObject obj = tree.getValue();
					for(int i = 1; i < path.length; i++)
						obj = obj.getValue(path[i].toString());
					
					if(CharacterManager.isNumber(obj.toString()))
						for(TreeListener listener : listenerManager.getListeners())
							listener.selectedItemValue(Integer.valueOf(obj.toString()));
				}
			}
		};
	}
	
	@Override
	public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return children.size()<=0;
	}

	@Override
	public Enumeration<CardinalTree> children() {
		return null;
	}
	
	@Override
	public String toString() {
		return name.toString();
	}
	
	public void addTreeListener(TreeListener listener) {listenerManager.addListener(listener);}

}
