package net.argus.event.gui.tree;

import net.argus.event.Event;

public class EventTree extends Event<TreeListener> {
	
	public static final int TREE_NODE_SELECTED = 0;

	@Override
	public void event(TreeListener listener, int event, Object... objs) {
		switch(event) {
			case TREE_NODE_SELECTED:
				listener.treeNodeSelected((TreeEvent) objs[0]);
				break;
		}
	}

}
