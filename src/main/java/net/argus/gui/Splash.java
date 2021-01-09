package net.argus.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.argus.file.FileManager;
import net.argus.file.Properties;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.Display;

@Deprecated
public class Splash extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2295168357935250694L;
	
	private SplashContent contentpane;
	public String statusText;
	private Robot robot;
	
	private int time;
	
	private boolean valid;
	
	public Splash(String title, ImageIcon splashImage, int time) {
		super();
		valid = false;
		this.time = time;
		
		this.setUndecorated(true);
		this.setDefaultCloseOperation(Frame.DISPOSE_ON_CLOSE);
		
		try{robot = new Robot();}catch(Exception e){e.printStackTrace();}
		
		contentpane = new SplashContent();
		
		Label img = new Label(splashImage);
		
		this.setContentPane(contentpane);
		this.getContentPane().add(img);
		this.pack();
		this.setLocationRelativeTo(null);
		createCapture();
		
	}
	
	@Deprecated
	public Splash(String title, String iconPath, Frame fen, int time, boolean nul, Properties config) {
		super(title); 
		this.setDefaultCloseOperation(Frame.DISPOSE_ON_CLOSE);
		try{robot = new Robot();}catch(Exception e){e.printStackTrace();}
		this.setSize(820,  570);
		@SuppressWarnings("unused")
		Properties spashConfig = new Properties("splash", "bin");
		//contentpane = new SplashContent(this, iconPath, this, spashConfig);
		
		this.setContentPane(contentpane);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		new PanelRepaint(fen, this).initImage();
		
		try{Thread.sleep(time);}catch(Exception e){e.printStackTrace();}
		this.setVisible(false);	
		try{Thread.sleep(10);}catch(Exception e){e.printStackTrace();}
	}
	
	public void createCapture() {
		try{
			Point point = new Point(0, 0);
			SwingUtilities.convertPointToScreen(point, contentpane);
			
			Image image = robot.createScreenCapture(new Rectangle(point, contentpane.getSize()));
			
			MediaTracker mt = new MediaTracker(this);
			mt.addImage(image,0);
			mt.waitForAll();
			contentpane.setImage(image);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void play() {
		new Thread(new Runnable() {
			public void run() {
			
				setVisible(true);
				
				try{Thread.sleep(time);}catch(Exception e){e.printStackTrace();}
				setVisible(false);	
				try{Thread.sleep(10);}catch(Exception e){e.printStackTrace();}
				valid = true;
			}
		}).start();
	}
	
	public boolean isValid() {return valid;}

	
	private class SplashContent extends JPanel implements Runnable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2784017252036363371L;
		
		private Image image;
		private Splash parent;
		private Label status;
		
		public SplashContent() {
			super();
			setOpaque(false);
		}
		
		@SuppressWarnings({"unused"})
		public SplashContent(Frame fen, String iconPath, Splash parent, Properties splashConfig) {
			super();
			this.parent = parent;
			this.setLayout(new BorderLayout());
			Panel westPanFlow = new Panel(splashConfig, false);
			Panel westPan = new Panel(splashConfig, false);
			westPan.setPreferredSize(new Dimension(400, 600));
			Panel imgPan = new Panel(splashConfig, splashConfig.getString("panel.img.background"), fen);
			new PanelRepaint(fen, parent).initImage(2);
			ImageIcon image = new ImageIcon(imgPan.getBackgroundImageFloat());
			ImageIcon icon = Icon.getIcon(iconPath, fen.getWidth() / 11, fen.getHeight() / 8);
			ImageIcon cc = Icon.getIcon(splashConfig.getString("cc.img.background"), 30, null);
			
			westPanFlow.setLayout(new FlowLayout());
			imgPan.setLayout(new BorderLayout());
			westPan.setLayout(new BoxLayout(westPan, BoxLayout.Y_AXIS));
			
			JLabel img = new JLabel(image);
			imgPan.add(BorderLayout.EAST, img);
			
			JLabel lIcon = new JLabel(icon);
			Label title = new Label("School", false); 
			Label copy = new Label("� " + splashConfig.getString("copy.text"), false);
			JLabel lCc = new JLabel(cc);
			status = new Label("loading", false);
			statusText = "initialization";
			title.setFont(splashConfig.getFont("title.font"));
			new Thread(this).start();
			
			westPan.add(Box.createRigidArea(new Dimension(55, 30)));
			westPan.add(lIcon);
			westPan.add(Box.createRigidArea(new Dimension(0, 30)));
			westPan.add(title);
			westPan.add(Box.createRigidArea(new Dimension(0, 30)));
			westPan.add(copy);
			westPan.add(Box.createRigidArea(new Dimension(0, 30)));
			westPan.add(splashConfig.getMultiString("text.line"));
			westPan.add(Box.createRigidArea(new Dimension(0, 60)));
			westPan.add(status);
			westPan.add(Box.createRigidArea(new Dimension(0, 175)));
			westPan.add(lCc);
			westPan.add(Box.createRigidArea(new Dimension(0, 30)));
			
			westPanFlow.add(westPan);
			this.add(BorderLayout.EAST, imgPan);
			this.add(BorderLayout.WEST, westPanFlow);
			
		}
			
		public void setImage(Image capture) {image = capture; repaint();}	
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(image != null) g.drawImage(image, 0, 0, this);
			
			/*Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int h = Display.getHeightDisplay(config) - 100;
			int w = h;
				
			GradientPaint GP = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h, new Color(0, 0, 0), true);
			g2d.setPaint(GP);
			g2d.fillRect(0, 0, w, h);
			g2d.dispose();
				
			super.paintComponent(g);*/
		}	
		
		@Override
		public void run() {
			while(!parent.isVisible()) {System.out.print("");}
			while(parent.isVisible()) {
				status.setText("Loading " + statusText);
				
			}
			
		}
	}	
	
	
	public static void main(String[] args) {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		Properties config = new Properties("config", "bin");
		Splash splash = new Splash("Splash", Icon.getIcon(FileManager.getPath("res/logo.png"), Display.getWidhtDisplay() - 20), 1000);
		splash.play();
		
		String iconPath = FileManager.getPath("res/favIcon32x32.png");
		boolean[] isE = new boolean[] {true, true, true};
		
		Frame fen = new Frame("School", iconPath, isE, config);
		
		Panel pan = new Panel(config, FileManager.getPath("res/favIcon16x16.png"), fen);
		new PanelRepaint(fen, splash).initImage();
		
		fen.setIcon(FileManager.getPath("res/favIcon16x16.png"));
		fen.add(pan);
		
		while(!splash.isValid()) {
			fen.setVisible(false);
		}
			
		fen.setVisible(true);
	}
}