package net.argus.gui.top;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import net.argus.util.Display;

class Motion {
	
	private TitleBar titleBar;
	
	private Point clickPoint;
	
	public Motion(TitleBar titleBar) {
		this.titleBar = titleBar;
		
		titleBar.addMouseListener(getMouseListener());
		titleBar.addMouseMotionListener(getMouseMotionListener());
	}
	
	private MouseMotionListener getMouseMotionListener() {
		return new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {}
			public void mouseDragged(MouseEvent e) {
				
				int y = getY(e.getYOnScreen());
				
				if((titleBar.getFrame().isMaximized() || titleBar.getFrame().isFullScreen()) && y > 0)
					titleBar.getFrame().setMaximize(false);
				
				if(clickPoint != null && !titleBar.getFrame().isMaximized()) {
					Point currCoords = e.getLocationOnScreen();
					titleBar.getFrame().setLocation(currCoords.x - clickPoint.x, currCoords.y - clickPoint.y);
				}
			}
		};
	}
	
	private MouseListener getMouseListener() {
		return new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				clickPoint = null;
				
				if(!titleBar.getFrame().isMaximized() && getY(titleBar.getFrame().getLocation().y) <= 0)
					titleBar.getFrame().setMaximize(true);
				
			}
			public void mousePressed(MouseEvent e) {clickPoint = e.getPoint();}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
					titleBar.getFrame().setMaximize(!titleBar.getFrame().isMaximized());
			}
		};
	}
	
	private int getY(int y) {
		Rectangle winSize = Display.getMaximumWindowBounds();

		return y - winSize.y;
	}
	
}
