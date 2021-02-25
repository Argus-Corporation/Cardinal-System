package net.argus.gui.top.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import net.argus.event.mouse.EventMouse;
import net.argus.event.mouse.MouseTrackListener;
import net.argus.gui.Frame;

class Button extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3716187022755205677L;
	
	public static Color red = Color.decode("#ED6A5F");
	public static Color yellow = Color.decode("#F6C351");
	public static Color green = Color.decode("#62C554");
	
	public static Color unused = new Color(180, 180, 180);
	
	private static int width = 17, height = width;
	
	private Color color;
	private ImageButton img;
	
	private boolean showImg;
	private boolean enableColor = true;
	
	private ButtonType type; 
	
	private EventMouse event = new EventMouse();
	
	public Button(Frame fen, ImageButton img, ButtonType type, Color color) {
		setPreferredSize(new Dimension(width + 2, height + 2));
		setBounds(0, 0, width + 2, height + 2);
		setOpaque(false);
		fen.addFocusListener(getFrameFocusListener());
		
		this.img = img;
		this.color = color;
		this.type = type;
		
		addMouseListener(getMouseListener());
	}
	
	public void showImg(boolean show) {this.showImg = show;}
	
	@Override
	protected void paintComponent(Graphics g) {}
	
	public void draw(Graphics g) {
		Graphics2D g2d = createGraphic(g);
		Color color = this.color;
		
		if(!enableColor)
			color = unused;
		
		drawButton(g2d, color);
	}
	
	private void drawButton(Graphics2D g2d, Color color) {
		GradientPaint gp = new GradientPaint(0, 0, color, 0, height, color, true);
		g2d.setPaint(gp);
		
		Ellipse2D.Float r2d = new Ellipse2D.Float(0, 0, width, height);
		
		Shape clip = g2d.getClip();
		g2d.clip(r2d);
		
		g2d.fillOval(1, 1, width, height);
		g2d.setClip(clip);
		
		g2d.drawOval(1, 1, width - 1, height - 1);
		g2d.drawOval(1, 1, width - 3, height - 3);
		
		if(showImg) img.draw(g2d);
		
		g2d.dispose();
	}
	
	private Graphics2D createGraphic(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		return g2d;
	}
	
	public ButtonType getType() {return type;}
	
	public void setEnableColor(boolean enable) {
		this.enableColor = enable;
		repaint();
	} 
	
	public void addMouseTrackListener(MouseTrackListener listener) {event.addListener(listener);}
	
	private MouseListener getMouseListener() {
		Button but = this;
		return new MouseListener() {
			public void mouseReleased(MouseEvent e) {e.setSource(but); event.startEvent(EventMouse.MOUSE_CLICKED, e);}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {e.setSource(but); event.startEvent(EventMouse.MOUSE_EXITED, e);}
			public void mouseEntered(MouseEvent e) {e.setSource(but); event.startEvent(EventMouse.MOUSE_ENTERED, e);}
			public void mouseClicked(MouseEvent e) {}
		};
	}
	
	private FocusListener getFrameFocusListener() {
		return new FocusListener() {
			public void focusLost(FocusEvent e) {setEnableColor(false);}
			public void focusGained(FocusEvent e) {setEnableColor(true);}
		};
	}

}
