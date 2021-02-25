package net.argus.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Icon {
	
	public static ImageIcon getIcon(String iconPath) {
		try {return getIcon(new ImageIcon(ImageIO.read(new File(iconPath))));}
		catch(IOException e) {return null;}
	}
	
	public static ImageIcon getIcon(ImageIcon icon) {return getIcon(icon, 32, 32);}
	
	public static ImageIcon getIcon(ImageIcon icon, int w, int h) {
		return new ImageIcon(icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}
	
	public static ImageIcon getIcon(ImageIcon icon, int width) {
		
		int w = width, h;
		int imgWidth = icon.getIconWidth(), imgHeight = icon.getIconHeight();
		h = (w * imgHeight) / imgWidth;
		
		Image dimg = icon.getImage().getScaledInstance(w, h, Image.SCALE_REPLICATE);
		return new ImageIcon(dimg);
	}
	
	public static ImageIcon getIconResize(String iconPath, Dimension frameSize) {
		BufferedImage img = null;
		int fenWidth = frameSize.width, fenHeight = frameSize.height;
		int imgWidth, imgHeight;
		
		try {img = ImageIO.read(new File(iconPath));}
		catch (IOException e) {e.printStackTrace();}
		
		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		
		int temp = (fenWidth * imgHeight) / imgWidth;
		boolean isW = false;
		if(temp < fenHeight) {temp = (fenHeight * imgWidth) / imgHeight; isW = true;}
		
		Image dimg;
		if(isW) {dimg = img.getScaledInstance(temp, fenHeight, Image.SCALE_SMOOTH);}
		else {dimg = img.getScaledInstance(fenWidth, temp, Image.SCALE_SMOOTH);}
		
		return new ImageIcon(dimg);
	}
	
	public static ImageIcon getIcon(String iconPath, int height, String nul) {
		BufferedImage img = null;
		try {img = ImageIO.read(new File(iconPath));}
		catch (IOException e) {e.printStackTrace();}
		int w, h = height;
		int imgWidth = img.getWidth(), imgHeight = img.getHeight();
		w = (h * imgWidth) / imgHeight;
		
		Image dimg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}
	
	public static ImageIcon getIcon(String iconPath, int width) {
		return getIcon(new ImageIcon(iconPath), width);
	}
	
	public static ImageIcon getIcon(String iconPath, int width, int height) {
		BufferedImage img = null;
		try {img = ImageIO.read(new File(iconPath));}
		catch (IOException e) {e.printStackTrace();}
		
		Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}
	
	public static void main(String[] args) {
		getIconResize("C:\\Users\\Utilisateur\\Desktop\\School\\res\\background.jpg", new Dimension(1200, 1080));
	}

}
