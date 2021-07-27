package net.argus.event.gui.tree;

import javax.swing.tree.TreePath;

import net.argus.gui.tree.Tree;

public class TreeEvent {
	
	private Tree tree;
	private TreePath path;
	
	public TreeEvent(Tree tree, TreePath path) {
		this.tree = tree;
		this.path = path;
	}
	
	public Tree getTree() {return tree;}
	public TreePath getPath() {return path;}

}
