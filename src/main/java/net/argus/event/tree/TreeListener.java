package net.argus.event.tree;

import net.argus.util.Listener;

public interface TreeListener extends Listener {
	
	public void treeNodeSelected(TreeEvent e);

}
