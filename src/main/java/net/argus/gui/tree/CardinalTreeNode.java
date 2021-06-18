package net.argus.gui.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import net.argus.cjson.CJSONItem;
import net.argus.cjson.value.CJSONObject;
import net.argus.cjson.value.CJSONString;
import net.argus.instance.Instance;
import net.argus.util.ArrayManager;
import net.argus.util.CardinalEnum;

public class CardinalTreeNode implements TreeNode {
	
	private String name;
	private String value;
	
	private boolean leaf;
	
	private List<CardinalTreeNode> child = new ArrayList<CardinalTreeNode>();

	public CardinalTreeNode(String name, int id) {
		this(name, id, Instance.currentInstance());
	}
	
	public CardinalTreeNode(String name, int id, Instance instance) {
		this(name, id + "@" + instance.getName(), true);
	}
	
	public CardinalTreeNode(String name, String value) {
		this(name, value, true);
	}
	
	public CardinalTreeNode(String name, String value, boolean leaf) {
		this.name = name;
		this.value = value;
		this.leaf = leaf;
	}
	
	@SuppressWarnings("unchecked")
	public static CardinalTreeNode nextNode(String name, String value, boolean leaf, List<CJSONItem> items) {
		CardinalTreeNode node = new CardinalTreeNode(name, value, leaf);
		for(CJSONItem item : items) {
			if(item.getValue() instanceof CJSONObject)
				node.addChild(nextNode(item.getName(), null, false, (List<CJSONItem>) item.getValue().getValue()));
			else if(item.getValue() instanceof CJSONString)
				node.addChild(new CardinalTreeNode(item.getName(), item.getValue().getString()));
		}
		return node;
	}
	
	public CardinalTreeNode getNode(int id, Instance instance) {
		for(CardinalTreeNode n : child) {
			if(n.isLeaf() && n.getValue().toLowerCase().equals(instance.getName().toLowerCase() + "@" + id))
				return n;
			else {
				CardinalTreeNode node = n.getNode(id, instance);
				if(node != null)
					return node;
			}
		}
		return null;
	}
	
	public Object[] getPath(int id, Instance instance) {
		for(CardinalTreeNode n : child) {
			if(n.isLeaf() && n.getValue().toLowerCase().equals(instance.getName().toLowerCase() + "@" + id))
				return new Object[] {this, n};
			else {
				Object[] path = n.getPath(id, instance);
				if(path != null)
					return ArrayManager.add(path, this, 0);
			}
		}
		return null;
	}
	
	@Override
	public TreeNode getChildAt(int childIndex) {
		return child.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return child.size();
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public int getIndex(TreeNode node) {
		return child.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return leaf;
	}

	@Override
	public Enumeration<CardinalTreeNode> children() {
		return new CardinalEnum<CardinalTreeNode>(child);
	}
	
	public void addChild(CardinalTreeNode node) {
		child.add(node);
	}
	
	public void addChild(String name, String value) {
		child.add(new CardinalTreeNode(name, value));
	}
	
	public void addChild(String name, int id) {
		child.add(new CardinalTreeNode(name, id));
	}
	
	public void addChild(String name, int id, Instance instance) {
		child.add(new CardinalTreeNode(name, id, instance));
	}
	
	public String getName() {return name;}
	public String getValue() {return value;}
	
	public int getId() {
		if(!isLeaf())
			return -1;
		
		int index = value.indexOf('@');
		
		if(index == -1)
			return -1;
		
		return Integer.valueOf(value.substring(index + 1));
	}
	
	public String getInstanceName() {
		if(!isLeaf())
			return null;
		
		int index = value.indexOf('@');
		
		if(index == -1)
			return null;
		
		return value.substring(0, index);
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
