package net.argus.gui.bubble;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.argus.gui.Look;
import net.argus.util.DoubleStock;

public class BubblePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5517196913594716389L;
	
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	
	private static final int CENTER = -1;
	
	private int indexRightColor;
	private int indexLeftColor;
	
	private List<DoubleStock<Bubble, Integer>> bubbles = new ArrayList<DoubleStock<Bubble, Integer>>();
	
	public Bubble addBubble(int pos, Bubble b) {
		setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + b.getHeight() + 10));
		bubbles.add(new DoubleStock<Bubble, Integer>(b, pos));
		
		repaint();
		
		return b;
	}
	
	public Bubble addInfoBubble(String text) {
		return addBubble(CENTER, new InfoBubble(text));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int y = getHeight();
        for(int i = bubbles.size()-1; i >= 0; i--) {
        	DoubleStock<Bubble, Integer> stock = bubbles.get(i);
        	Bubble bubble = stock.getFirst();
        	int dir = stock.getSecond();
        	
        	bubble.setParent(this);
        	
        	y -= bubble.getTotalHeight() + 5;
        	int x = 10;
        	if(dir == RIGHT)
        		x = getWidth() - stock.getFirst().getWidth() - 10;
        
        	bubble.draw(g2d, x, y, dir, dir==RIGHT?BubbleRegisterColor.getRight(indexRightColor):BubbleRegisterColor.getLeft(indexLeftColor));
        }

	}
	
	public void setIndexColorRight(int index) {
		this.indexRightColor = index;
	}
	
	public void setIndexColorLeft(int index) {
		this.indexLeftColor = index;
	}
		
	public void clear() {bubbles.clear(); repaint();}
	
	public static void main(String[] args) {
		//InitializationSystem.initSystem(args, false);
		Look.chageLook(UIManager.getSystemLookAndFeelClassName());
		JFrame fen = new JFrame("Title");
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setSize(700, 700);
		fen.setLocationRelativeTo(null);
		
		BubbleScrollPane bp = new BubbleScrollPane();
		bp.setBackground(new Color(0xff1c1c1e));

		bp.addBubble(RIGHT, "coucou");
		bp.addBubble(RIGHT, "coucou", "Olario 35");
	//	bp.addInfoBubble("Olario join server");
		bp.addBubble(LEFT, "Couqsc:lkfjshfkjsehfdkjhsdfkjhsdkjfhs djf dskf sd fsd f sd g dfg  dfgdfgcou  l", "lkrgjlkdmflkgmldfgf");
		bp.addBubble(RIGHT, "Cousfsdcou");
		//bp.addBubble(RIGHT, new Bubble(new JLabel("lol")));
		
		//JScrollPane p = new JScrollPane(bp);
		fen.add(bp);
		
		fen.setVisible(true);
		
		
		while(true) {
			@SuppressWarnings("resource")
			Scanner s = new Scanner(System.in);
			bp.addBubble(RIGHT, new Bubble(s.nextLine()));
		}
		
		
	}

}
