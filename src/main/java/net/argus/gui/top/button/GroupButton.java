package net.argus.gui.top.button;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.argus.event.gui.frame.EventFrame;
import net.argus.event.gui.frame.FrameEvent;
import net.argus.event.gui.frame.FrameListener;
import net.argus.event.mouse.MouseTrackListener;
import net.argus.gui.Frame;
import net.argus.system.UserSystem;

public class GroupButton extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3715825522576719957L;
	
	private Button close;
	private Button minimize;
	private Button expand;
	private Button unexpand;
	
	private Frame fen;
	
	private boolean exp;
	
	public GroupButton(Frame fen) {
		close = new Button(fen, ImageButton.close, TitleButtonType.CLOSE, Button.red);
		minimize = new Button(fen, ImageButton.minimize, TitleButtonType.MINIMIZE, Button.yellow);
		expand = new Button(fen, ImageButton.expand, TitleButtonType.EXPAND, Button.green);
		unexpand = new Button(fen, ImageButton.unexpand, TitleButtonType.UNEXPAND, Button.green);
	
		int w = close.getPreferredSize().width;
		int h = close.getPreferredSize().height;
		
		setPreferredSize(new Dimension((w * 4) + (w / 3) * 4, h));
		setBounds(0, 0, (w * 4) + (w / 3) * 4, h);
		setLayout(null);
		
		this.add(close);
		this.add(minimize);
		this.add(expand);
		this.add(unexpand);
		
		close.addMouseTrackListener(getMouseTrackListener());
		minimize.addMouseTrackListener(getMouseTrackListener());
		expand.addMouseTrackListener(getMouseTrackListener());
		unexpand.addMouseTrackListener(getMouseTrackListener());
		
		fen.addFrameListener(getFrameListener());
		
		this.setOpaque(false);
		
		this.fen = fen;
	}
	
	public void setExpand(boolean exp) {this.exp = exp;}
	
	public void setEnabled(TitleButtonType type, boolean b) {
		switch(type) {
			case CLOSE:
				close.setEnabled(b);
				break;
				
			case MINIMIZE:
				minimize.setEnabled(b);
				break;
				
			case EXPAND:
				expand.setEnabled(b);
				unexpand.setEnabled(b);
				break;
				
			case UNEXPAND:
				expand.setEnabled(b);
				unexpand.setEnabled(b);
				break;
		}
	}
	
	public void showImgs(boolean show) {
		close.showImg(show);
		minimize.showImg(show);
		expand.showImg(show);
		unexpand.showImg(show);
	}
	
	public void setEnableColors(boolean enable) {
		close.setEnableColor(enable);
		minimize.setEnableColor(enable);
		expand.setEnableColor(enable);
		unexpand.setEnableColor(enable);
	}
	
	private MouseTrackListener getMouseTrackListener() {
		return new MouseTrackListener() {
			public void mouseExited(MouseEvent e) {
				if(!fen.isActive())
					setEnableColors(false);
				
				showImgs(false);
				repaint();
			}
			public void mouseEntered(MouseEvent e) {
				if(!fen.isActive())
					setEnableColors(true);
				
				showImgs(true);
				repaint();
			}
			public void mouseClicked(MouseEvent e) {
				Button but = (Button) e.getSource();
				if(e.getButton() == MouseEvent.BUTTON1)
					switch(but.getType()) {
						case CLOSE:
							fen.event(EventFrame.FRAME_CLOSING, e.getSource());
							UserSystem.exit(0);
							break;
							
						case MINIMIZE:
							fen.event(EventFrame.FRAME_MINIMALIZED, e.getSource());
							fen.setExtendedState(JFrame.ICONIFIED);
							break;
							
						case EXPAND:
							fen.event(EventFrame.FRAME_RESIZING, e.getSource());
							fen.setMaximize(true);
							break;
							
						case UNEXPAND:
							fen.event(EventFrame.FRAME_RESIZING, e.getSource());
							fen.setMaximize(false);
							break;
							
					}
			}
		};
	}
	
	private FrameListener getFrameListener() {
		return new FrameListener() {
			public void frameResizing(FrameEvent e) {
				setExpand(fen.isMaximized() || fen.isFullScreen());
				
			}
			public void frameMinimalized(FrameEvent e) {}
			public void frameClosing(FrameEvent e) {}
		};
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int w = close.getPreferredSize().width;
		int h = close.getPreferredSize().height;
		super.paintComponent(g);
		
		int space = (w / 3) * 2;
		
		close.setBounds(0, 0, w + 2, h);
		minimize.setBounds(w + space, 0, w + 2, h);
		expand.setBounds((w + space) * 2, 0, w + 2, h);
		unexpand.setBounds((w + space) * 2, 0, w + 2, h);
		
		close.draw(g);
		g.translate(w + space, 0);
		minimize.draw(g);
		
		g.translate(w + space, 0);
		
		if(!exp) {
			expand.draw(g);
			expand.setEnabled(true);
			unexpand.setEnabled(false);
		}else {
			unexpand.draw(g);
			unexpand.setEnabled(true);
			expand.setEnabled(false);
		}
		
		g.dispose();
	}

}
