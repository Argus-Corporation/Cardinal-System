package net.argus.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import net.argus.file.Properties;
import net.argus.graphic.GraphicsListener;
import net.argus.graphic.Vector2;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.SClass;

public class GraphicsPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4924292783008457853L;
	
	private List<Vector2> vactors = new ArrayList<Vector2>();
	private GraphicsListener gl;
	
	private Color color;
	private int x, y;
	
	
	public GraphicsPanel(GraphicsListener gl) {this.gl = gl;}
	
	public GraphicsPanel() {}
	
	public void addPoint(Vector2 v) {
		vactors.add(v);
		getParent().repaint();
	}
	
	public void setColor(Color color) {this.color = color;}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color!=null?color:g2.getColor());
		
		x = getParent().getWidth() / 2;
		y = getParent().getHeight() / 2;
		
		if(gl != null) 
			for(int i = -10; i < 10; i += 1)
				g2.drawLine(x + i, y + gl.fonction(i), x + i + 1, y + gl.fonction(i + 1));
		
		else
			for(int i = 0; i < vactors.size() - 1; i++)
				g2.drawLine((int)vactors.get(i).getX(), (int)vactors.get(i).getY(), (int)vactors.get(i + 1).getX(), (int)vactors.get(i + 1).getY());	
	}
	
	public static void main(String[] args) throws InterruptedException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		Properties config = new Properties("config", "bin");
		Frame fen = new Frame("test", SClass.getPath("res/favIcon32x32.png"), new boolean[] {true, true, true}, config);
		fen.setIcon(SClass.getPath("res/favIcon16x16.png"));
		
		GraphicsPanel gp = new GraphicsPanel(new GraphicsListener() {
			
			@Override
			public int fonction(int x) {
				return (x*2) * x^2;
			}
		});
		
		fen.add(gp);
		
		fen.setVisible(true);
		
	}
}
