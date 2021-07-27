package net.argus.gui.frame.top.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JPanel;

import net.argus.event.mouse.EventMouse;
import net.argus.event.mouse.MouseTrackListener;
import net.argus.gui.frame.Frame;

class Button extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3716187022755205677L;
	
	public static Color red = Color.decode("#ED6A5F");
	public static Color yellow = Color.decode("#F6C351");
	public static Color green = Color.decode("#62C554");
	
	public static Color unused = new Color(180, 180, 180);
	
	public static Color border = new Color(128, 128, 128, 128);
	
	private static int width = 17, height = width;
	
	private Color color;
	private ImageButton img;
	
	private boolean showImg;
	private boolean enableColor = true;
	
	private TitleButtonType type; 
	
	private EventMouse event = new EventMouse();
	
	public Button(Frame fen, ImageButton img, TitleButtonType type, Color color) {
		setPreferredSize(new Dimension(width + 2, height + 2));
		setBounds(0, 0, width + 2, height + 2);
		setOpaque(false);
		
		fen.addWindowFocusListener(getFrameFocusListener());
		
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
		g2d.setColor(isEnabled()?color:unused);
		g2d.fillOval(0, 0, width + 1, height + 1);
		
		g2d.setColor(border);
		g2d.drawOval(0, 0, width, height);
		
		if(isEnabled() && showImg) img.draw(g2d);
		
		g2d.dispose();
	}
	
	private Graphics2D createGraphic(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		return g2d;
	}
	
	public TitleButtonType getType() {return type;}
	
	public void setEnableColor(boolean enable) {
		this.enableColor = enable;
		repaint();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setVisible(enabled);
		super.setEnabled(enabled);
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
	
	private WindowFocusListener getFrameFocusListener() {
		return new WindowFocusListener() {
			public void windowLostFocus(WindowEvent e) {setEnableColor(false);}
			public void windowGainedFocus(WindowEvent e) {setEnableColor(true);}
		};
	}

}
