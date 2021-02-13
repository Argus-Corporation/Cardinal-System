package net.argus.gui.gif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

import net.argus.image.gif.GIF;
import net.argus.util.ListenerManager;
import net.argus.util.ThreadManager;

public class GIFComponent extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 375963232673349759L;
	
	private ListenerManager<GIFListener> gifManager = new ListenerManager<GIFListener>();
	
	private GIF gif;
	private Image currentImage;
	
	private int index;
	
	private int fps = 25;
	private int numLoop;
	
	private boolean running, destroy;
	
	private ThreadManager gifThread;
	
	public GIFComponent(GIF gif) {
		this.gif = gif;
		this.setPreferredSize(new Dimension(gif.getWidth(), gif.getHeight()));
		gifThread = new ThreadManager("gif");
	}
	
	public void fusion(GIFComponent comp) {
		this.gif = comp.gif;
		this.fps = comp.fps;
		this.running = comp.running;
		this.gif.setLoop(comp.getGIF().isLoop());
		this.setPreferredSize(comp.getPreferredSize());
	}
	
	public void start() {
		running = true;
		gifThread.start(loop());
	}
	
	public void stop() {running = false;}
	
	public void destroy() {
		destroy = true;
	}
	
	private Runnable loop() {
		return () -> {
			index = 0;
			while(running) {
				for(index = 0; index < gif.length(); index ++) {
					if(!destroy) {
						currentImage = gif.getImage(index);
						repaint();
						
						ThreadManager.sleep(1000 / fps);
					}else {
						System.out.println("hello");
						stop();
						break;
					}
				}
				if(gif.isLoop())
					numLoop++;
				else
					ThreadManager.stop(Thread.currentThread());
			}

			for(GIFListener mana : gifManager)
				mana.stop(new GIFEvent(this, numLoop, index, currentImage));
			
			ThreadManager.stop(Thread.currentThread());
		};
	}
	
	public Image getCurrentImage() {return currentImage;}
	public int getCurrentIndex() {return index;}
	
	public GIF getGIF() {return gif;}
	
	public void addGIFListener(GIFListener listener) {gifManager.addListener(listener);}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		gif.setWidth(preferredSize.width);
		gif.setHeight(preferredSize.height);
		
		super.setPreferredSize(preferredSize);
	}
	
	public void setGIF(GIF gif) {
		gif.setWidth(this.gif.getWidth());
		gif.setHeight(this.gif.getHeight());
		this.gif = gif;
	}
	
	public void setFramePerSecond(int fps) {this.fps = fps;}
	public void setLoop(boolean loop) {this.gif.setLoop(loop);}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(currentImage, 0, 0, null);
		super.paintComponent(g);
	}
	
	@Override
	public void paintComponents(Graphics g) {
		g.drawImage(currentImage, 0, 0, null);
		g.setFont(new Font("roboto", 0, 20));
		g.setColor(Color.RED);
		g.drawString("hello", 100, 100);
		//super.paintComponents(g);
	}

}
