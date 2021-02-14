package net.argus.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.UIManager;

import net.argus.util.ChangeListener;
import net.argus.util.ListenerManager;

public class TextField extends JTextField implements Element, GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 907390325134914133L;
	
	private boolean placeholder;
	
	private ListenerManager<ChangeListener> changeManager = new ListenerManager<ChangeListener>();
	
	private List<String> oldData = new ArrayList<String>();
	private int dataIndex;
	private boolean touch;
	private boolean error;
	
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
		FontRegistry.addElement(this);
		
		nameTypes.add(nameType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
		this.index = element.size() - 1;
	}
	
	public void setError() {
		error = true;
		setBackground(UIManager.getColor("TextField.error"));
	}
	
	public void unError() {
		error = false;
		setBackground(UIManager.getColor("TextField.background"));
	}
	
	public boolean isError() {return error;}
	
	public void setText(String text) {
		super.setText(text);
		for(ChangeListener l : changeManager)
			l.valueChanged(text);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		if(error && !enabled && isEnabled()) {
			int sup = 50;
			Color bc = getBackground();
			int r, g, b;
			r = bc.getRed() - sup;
			g = bc.getBlue() - sup;
			b = bc.getGreen() - sup;
			if(r < 0)
				r = 0;
			if(g < 0)
				g = 0;
			if(b < 0)
				b = 0;
			Color nbc = new Color(r, g, b);
			setBackground(nbc);
		}else if(error && enabled && !isEnabled())
			setError();
			
		super.setEnabled(enabled);
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
				
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A)
					selectAll();
					
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
	
	public void addChangeListener(ChangeListener listener) {changeManager.addListener(listener);}

	@Override
	public void setText() {}

	@Override
	public String getElementName() {
		return "TextField";
	}
	
	@Override
	public void setFont(Font f) {
		super.setFont(f);
	}

}
