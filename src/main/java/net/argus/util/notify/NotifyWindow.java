package net.argus.util.notify;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JWindow;

import net.argus.event.notify.EventNotify;
import net.argus.event.notify.NotifyEvent;
import net.argus.gui.animation.Animation;
import net.argus.gui.animation.NotifyAnimation;
import net.argus.util.Display;

public class NotifyWindow extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4780675372783192303L;
	
	private Notify notify;
	private NotifyComponent comp;
	private Animation anim;
	
	public NotifyWindow(Notify notify) {
		setAlwaysOnTop(true);
		
		addMouseListener(getMouseListener());
		
		setBackground(new Color(0, 0, 0, 0));
		
		this.notify = notify;
		
		anim = new NotifyAnimation(this);
	}
	
	public void setNotifyComponent(NotifyComponent comp) {this.comp = comp; add(comp); repaint();}
	
	private MouseListener getMouseListener() {
		return new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1)
					setVisible(false);
			}
		};
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b) {
			super.setVisible(b);
			notify.getEvent().startEvent(EventNotify.SHOW, new NotifyEvent("show", comp.getInfo()));
			anim.play(NotifyAnimation.OPEN);
		}else {
			anim.play(NotifyAnimation.CLOSE);
			super.setVisible(b);
			notify.getEvent().startEvent(EventNotify.HIDE, new NotifyEvent("close", comp.getInfo()));
		}	
	}
	
	@Override
	public void pack() {
		super.pack();
		Rectangle screen = Display.getMaximumWindowBounds();
		
		int x, y;
		
		x = (screen.x + screen.width) - getWidth() - 50;
		y = (screen.y + screen.height) - getHeight() - 70;
		
		setLocation(x, y);	
	}

}
