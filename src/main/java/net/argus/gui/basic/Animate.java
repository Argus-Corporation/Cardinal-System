package net.argus.gui.basic;

import java.awt.Graphics;

import javax.swing.JComponent;

public abstract class Animate extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5111470004386843137L;

	public abstract void pre();
	
	public abstract void animate();
	
	public abstract void post();
	
	public abstract void paintComponent(Graphics g);
	
	public void start() {
		pre();
		animate();
		post();
	}

}
