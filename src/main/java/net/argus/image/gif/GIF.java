package net.argus.image.gif;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class GIF {
	
	private BufferedImage[] imgs;
	private int width = 1, height = 1;
	
	private boolean loop = true;
	
	public GIF(BufferedImage[] imgs) {
		this.imgs = imgs;
	}
	
	public GIF(GIF gif) {
		imgs = gif.imgs;
		width = gif.width;
		height = gif.height;
	}
	
	public int length() {return imgs.length;}
	
	public BufferedImage getImageAt(int index) {return imgs[index];}
	public BufferedImage[] getImages() {return imgs;}
	public Image getImage(int index) {return imgs[index].getScaledInstance(width, height, 1);}
	
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	
	public boolean isLoop() {return loop;}
	
	public GIF setWidth(int width) {this.width = width; return this;}
	public GIF setHeight(int height) {this.height = height; return this;}
	
	public GIF setLoop(boolean loop) {this.loop = loop; return this;}

}
