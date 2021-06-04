package net.argus.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.argus.file.Properties;
import net.argus.system.ExtractTemp;
import net.argus.system.Temp;
import net.argus.system.UserSystem;

@Deprecated
public class RondButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2414029985404235678L;
	
	private Color background;
	private GradientPaint GP;
	private JPanel panel;
	private int id;
	private ButtonModel model;
	private static boolean isFirst = true;
	private static Frame fen;
	private static TopPanel top;
	private static int index;
	
	private static RondButton[] rondButton = new RondButton[3];
	
	private static boolean[] isEnable = new boolean[rondButton.length];
	private static boolean[] isRollover = new boolean[rondButton.length];
	private static boolean isR;
	private static ImageIcon iconCroix;
	private static ImageIcon iconBar;
	private static ImageIcon iconFleche;
	private static ImageIcon iconFlecheInv;
	
	public static final int CROIX = 0;
	public static final int BAR = 1;
	public static final int FLECHE = 2;
	public static final int INV_FLECHE = 3;
	
	{
		List<String> pathImg = new ArrayList<String>();
		pathImg.add("res/images/bar.png");
		pathImg.add("res/images/fleche.png");
		pathImg.add("res/images/fleche2.png");
		pathImg.add("res/images/croix.png");
		
		try {
			ExtractTemp copy = new ExtractTemp();
			copy.copy(pathImg);
		}catch(IOException | URISyntaxException e) {e.printStackTrace();}
		
		RondButton.iconBar = new ImageIcon(Temp.getTempDir() + "/res/images/bar.png");
		RondButton.iconFleche = new ImageIcon(Temp.getTempDir() + "/res/images/fleche.png");
		RondButton.iconFlecheInv = new ImageIcon(Temp.getTempDir() + "/res/images/fleche2.png");
		
		RondButton.iconCroix = new ImageIcon(Temp.getTempDir() + "/res/images/croix.png");
	}
	
	public RondButton(boolean[] isE, TopPanel top) {
		RondButton.top = top;
		isEnable = isE;
	}
		
	public RondButton(Color background) {
		super();
		this.background = background;
		this.setFocusable(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
			
	}	
	
	private RondButton(Color background, Frame fen, int ids) {
		super();
		RondButton.fen = fen;
		this.setSize(16, 16);
		this.id = ids;
		rondButton[ids] = this;
		
		this.background = background;
		this.setFocusable(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		
		this.setEnabled(isEnable[ids]);
		
		if(isFirst) {
			isFirst = false;
			new Thread(active()).start();
		}
		
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(fen.isActive()) {
					switch(id) {
						case CROIX:
							/*for(FrameListener frameListener : fen.frameManager.getListeners())
								if(frameListener != null) frameListener.frameClosing(null);*/
							
							UserSystem.exit(0);
							break;
						case BAR:
							fen.setState(JFrame.ICONIFIED);
							
							/*for(FrameListener frameListener : fen.frameManager.getListeners())
								if(frameListener != null) frameListener.frameMinimalized(null);*/
							break;
						case FLECHE:
							Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
							Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
							
							int taskBarHeight = scrnSize.height - winSize.height;
							
							fen.savePosition();
							
							fen.setSize(scrnSize.width, scrnSize.height - taskBarHeight);
							fen.setLocationRelativeTo(null);
							fen.setMaximize(true);
							
							top.fullScreen = true;
							id = INV_FLECHE;
							/*for(FrameListener frameListener : fen.frameManager.getListeners())
								if(frameListener != null) frameListener.frameResizing(null);*/
							break;
						case INV_FLECHE:
							Dimension defaultSize = new Dimension();
							fen.setSize(defaultSize);
							
							fen.setLocation(fen.getSavedPosition());
							fen.setMaximize(false);
							top.fullScreen = false;
							id = FLECHE;
							
						/*	for(FrameListener frameListener : fen.frameManager.getListeners())
								if(frameListener != null) frameListener.frameResizing(null);*/
							break;
					}
					
				}
				
			}
		});
		
	}
	
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int h = getHeight();
			int w = getWidth();
			model = getModel();
			
			isR = !fen.isActive() && isRollover() ? true : false;
			if(!fen.isActive() && !model.isRollover() && !isR || !model.isEnabled()) {
				GP = new GradientPaint(0, 0, new Color(180, 180, 180), 0, h, new Color(180, 180, 180), true);
			}else {
				if(isRollover() && rondButton[index] != this) {
					for(int i = 0; i < rondButton.length; i++) {
						switch(rondButton[i].id) {
							case CROIX:
								if(isEnable[i]) {
									rondButton[i].setIcon(iconCroix);
								}
								break;
							case BAR:
								if(isEnable[i]) {
									rondButton[i].setIcon(iconBar);
								}
								break;
							case FLECHE:
								if(isEnable[i]) {
									rondButton[i].setIcon(iconFleche);
								}
								break;
							case INV_FLECHE:
								if(isEnable[i]) {
									rondButton[i].setIcon(iconFlecheInv);
								}
								break;
						}
					}
				}else {
					if(model.isRollover()) {
						for(int i = 0; i < rondButton.length; i++) {
							switch(rondButton[i].id) {
							case 0:
								if(isEnable[i]) {
									rondButton[i].setIcon(iconCroix);
								}
								break;
							case 1:
								if(isEnable[i]) {
									rondButton[i].setIcon(iconBar);
								}
								break;
							case 2:
								if(isEnable[i]) {
									rondButton[i].setIcon(iconFleche);
								}
								break;
							case 3:
								if(isEnable[i]) {
									rondButton[i].setIcon(iconFlecheInv);
								}
								break;
							}
							isRollover[getRondButtonPos(this)] = true;
							index = getRondButtonPos(this);
						}
					}else {
						GP = new GradientPaint(0, 0, background, 0, h, background, true);
						for(int i = 0; i < rondButton.length; i++) {
							rondButton[i].setIcon(null);
						}
						isRollover[index] = false;
					}
					
				}
				
			}
			g2d.setPaint(GP);
			
			GP = new GradientPaint(0, 0, background, 0, h, background, true);
			
	 
			Ellipse2D.Float r2d = new Ellipse2D.Float(0, 0, w - 1, h - 1);
			Shape clip = g2d.getClip();
			g2d.clip(r2d);
			g2d.fillOval(0, 0, w, h);
			g2d.setClip(clip);
			g2d.drawOval(0, 0, w - 1, h - 1);
			g2d.drawOval(1, 1, w - 3, h - 3);
			g2d.dispose();
	 
			super.paintComponent(g);
		}

		
		private static boolean isRollover() {
			int id = 0;
			for(int i = 0; i < isRollover.length; i++) {
				if(!isRollover[i]) {
					id++;
				}
			}
			if(id == isRollover.length) {
				return false;
			}else {
				return true;
			}
		}
	 
		private static int getRondButtonPos(RondButton rb) {
			for(int i = 0; i < rondButton.length; i++) {
				if(rondButton[i].equals(rb)) {
					return i;
				}
			}
			return 0;
		}
	
		public JPanel getButtonsPanel(Frame fen, Properties config){
			panel = new JPanel();
			panel.setOpaque(false);
			RondButton croix = new RondButton(Color.decode("#ED6A5F"), fen, RondButton.CROIX);
			RondButton bar = new RondButton(Color.decode("#F6C351"), fen, RondButton.BAR);
			RondButton fleche = new RondButton(Color.decode("#62C554"), fen, RondButton.FLECHE);
			croix.setPreferredSize(new Dimension(16, 16));
			bar.setPreferredSize(new Dimension(16, 16));
			fleche.setPreferredSize(new Dimension(16, 16));
	 
			panel.add(croix);
			panel.add(bar);
			panel.add(fleche);
			return panel;
	 
		}
		
		@Deprecated
		public void setEnable(boolean[] isE) {
			isEnable = isE;
			
			rondButton[0].setEnabled(isE[0]);
			rondButton[1].setEnabled(isE[1]);
			rondButton[2].setEnabled(isE[2]);
		}
		
		private static Runnable active() {
			return new Runnable() {
				@Override
				public void run() {
					while(true) {
						while(!fen.isActive()) {
							if(!fen.isActive() && rondButton[2] != null) {
								for(int i = 0; i < rondButton.length; i++) {
									rondButton[i].repaint();
								}
								break;
							}	
						}
					}
				}
			};
			
		}
		
}
