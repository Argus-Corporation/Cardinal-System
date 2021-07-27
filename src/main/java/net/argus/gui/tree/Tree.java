package net.argus.gui.tree;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import net.argus.cjson.CJSON;
import net.argus.event.gui.tree.EventTree;
import net.argus.event.gui.tree.TreeEvent;
import net.argus.event.gui.tree.TreeListener;
import net.argus.gui.BackgoundRegister;
import net.argus.gui.FontRegister;
import net.argus.gui.ForegroundRegiter;
import net.argus.gui.GUI;
import net.argus.instance.Instance;
import net.argus.lang.LangRegister;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class Tree extends JTree implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1496939304492998804L;
	
	public EventTree event = new EventTree();
	
	public TreePath oldPath;
	
	public Tree(CJSON cjson) {
		this(new CardinalTreeModel(cjson));
	}
	
	public Tree(CJSON cjson, String rootName) {
		this(new CardinalTreeModel(cjson, rootName));
	}
	
	public Tree(CardinalTreeModel treeModel) {
		super(treeModel);
		
		setCellRenderer(new CardinalCellRenderer());
		
		LangRegister.addElementLanguage(this);
		ForegroundRegiter.addElement(this);
		BackgoundRegister.addElement(this);
		FontRegister.addElement(this);
		
		oldPath = new TreePath(treeModel.getRoot());
	}
	
	public void setSelection(int id) {
		setSelection(id, Instance.currentInstance());
	}
	
	public void setSelection(int id, Instance instance) {
		Object obj = getModel().getRoot();
		
		if(!(obj instanceof CardinalTreeNode))
			return;
		
		CardinalTreeNode root = (CardinalTreeNode) obj;
		Object[] objPath = root.getPath(id, instance);
		if(objPath == null || objPath.length == 0) {
			Debug.log("The path to the id \"" + id + "\" with the instance \"" + instance + "\" not found", Info.ERROR);
			return;
		}
		
		TreePath path = new TreePath(objPath);
		setSelectionPath(path);
	}

	@Override
	public void setText() {
		repaint();
	}

	@Override
	public String getElementName() {
		return "Tree";
	}
	
	@Override
	public void setSelectionPath(TreePath path) {
		Object no = path.getLastPathComponent();
		
		if(!(no instanceof CardinalTreeNode)) {
			setSelectPath(path);
			return;
		}
		CardinalTreeNode node = (CardinalTreeNode) no;
		TreePath curPath = getSelectionPath();
		
		if(curPath == null) {
			setSelectPath(path);
			return;
		}
		
		Object cur = curPath.getLastPathComponent();
			
		if(!(cur instanceof CardinalTreeNode)) {
			setSelectPath(path);
			return;
		}
		
		CardinalTreeNode currentNode = (CardinalTreeNode) cur;
		
		if(node.isLeaf() && !node.getValue().equals(currentNode.getValue()))
			setSelectPath(path);
		
	}
	
	public void setSelectPath(TreePath path) {
		if(path == null)
			return;
		
		oldPath = getSelectionPath();
		
		event.startEvent(EventTree.TREE_NODE_SELECTED, new TreeEvent(this, path));
		super.setSelectionPath(path);

	}
	
	public void addTreeListener(TreeListener listener) {event.addListener(listener);}
	
	public TreePath getOldPath() {return oldPath;}
	/*
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
	
	public void addTreeListener(TreeListener listener) {tree.addTreeListener(listener);}*/

}
