package net.argus.util.notify;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JWindow;

public class NotifyWindow extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4780675372783192303L;
	
	private int y = -1;
	private int basY = -1;
	
	public NotifyWindow() {
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		addMouseListener(getMouseListener());
		addMouseMotionListener(getMouseMotionListener());
		
		setBackground(new Color(0, 0, 0, 0));
	}
	
	public void setNotifyComponent(NotifyComponent comp) {add(comp); repaint();}
	
	private MouseMotionListener getMouseMotionListener() {
		return new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {}
			public void mouseDragged(MouseEvent e) {
				if(y != -1) {
					int yO = e.getLocationOnScreen().y;
					
				/*	float coef = 0.2f;
					
					float op = (float) ((yO * (100.0f * (coef * 10)) / basY) / 100.0f * (coef * 10));
					System.out.println(op - coef);*/
					//setOpacity(op - coef);
					setLocation(getLocation().x, yO - y);
				}
			}
		};
	}
	
	private MouseListener getMouseListener() {
		return new MouseListener() {
			public void mouseReleased(MouseEvent e) {y = -1;}
			public void mousePressed(MouseEvent e) {y = e.getPoint().y;}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		};
	}
	
	@Override
	public void setLocation(int x, int y) {
		if(basY == -1)
			basY = y;
		/*else {
			float op = (float) y * 0.5f / (basY / 2f);
			System.out.println(op);
			setOpacity(op);
		}
		*/
		super.setLocation(x, y);
	}

}
