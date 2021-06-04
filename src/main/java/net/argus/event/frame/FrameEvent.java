package net.argus.event.frame;

import java.awt.Dimension;

public class FrameEvent {
	
	private Object source;
	private Dimension size;
	
	public FrameEvent(Object source, Dimension size) {
		this.source = source;
		this.size = size;
	}
	
	public void setSource(Object source) {this.source = source;}
	
	public Object getSource() {return source;}
	public Dimension getSize() {return size;}

}
