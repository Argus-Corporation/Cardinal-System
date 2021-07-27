package net.argus.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.argus.event.change.ChangeEvent;
import net.argus.event.change.ChangeListener;
import net.argus.event.change.EventChange;
import net.argus.lang.Lang;
import net.argus.lang.LangManager;
import net.argus.lang.LangRegister;
import net.argus.lang.LangType;

public class TextField extends JTextField implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 907390325134914133L;
	
	private boolean useLang;
		
	private List<String> oldData = new ArrayList<String>();
	private int dataIndex;
	private boolean touch;
	private boolean error;
	
	private String placeholder;
	private String name;
	
	private EventChange event = new EventChange();
	
	public TextField(int size) {
		this(size, null);
	}
	
	public TextField(int size, String name) {
		super(size);
		this.name = name;
		
		if(name != null && !name.equals(""))
			useLang = true;
		
		LangRegister.addElementLanguage(this);
		FontRegister.addElement(this);
		BackgoundRegister.addElement(this);
		ForegroundRegiter.addElement(this);
				
		setText();
	}
	
	public void setError() {
		error = true;
		Color color = UIManager.getColor("TextField.error");
		if(color != null)
			setBackground(color);
		else
			setBackground(new Color(1f, 0f, 0f));
	}
	
	public void unError() {
		error = false;
		setBackground(UIManager.getColor("TextField.background"));
	}
	
	@Override
	public void setText(String t) {
		String text = getText();
		super.setText(t);
		
		event.startEvent(EventChange.VALUE_CHANGED, new ChangeEvent(text, t));
	}
	
	public boolean isError() {return error;}
	
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
		
	public void copyData() {oldData.add(getText());}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g2);
		
		FontMetrics metrics = g2.getFontMetrics();
		if(getText() == null || getText().equals("")) {
			g2.setFont(UIManager.getFont("TextField.font"));
			
			if(UIManager.getColor("TextField.placeholder") != null) g2.setColor(UIManager.getColor("TextField.placeholder"));
			else  g2.setColor(new Color(128, 128, 128));
			String text = !useLang?placeholder!=null?placeholder:"":Lang.get(TEXT_FIELD + "." + name + ".name");

			g2.drawString(text, 5, metrics.getHeight());
		}
	}
	
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
	
	public void setPlaceholder(String placeholder) {this.placeholder = placeholder;}
	
	@Override
	public void setText() {
		if(useLang && (getText() == null || getText().equals("")))
			repaint();
	}

	@Override
	public String getElementName() {
		return "TextField";
	}
	
	public void addChangeListener(ChangeListener listener) {event.addListener(listener);}
	
	public static void main(String[] args) {
		JFrame fen = new JFrame();
		fen.setDefaultCloseOperation(3);
		fen.setSize(1200, 700);
		fen.setLocationRelativeTo(null);
		Look.chageLook(UIManager.getSystemLookAndFeelClassName());
		Lang.addLang(LangType.en_US);
		Lang.addLang(LangType.fr_FR);
		Lang.put("textfield.test.name", "Hellooooo");
		LangManager.putElement("textfield.test.name", "fr", LangType.fr_FR);
		
		JPanel pan = new JPanel();
		TextField tf = new TextField(10, "test");
		pan.add(tf);
		tf.setPlaceholder("Name");
		
		JButton but = new JButton("click");
		but.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!tf.isError())
					tf.setError();
				else
					tf.unError();
				//Lang.updateLang(LangType.fr_FR);
			}
		});
		pan.add(but);
		
		fen.setContentPane(pan);
		
		fen.setVisible(true);
	}

}
