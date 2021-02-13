package net.argus.gui.gif;

import java.awt.Image;

public class GIFEvent {
	
	private Object parent;
	
	private int numLoop, index;
	private Image currenImage;
	
	public GIFEvent(Object parent, int numLoop, int index, Image currentImage) {
		this.parent = parent;
		this.numLoop = numLoop;
		this.index = index;
		this.currenImage = currentImage;
	}
	
	public Object getParent() {return parent;}
	
	public int getNumberLoop() {return numLoop;}
	public int getIndex() {return index;}
	
	public Image getCurrentImage() {return currenImage;}

}
