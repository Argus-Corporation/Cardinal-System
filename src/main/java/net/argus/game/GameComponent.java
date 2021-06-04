package net.argus.game;

import java.awt.Graphics;
import java.util.Enumeration;

import javax.swing.JComponent;

public class GameComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1992220358549919178L;
	
	@Override
	protected void paintComponent(Graphics g) {		
		Enumeration<Pixel> pix = Matrix.getPixels();

		while(pix.hasMoreElements()) {
			Pixel p = pix.nextElement();
			g.setColor(p.getColor());
			g.drawRect(p.getPoint().x, p.getPoint().y, 1, 1);
		}
	}

}
