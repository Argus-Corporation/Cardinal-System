package net.argus.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import net.argus.file.FileManager;
import net.argus.file.Properties;
import net.argus.graphic.GraphicsListener;
import net.argus.graphic.Vector2;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.Display;

public class GraphicsPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4924292783008457853L;
	
	private List<Vector2> vactors = new ArrayList<Vector2>();
	private GraphicsListener graphicsListener;
	
	private int lineSize = 2;
	private Color color;
	private int x, y;
	
	
	public GraphicsPanel(GraphicsListener graphicsListener) {this.graphicsListener = graphicsListener;}
	
	public GraphicsPanel() {}
	
	/**
	 * Ajoute un vecteur au graphique
	 * @param vector
	 */
	public void addPoint(Vector2 vector) {
		vactors.add(vector);
		getParent().repaint();
	}
	
	/****GETTERS****/
	public GraphicsListener getGraphicsListener() {return graphicsListener;}
	public Color getColor() {return color;}
	public int getLineSize() {return lineSize;}
	
	/****SETTERS****/
	public void setColor(Color color) {this.color = color;}
	public void setLineSize(int lineSize) {this.lineSize = lineSize;}
	public void addGraphicsListener(GraphicsListener graphicsListener) {this.graphicsListener = graphicsListener;}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		x = getParent().getWidth() / 2;
		y = getParent().getHeight() / 2;
		
		g2.fillRect(0, y, getParent().getWidth(), 2);
		g2.fillRect(x, 0, 2, getParent().getHeight());
		
		g2.setColor(color!=null?color:g2.getColor());
		
		g2.setStroke(new BasicStroke(lineSize));
		
		if(graphicsListener != null) 
			for(float i = -10000; i < 10000; i += 1f)
				g2.draw(new Line2D.Float(x + i, y + graphicsListener.fonction(i), x + i + 1, y + graphicsListener.fonction(i + 1)));
		
		else
			for(int i = 0; i < vactors.size() - 1; i++)
				g2.draw(new Line2D.Float(vactors.get(i).getX(), vactors.get(i).getY(), vactors.get(i + 1).getX(), vactors.get(i + 1).getY()));
	}
	
	public static void main(String[] args) throws InterruptedException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		Properties config = new Properties("config", "bin");
		Frame fen = new Frame("Graphics", FileManager.getPath("res/favIcon32x32.png"), new boolean[] {true, true, true}, config);
		fen.setIcon(FileManager.getPath("res/favIcon16x16.png"));
		
		GraphicsPanel gp = new GraphicsPanel(new GraphicsListener() {
			
			@Override
			public float fonction(float x) {
				return 2 * x * x* x;
			}
		});
		gp.setColor(Color.BLACK);
		fen.add(gp);
		
		new Splash("test", Icon.getIcon(FileManager.getPath("res/logo.png"), Display.getWidhtDisplay() - 20), fen, 2000, config);
		
		fen.setVisible(true);
		
	}
}
