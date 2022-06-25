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
import net.argus.system.OS;
import net.argus.system.UserSystem;
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
	
	private boolean newGraph = true;
	
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
	
	private void init0() {
		System.out.println("init0");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void init() {
		newGraph = UserSystem.getBooleanProperty("gui.frame.newgraph");
		if(!newGraph) {
			init0();
			return;
		}
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
	
	private void setSize0(int width, int height) {
		super.setSize(width, height);
	}
	
	@Override
	public void setSize(int width, int height) {
		if(!newGraph) {
			setSize0(width, height);
			return;
		}
		width = width<=MIN_WIDTH?MIN_WIDTH:width;
		height = height<=MIN_HEIGHT?MIN_HEIGHT:height;
		
		super.setSize(width, height);
		titleBar.setSize();
	}
	
	@Override
	public void setSize(Dimension d) {
		if(!newGraph) {
			setSize0(d.width, d.height);
			return;
		}
		setSize(d.width, d.height);
	}
	
	private void setVisible0(boolean b) {
		super.setVisible(b);
	}
	
	@Override
	public void setVisible(boolean b) {
		if(!newGraph) {
			setVisible0(b);
			return;
		}
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
	
	private void removeAll0() {
		super.removeAll();
	}
	
	@Override
	public void removeAll() {
		if(!newGraph) {
			removeAll0();
			return;
		}
		contentPane.removeAll();
	}
	
	public void maximize0() {
		super.setExtendedState(MAXIMIZED_BOTH);
	}
	
	private void maximize() {
		if(newGraph) {
			maximize0();
			return;
		}
		
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
		if(!newGraph)
			return;
		titleBar.setIcon(image);
	}
	
	public ImageIcon getFrameIconImage() {if(!newGraph)return null; return titleBar.getIcon();}
	
	private Component add0(Component comp) {
		return super.add(comp);
	}
	
	@Override
	public Component add(Component comp) {
		if(!newGraph)
			return add0(comp);
		return contentPane.add(comp);
	}
	
	private Component add0(String name, Component comp) {
		return super.add(comp);
	}
	
	@Override
	public Component add(String name, Component comp) {
		if(!newGraph)
			return add0(name, comp);
		return contentPane.add(name, comp);
	}
	
	private void setContentPane0(Container contentPane) {
		super.setContentPane(contentPane);
	}
	
	@Override
	public void setContentPane(Container contentPane) {
		if(!newGraph) {
			setContentPane0(contentPane);
			return;
		}
		super.remove(this.contentPane);
		this.contentPane = contentPane;
		
		super.add(BorderLayout.CENTER, contentPane);
	}
	
	private void setLocation0(int x, int y) {
		super.setLocation(x, y);
	}
	
	@Override
	public void setLocation(int x, int y) {
		if(!newGraph) {
			setLocation0(x, y);
			return;
		}
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
		if(!newGraph)
			return;
		contentPane.setLayout(manager);
	}
	
	public LayoutManager getContentPaneLayout() {
		if(!newGraph)
			return null;
		return contentPane.getLayout();
	}
	
	public boolean isMaximized() {
		if(!newGraph)
			throw new IllegalStateException();
		return maximize;
	}
	
	public void setMaximize(boolean max) {
		if(!newGraph)
			return;
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
		if(newGraph)
			titleBar.setTitle(title);
		super.setTitle(title);
	}
	
	private void saveSize() {
		if(newGraph)
			size = getSize();
	}
	
	private void savePosition() {
		if(newGraph)
			position = getLocation();
	}
	
	public void setEnableButton(TitleButtonType type, boolean e) {
		if(newGraph)
			titleBar.setEnabled(type, e);
	}
	
	public void addFrameListener(FrameListener listener) {event.addListener(listener);}
	
	public void setFrameAnimation(FrameAnimation frameAnimation) {if(newGraph) return; this.frameAnimation = frameAnimation;}
	
	public EventFrame getEvent() {return event;}
	
}
