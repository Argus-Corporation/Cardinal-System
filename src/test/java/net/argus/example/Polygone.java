package net.argus.example;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Polygone extends Component {
    private static final long serialVersionUID = 1L;

    protected final Color color;

    protected final int[] x;
    protected final int[] y;

    public Polygone(int[] x, int[] y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void paint(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	
        g2.setColor(color);
       
        int r = 10;
        for(int i = 0; i < x.length; i++) {
        	g2.drawOval(x[i] - r/2, y[i] - r/2, r, r);
        }
       //g2.fillPolygon(x, y, x.length);
    }
}