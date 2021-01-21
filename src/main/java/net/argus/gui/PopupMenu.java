package net.argus.gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;

import net.argus.annotation.Test;

@Test(info = "null")
@Deprecated
public class PopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PopupMenu(Component parent) {
		parent.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				System.out.println(e.isPopupTrigger());
				if(e.isPopupTrigger())
					show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

}
