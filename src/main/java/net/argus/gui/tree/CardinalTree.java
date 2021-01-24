package net.argus.gui.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import net.argus.file.cjson.CJSON;
import net.argus.file.cjson.CJSONElement;
import net.argus.file.cjson.CJSONItem;
import net.argus.file.cjson.CJSONObject;
import net.argus.file.cjson.CJSONString;
import net.argus.util.ArrayManager;
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
					for(int i = 1; i < path.length; i++) {
						CJSONObject ob = obj.getValue(path[i].toString());
						obj = ob!=null?ob:obj;
					}
					
					if(CharacterManager.isNumber(obj.toString()))
						for(TreeListener listener : listenerManager.getListeners())
							listener.selectedItemValue(Integer.valueOf(obj.toString()));
				}
			}
		};
	}
	
	public List<CardinalTree> getListPath(int id, List<CardinalTree> children) {
		List<CardinalTree> trees = new ArrayList<CardinalTree>();
		for(CardinalTree tr : children!=null?children:this.children)
			if(tr.tree.getValue() instanceof CJSONElement) {
				if(Integer.valueOf(tr.tree.getValue().toString()) == id) {
					trees.add(tr);
					return trees;
				}
			}else {
				trees = getListPath(id, tr.children);
				trees.add(0, tr);
				return trees;
			}
		return null;
	}
	
	public List<CardinalTree> getListPath(String name, List<CardinalTree> children) {
		List<CardinalTree> trees = new ArrayList<CardinalTree>();
		for(CardinalTree tr : children!=null?children:this.children)
			if(tr.tree.getValue() instanceof CJSONElement) {
				if(tr.tree.getName().toString().equals(name)) {
					trees.add(tr);
					return trees;
				}
			}else {
				trees = getListPath(name, tr.children);
				if(trees != null)
					trees.add(0, tr);
				return trees;
			}
		return null;
	}
	
	public Object[] getPath(int id, List<CardinalTree> children) {
		for(CardinalTree tr : children!=null?children:this.children)
			if(tr.tree.getValue() instanceof CJSONElement) {
				if(Integer.valueOf(tr.tree.getValue().toString()) == id)
					return new Object[] {tr};
			}else
				return ArrayManager.add(getPath(id, tr.children), tr, 0);
		return null;
	}
	
	public Object[] getPath(String name, List<CardinalTree> children) {
		for(CardinalTree tr : children!=null?children:this.children)
			if(tr.tree.getValue() instanceof CJSONElement) {
				System.out.println(tr.tree.toString());
				if(tr.tree.getName().toString().equals(name))
					return new Object[] {tr};
			}else
				return ArrayManager.add(getPath(name, tr.children), tr, 0);
		return null;
	}
	
	public CardinalTree[] getTreePath(int id, List<CardinalTree> children) {
		return (CardinalTree[]) getListPath(id, children).toArray(new CardinalTree[getListPath(id, children).size()]);
	}
	
	public CardinalTree[] getTreePath(String name, List<CardinalTree> children) {
		List<CardinalTree> path = getListPath(name, children);
		if(path != null)
			return (CardinalTree[]) path.toArray(new CardinalTree[path.size()]);
		else
			return null;
	}
	
	public int getSelectedId(TreePath path) {
		if(path != null) {
			Object[] paths = path.getPath();
			CardinalTree[] objs = (CardinalTree[]) getTreePath(paths[paths.length - 1].toString(), null);
			if(objs != null) {
				CardinalTree obj = objs[objs.length - 1];
			
				return Integer.valueOf(obj.tree.getValue().toString());
			}else
				return -1;
			
		}else
			return -1;
	}
	
	@Override
	public TreeNode getChildAt(int childIndex) {return children.get(childIndex);}

	@Override
	public int getChildCount() {return children.size();}

	@Override
	public TreeNode getParent() {return null;}

	@Override
	public int getIndex(TreeNode node) {return children.indexOf(node);}

	@Override
	public boolean getAllowsChildren() {return true;}

	@Override
	public boolean isLeaf() {return children.size()<=0;}

	@Override
	public Enumeration<CardinalTree> children() {return null;}
	
	@Override
	public String toString() {return name.toString();}
	
	public void addTreeListener(TreeListener listener) {listenerManager.addListener(listener);}

}
