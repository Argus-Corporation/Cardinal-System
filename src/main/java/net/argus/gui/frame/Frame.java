package net.argus.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import net.argus.event.gui.frame.EventFrame;
import net.argus.event.gui.frame.FrameListener;
import net.argus.gui.animation.FrameAnimation;
import net.argus.gui.frame.top.TitleBar;
import net.argus.gui.frame.top.button.TitleButtonType;
import net.argus.util.Display;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3331711015752667605L;
	
	public static final int MIN_WIDTH = 140;
	public static final int MIN_HEIGHT = 35;
	
	private boolean maximize = false;
	
	private TitleBar titleBar;
	private Container contentPane = new Container();
	
	private Dimension size;
	private Point position;
		
	private FrameAnimation frameAnimation = new FrameAnimation(this);
	
	private EventFrame event = new EventFrame();
	
	public Frame() {
		super();
		init();
	}
	
	public Frame(GraphicsConfiguration gc) {
		super(gc);
		init();
	}
	
	public Frame(String title) {
		super(title);
		init();
	}
	
	public Frame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		init();
	}
	
	private void init() {
		super.setUndecorated(true);
		
		addWindowListener(getWindowListener());
		
		titleBar = new TitleBar(this);
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBackground(new Color(0, 0, 0, 0));
		setFocusable(true);

		super.add(BorderLayout.NORTH, titleBar);
		super.add(BorderLayout.CENTER, contentPane);	
	}
	
	private WindowListener getWindowListener() {
		return new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {setVisible(false);}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		};
	}
	
	@Override
	public void setUndecorated(boolean undecorated) {}
	
	@Override
	public void setSize(int width, int height) {
		width = width<=MIN_WIDTH?MIN_WIDTH:width;
		height = height<=MIN_HEIGHT?MIN_HEIGHT:height;
		
		super.setSize(width, height);
		titleBar.setSize();
	}
	
	@Override
	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}
	
	@Override
	public void setVisible(boolean b) {
		if(frameAnimation != null) {
			if(b && !isVisible()) {
				setOpacity(0f);
				super.setVisible(b);
				frameAnimation.play(FrameAnimation.OPEN);
			}else if(b && isVisible()) {
					
				if(getOpacity() > 0f) {
					float op = getOpacity();
					
					for(int i = 0; i < 3; i++) {
						float opa = getOpacity() - op / 3f;
						if(opa < 0f)
							opa = 0f;
						
						setOpacity(opa);
					}
				}
				
				if(getState() == ICONIFIED)
					setExtendedState(NORMAL);

				frameAnimation.play(FrameAnimation.OPEN);
			}else if(isVisible()) {
				frameAnimation.play(FrameAnimation.CLOSE);
				super.setVisible(false);
			}
			
		}else
			super.setVisible(b);
	}
	
	@Override
	public void removeAll() {
		contentPane.removeAll();
	}
	
	private void maximize() {
		Dimension scrnSize = Display.getSize();
		Rectangle winSize = Display.getMaximumWindowBounds();
		
		int taskBarWidth = scrnSize.width - winSize.width;
		int taskBarHeight = scrnSize.height - winSize.height;
		
		savePosition();
		saveSize();
				
		setSize(scrnSize.width - taskBarWidth, scrnSize.height - taskBarHeight);
		setLocationRelativeTo(null);
	}
	
	@Override
	public void setIconImage(Image image) {
		super.setIconImage(image);
	}
	
	public void setFrameIconImage(Image image) {
		setFrameIconImage(new ImageIcon(image));
	}
	
	public void setFrameIconImage(ImageIcon image) {
		titleBar.setIcon(image);
	}
	
	public ImageIcon getFrameIconImage() {return titleBar.getIcon();}
	
	@Override
	public Component add(Component comp) {
		return contentPane.add(comp);
	}
	
	@Override
	public Component add(String name, Component comp) {
		return contentPane.add(name, comp);
	}
	
	@Override
	public void setContentPane(Container contentPane) {
		super.remove(this.contentPane);
		this.contentPane = contentPane;
		
		super.add(BorderLayout.CENTER, contentPane);
	}
	
	@Override
	public void setLocation(int x, int y) {
		Rectangle winSize = Display.getMaximumWindowBounds();
		
		if(y < winSize.y)
			y = winSize.y;
		
		if(x + getWidth() - 10 < winSize.x)
			x = winSize.x - getWidth() + 10;
		
		if(x + 10 > winSize.x + winSize.width)
			x = winSize.x + winSize.width - 10;
				
		if(y + 10 > winSize.y + winSize.height)
			y = winSize.y + winSize.height - 10;
		
		super.setLocation(x, y);
	}
	
	public void setContentPaneLayout(LayoutManager manager) {
		contentPane.setLayout(manager);
	}
	
	public LayoutManager getContentPaneLayout() {
		return contentPane.getLayout();
	}
	
	public boolean isMaximized() {
		return maximize;
	}
	
	public void setMaximize(boolean max) {
		if(max)
			maximize();
		else if(size != null && position != null && isMaximized()) {
			setSize(size);
			setLocation(position);
		}
		this.maximize = max;
		
		
		titleBar.repaint();
		titleBar.getGroupButton().repaint();
		
	}
	
	@Override
	public void setTitle(String title) {
		titleBar.setTitle(title);
		super.setTitle(title);
	}
	
	private void saveSize() {
		size = getSize();
	}
	
	private void savePosition() {
		position = getLocation();
	}
	
	public void setEnableButton(TitleButtonType type, boolean e) {
		titleBar.setEnabled(type, e);
	}
	
	public void addFrameListener(FrameListener listener) {event.addListener(listener);}
	
	public void setFrameAnimation(FrameAnimation frameAnimation) {this.frameAnimation = frameAnimation;}
	
	public EventFrame getEvent() {return event;}
	
}
