package net.argus.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.UIManager;

public class TextField extends JTextField implements Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 907390325134914133L;
	
	private boolean placeholder;
	
	private List<String> oldData = new ArrayList<String>();
	private int dataIndex;
	private boolean touch;
	
	private static final String nameType = "textfield";
	private static final boolean isBack = true;
	private static final boolean isFore = true;
	private static final boolean isFont = true;
	
	private int index;
	
	
	public TextField(int size) {
		super(size);
		common();
	}
	
	public TextField(int size, boolean nul) {
		super(size);
		this.addKeyListener(getDefaultKeyListener());
		common();
	}
	
	private void common() {
		nameTypes.add(nameType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
		this.index = element.size() - 1;
	}
	
	public String getText() {return !placeholder?super.getText():null;}
	
	public void copyData() {oldData.add(getText());}
	
	public KeyListener getDefaultKeyListener() {
		return new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_UP && oldData.size()!=0) {
					if(!touch) dataIndex = dataIndex>0?dataIndex-1:dataIndex;
					setText(oldData.get(dataIndex));
					touch = false;
				}else if(e.getKeyCode() == KeyEvent.VK_DOWN && oldData.size()!=0) {
					dataIndex = dataIndex<oldData.size()-1?dataIndex+1:dataIndex;
					
					setText(oldData.get(dataIndex));
					touch = false;
				}else {
					dataIndex = oldData.size()>0?oldData.size():0;
					touch = true;
				}	
			}
			public void keyReleased(KeyEvent e) {}
			
		};
	}
	
	public void setPlaceholder(String placeholder) {
		isFores.set(index, false);
		
		if(UIManager.getColor("TextField.placeholder") != null) this.setForeground(UIManager.getColor("TextField.placeholder"));
		else this.setForeground(new Color(128, 128, 128));
		
		this.placeholder = true;
		this.setText(placeholder);
		
		TextField tf = this;
		this.addMouseListener(new MouseListener() {
		    public void mouseReleased(MouseEvent e) {}         
		    public void mousePressed(MouseEvent e) {}          
		    public void mouseExited(MouseEvent e) {}           
		    public void mouseEntered(MouseEvent e) {}          
		    public void mouseClicked(MouseEvent e) {
		    	tf.setText("");
		    	
		    	tf.placeholder = false;
		    	
				if(UIManager.getColor("TextField.foreground") != null) tf.setForeground(UIManager.getColor("TextField.foreground"));
				else tf.setForeground(Color.BLACK);
		    	
		    	tf.removeMouseListener(this);
		    	isFores.set(index, true);
		    }
		});
	}

}
