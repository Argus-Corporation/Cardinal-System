package net.argus.gui;

import javax.swing.JFrame;

@Deprecated
public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6058458405056389502L;
	/*
	public static final int MIN_WIDTH = 140;
	public static final int MIN_HEIGHT = 35;
	
	private TitleBar titleBar;
	private Panel mainPan = new Panel();
	
	private ImageIcon iconFrame;
	private ImageIcon iconOs;

	private boolean fullScreen;
	private boolean maximized;
	
	private Dimension size;
	private Point position;
	
	private Animation anim;
	
	private EventFrame event = new EventFrame();
	
	public Frame(String title, Dimension dimension, boolean undecorated, boolean alwaysOnTop, ImageIcon iconFrame, ImageIcon iconOs) {
		//boolean undecorated = config.getBoolean("frame.undecorated");
		
		mainPan = new Panel();
		
		this.iconFrame = iconFrame;
		this.iconOs = iconOs;
		
		setIconImage(iconOs.getImage());
		
		setSize(dimension);
		saveSize();
		
		this.setTitle(title);
		this.setFocusable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(undecorated);
		this.setAlwaysOnTop(alwaysOnTop);
		
		setBackground(new Color(0, 0, 0, 0));
		
		addFrameListener(getFrameListener());
		
		addWindowListener(getWindowListener());
		
		if(undecorated) {
			titleBar = new TitleBar(this);
			super.add(BorderLayout.NORTH, titleBar);
			super.add(BorderLayout.CENTER, mainPan);

			//getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#DADADA")));
		}
		*/
		/*this.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				/*if(e.getKeyCode() == KeyEvent.VK_F11 && !fullScreen) {
					maximize(false);
					
					event.startEvent(EventFrame.FRAME_RESIZING, new FrameEvent(this, getSize()));
				}else if(e.getKeyCode() == KeyEvent.VK_F11 && fullScreen) {
					setSize(size);
					
					setLocation(position);
					maximized = false;
					fullScreen = false;
					
					event.startEvent(EventFrame.FRAME_RESIZING, new FrameEvent(this, getSize()));

				}
				
			}
		});*/
/*
	}

	public Frame(String title, String pathIcon, Properties config) {
		this(title, config.getDimension("frame.size"), config.getBoolean("frame.undecorated"),
				config.getBoolean("frame.alwaysontop"), new ImageIcon(pathIcon), new ImageIcon(pathIcon));
	}
	
	public Frame(String iconPath) throws MalformedURLException {
		this(new URL(iconPath));
	}
	
	public Frame(URL iconPath) {
		this("", new Dimension(1200, 700), true, false, new ImageIcon(iconPath), new ImageIcon(iconPath));
	}
	*/
	/*protected Frame(String title, String iconPath, Properties config) {
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setSize(config.getDimension("frame.size"));
		this.setAlwaysOnTop(config.getBoolean("frame.alwaysontop"));
	}*/
	/*
	public void removeAll() {
		super.removeAll();
		this.add(BorderLayout.NORTH, titleBar);
		this.repaint();
	}
	
	public void setIcon(String iconPath) {
		iconFrame = Icon.getIcon(iconPath);
		titleBar.setIcon(iconFrame);
	}
	
	@Override
	public void setTitle(String title) {
		if(titleBar != null) titleBar.setTitle(title);
		super.setTitle(title);
	}
	
	public ImageIcon getFrameIcon() {return iconFrame;}
	public ImageIcon getOsIcon() {return iconOs;}
		
	public boolean isMaximized() {return maximized;}
	public boolean isFullScreen() {return fullScreen;}
	
	public void setMaximize(boolean maximized) {
		if(maximized && !isMaximized())
			maximize(true);
		else {
			setSize(size);
			if(position != null) setLocation(position);
			fullScreen = false;
		}
		
		this.maximized = maximized;
		
		event.startEvent(EventFrame.FRAME_RESIZING, new FrameEvent(this, getSize()));
		
	}
	
	private void maximize(boolean maximized) {
		if(maximized) {
			Dimension scrnSize = Display.getSize();
			Rectangle winSize = Display.getMaximumWindowBounds();
					
			int taskBarWidth = scrnSize.width - winSize.width;
			int taskBarHeight = scrnSize.height - winSize.height;
			
			savePosition();
			saveSize();
			
			setSize(scrnSize.width - taskBarWidth, scrnSize.height - taskBarHeight);
			setLocationRelativeTo(null);
		}else {
			if(!isMaximized()) {
				savePosition();
				saveSize();
			}
			
			setSize(Display.getSize());
			setLocationRelativeTo(null);
			
			fullScreen = true;
		}
	}
	
	@Override
	public void setSize(int width, int height) {
		width = width<=MIN_WIDTH?MIN_WIDTH:width;
		height = height<=MIN_HEIGHT?MIN_HEIGHT:height;
		
		saveSize();
		super.setSize(width, height);
	}
	
	@Override
	public void setSize(Dimension d) {
		if(isMaximized()) saveSize();
		super.setSize(d);
	}
	
	@Override
	public void setVisible(boolean b) {
		setOpacity(0f);
		super.setVisible(b);
		
		if(b) {
			if(anim instanceof FrameAnimation) {
				//int i = (int) (1 / FrameAnimation.ADD);
				//super.setSize(getWidth() - FrameAnimation.SIZE_ADD * i, getHeight() - FrameAnimation.SIZE_ADD * i);
				anim.play(FrameAnimation.UNMINIIZE);
			}
		}
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
	
	public TitleBar getTitleBar() {return titleBar;}
	public Point getSavedPosition() {return position;}
	
	public void addFrameListener(FrameListener fenListener) {event.addListener(fenListener);}
	
	public void setAnimation(Animation anim) {this.anim = anim;}
	
	public void savePosition() {this.position = this.getLocation();}
	public void saveSize() {this.size = this.getSize();}
	
	private FrameListener getFrameListener() {
		return new FrameListener() {
			public void frameResizing(FrameEvent e) {}
			public void frameMinimalized(FrameEvent e) {}
			public void frameClosing(FrameEvent e) {
				if(anim != null)
					anim.play(FrameAnimation.CLOSE_MINIMIZE);
			}
		};
	}
	
	private WindowListener getWindowListener() {
		Frame fen = this;
		return new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				event.startEvent(EventFrame.FRAME_CLOSING, new FrameEvent(fen, size));
			}
			public void windowClosed(WindowEvent e) {windowClosing(e);}
			public void windowActivated(WindowEvent e) {}
		};
	}
	
	public void event(int event, Object source) {this.event.startEvent(event, new FrameEvent(source, getSize()));}
	
	public Panel getMainPane() {return mainPan;}
	
	@Override
	public Component add(Component comp) {
		return mainPan.add(comp);
	}
	
	@Override
	public Component add(String name, Component comp) {
		return mainPan.add(name, comp);
	}
	
	public void setMainLayout(LayoutManager manager) {
		mainPan.setLayout(manager);
	}
	
	public LayoutManager getMainLayout() {
		return mainPan.getLayout();
	}*/
	
}