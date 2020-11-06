package net.argus.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Sprite {
	
	public static Image[] getSprite(ImageIcon source, Dimension spriteDim) {
		return getSprite(source, new Dimension(), spriteDim);
	}
	
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
	
	public static void main(String[] args) {
		JFrame fen = new JFrame();
		JPanel pan = new JPanel();
		pan.setBackground(new Color(64, 64, 64));
		pan.setLayout(null);
		fen.add(pan);
		Image[] sprite = getSprite(new ImageIcon("D:\\Django\\Document 1\\Java\\Project\\Save\\res\\tiles_spritesheet.png"), new Dimension(2, 2), new Dimension(70, 70));
		
		for(int i = 0, x = 0, y = 0; i < sprite.length; i++) {
			if(x >= new ImageIcon("D:\\Django\\Document 1\\Java\\Project\\Save\\res\\tiles_spritesheet.png").getIconWidth()) {
				x = 0;
				y += 70;
			}
			JLabel lab = new JLabel(new ImageIcon(sprite[i]));
			lab.setBounds(x, y, 70, 70);
			pan.add(lab);
			
			x += 70;
		}
		
		fen.setSize(500, 500);
		fen.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
		fen.setVisible(true);
	}
}
