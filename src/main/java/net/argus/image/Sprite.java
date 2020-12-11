package net.argus.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.argus.gui.Frame;

public class Sprite {
	
	private List<SpriteImage> imgs = new ArrayList<SpriteImage>();
	private String path;
	
	public Sprite(String path) {
		this.path = path;
		init();
	}
	
	public void init() {
		BufferedImage img = null;
		
		try {img = ImageIO.read(new File(path));
		}catch(IOException e) {e.printStackTrace();}
		
		int w = img.getWidth();
		int h = img.getHeight();
		int[] pixels = new int[w * h];
		img.getRGB(0, 0, w, h, pixels, 0, w);
		
		Point start = null;
		@SuppressWarnings("unused")
		Point lineEnd;
		Point end;
		
		for(int i = 0, x = 0, y = 0; i < w * h; i++, y += x == w-1 ? 1 : 0, x = x < w-1 ? x + 1: 0) {
			if(!getColor(pixels, i).equals(new Color(1.0f, 0.0f, 1.0f, 1.0f))
					&& getColor(pixels, i - 1).equals(new Color(1.0f, 0.0f, 1.0f, 1.0f))
					&& getColor(pixels, i - w).equals(new Color(1.0f, 0.0f, 1.0f, 1.0f))) {
				start = new Point(x, y);
			}
			
			if(!getColor(pixels, i).equals(new Color(1.0f, 0.0f, 1.0f, 1.0f))
					&& getColor(pixels, i + 1).equals(new Color(1.0f, 0.0f, 1.0f, 1.0f))
					&& getColor(pixels, i - w).equals(new Color(1.0f, 0.0f, 1.0f, 1.0f))) {
				lineEnd = new Point(x, y);
				
				for(int j = i, y0 = y; j < w * h; j += w, y0++) {
					if(!getColor(pixels, j).equals(new Color(1.0f, 0.0f, 1.0f, 1.0f))
							&& getColor(pixels, j + 1).equals(new Color(1.0f, 0.0f, 1.0f, 1.0f))
							&& getColor(pixels, j + w).equals(new Color(1.0f, 0.0f, 1.0f, 1.0f))) {
						end = new Point(x, y0);
						
						imgs.add(new SpriteImage(img, start, end));
						
						j = w * h;
					}
				}
			}	
		}
	}
	
	private Color getColor(int[] pix, int i) {
		int p = pix[i<0?0:i];
		int r = 0xff & (p >> 16);
		int g = 0xff & (p >> 8);
		int b = 0xff & (p);
		return new Color(r, g, b, 255);
	}
	
	public SpriteImage[] getSpriteImage() {
		return (SpriteImage[]) imgs.toArray(new SpriteImage[imgs.size()]);
	}
	
	@Deprecated
	public static Image[] getSprite(ImageIcon source, Dimension spriteDim) {return getSprite(source, new Dimension(), spriteDim);}
	
	@Deprecated
	public static Image[] getSprite(ImageIcon source, Dimension offset, Dimension spriteDim) {
		int widthImg = source.getIconWidth() / spriteDim.width, heightImg = source.getIconHeight() / spriteDim.height;
		
		int widthSprit = spriteDim.width, heightSprit = spriteDim.height;
		
		Image[] imgs = new Image[widthImg * heightImg];
		
		for(int i = 0, x = 0, y = 0; i < imgs.length; i++) {
			if(x >= source.getIconWidth()) {
				x = 0;
				y += heightSprit + offset.height;
			}
			imgs[i] = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(source.getImage().getSource(), new CropImageFilter(x, y, widthSprit, heightSprit)));
			x += widthSprit + offset.width;
		}
		return imgs;
	}
	
	public static Sprite s;
	
	public static void main(String[] args) {
		JFrame fen = new JFrame();
		SPanel pan = new SPanel();
		pan.setBackground(new Color(64, 64, 64));
		pan.setLayout(null);
		fen.setContentPane(pan);
		
		s = new Sprite("D:\\Django\\Document 1\\Game\\Among Us\\Platformer\\resources\\player\\player.png");
		
		fen.setSize(500, 500);
		fen.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
		fen.setVisible(true);
	}
	
	static class SPanel extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -7838440476309512138L;

		@Override
		protected void paintComponent(Graphics g) {
			SpriteImage[] imgs = s.getSpriteImage();
			imgs[2].render(g, getWidth() / 2, getHeight() / 2);
		}
		
	}
}
