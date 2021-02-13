package net.argus.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import net.argus.system.InitializationSystem;
@Deprecated
public class BubblePanel {
	
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	
	private static List<Bubble> leftBubble = new ArrayList<Bubble>();
	private static List<Bubble> rightBubble = new ArrayList<Bubble>();
	
	public void addBubble(int pos, Bubble b) {
		switch(pos) {
			case RIGHT:
				rightBubble.add(b);
				break;
			case LEFT:
				leftBubble.add(b);
				break;
			default:
				rightBubble.add(b);
				break;
		}
	}
	
	public Panel getComponentLeft() {
		Panel pan = new Panel();
		Panel mainLeft = new Panel();
		Panel mainRight = new Panel();
		Panel left = new Panel();
		Panel right = new Panel();
		
		pan.setLayout(new BorderLayout());
		mainLeft.setLayout(new BorderLayout());
		mainRight.setLayout(new BorderLayout());
		
		BoxLayout leftLayout = new BoxLayout(left, BoxLayout.Y_AXIS);
		BoxLayout rightLayout = new BoxLayout(right, BoxLayout.Y_AXIS);
		
		left.setLayout(leftLayout);
		right.setLayout(rightLayout);
		
		
		for(int i = 0; i < leftBubble.size(); i++) {
			left.add(leftBubble.get(i));
		}
		
		
		for(int i = 0; i < rightBubble.size(); i++) {
			right.add(rightBubble.get(i));
		}
		
		mainRight.add(BorderLayout.NORTH, right);
		mainLeft.add(BorderLayout.NORTH, left);
	
		
		return mainLeft;
	}
	
	public Panel getComponentRight() {
		Panel pan = new Panel();
		Panel mainLeft = new Panel();
		Panel mainRight = new Panel();
		Panel left = new Panel();
		Panel right = new Panel();
		
		pan.setLayout(new BorderLayout());
		mainLeft.setLayout(new BorderLayout());
		mainRight.setLayout(new BorderLayout());
		
		BoxLayout leftLayout = new BoxLayout(left, BoxLayout.Y_AXIS);
		BoxLayout rightLayout = new BoxLayout(right, BoxLayout.Y_AXIS);
		
		left.setLayout(leftLayout);
		right.setLayout(rightLayout);
		
		
		for(int i = 0; i < leftBubble.size(); i++) {
			left.add(leftBubble.get(i));
		}
		
		
		for(int i = 0; i < rightBubble.size(); i++) {
			right.add(rightBubble.get(i));
		}
		
		mainRight.add(BorderLayout.NORTH, right);
		mainLeft.add(BorderLayout.NORTH, left);
	
		
		return mainRight;
	}
	
	public static void main(String[] args) {
		InitializationSystem.initSystem(args, false);
		
		JFrame fen = new JFrame("Title");
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setSize(700, 700);
		fen.setLocationRelativeTo(null);

		BubblePanel bp = new BubblePanel();
		bp.addBubble(RIGHT, new Bubble(new Label("test", false)));
		bp.addBubble(LEFT, new Bubble(new Label("test 1", false)));
		bp.addBubble(LEFT, new Bubble(new Label("test 2", false)));
		bp.addBubble(RIGHT, new Bubble(new Label("test  3", false)));
		
		fen.add(BorderLayout.CENTER, leftBubble.get(0));
		fen.add(BorderLayout.EAST, bp.getComponentRight());
		
		fen.setVisible(true);
		
		
	}

}
