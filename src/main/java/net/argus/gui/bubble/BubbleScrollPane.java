package net.argus.gui.bubble;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BubbleScrollPane extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6425927703555314394L;
	
	private BubblePanel pan = new BubblePanel();
	private boolean newBubble;
	private int oldMax = -1;
	
	public BubbleScrollPane() {
		setViewportView(pan);	
		
		getViewport().addChangeListener(getChangeListener());
		getVerticalScrollBar().setUnitIncrement(10);
	}
	
	public Bubble addBubble(int pos, Bubble b) {
		pan.addBubble(pos, b);
		setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + b.getHeight() + 10));
		getViewport().revalidate();
		
		newBubble = true;
		
		return b;
	}
	
	public Bubble addBubble(int pos, String text) {
		return addBubble(pos, new Bubble(text));
	}
	
	public Bubble addBubble(int pos, String text, String info) {
		Bubble b = new Bubble(text);
		b.setInfo(info);
		return addBubble(pos, b);
	}
	
	public void addInfoBubble(String text) {
		Bubble b = pan.addInfoBubble(text);
		setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + b.getHeight() + 10));
		getViewport().revalidate();
		
		newBubble = true;
	}
	
	private ChangeListener getChangeListener() {
		return (ChangeEvent e) -> {
			if(newBubble)
				SwingUtilities.invokeLater(() -> {
					JScrollBar vert = getVerticalScrollBar();
					Rectangle rect = getViewport().getViewRect();
					
					if(oldMax == -1 || rect.y + rect.height == oldMax)
						vert.setValue(vert.getMaximum());
					
					if(vert.getValue() > 0)
					oldMax = vert.getMaximum();
				});
		};
	}
	
	public void setIndexColorRight(int index) {
		pan.setIndexColorRight(index);
	}
	
	public void setIndexColorLeft(int index) {
		pan.setIndexColorLeft(index);
	}
	
	public void clear() {pan.clear();}
	
	@Override
	public void setBackground(Color bg) {
		if(pan != null)
			pan.setBackground(bg);
	}

}
