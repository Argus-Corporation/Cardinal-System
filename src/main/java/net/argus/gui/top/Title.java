package net.argus.gui.top;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import net.argus.gui.Icon;

class Title extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3476574648299364833L;
	
	private String title;
	private ImageIcon icon;
	private TitleBar titleBar;
	
	private Font font = new Font("arial", 1, 19);
	
	public Title(String title, ImageIcon icon, TitleBar titileBar) {
		this.titleBar = titileBar;
		this.title = title;
		this.icon = icon;
		
		setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        
        FontMetrics metrics = g2d.getFontMetrics();
        int stringWith = metrics.stringWidth(title);
        
        setBounds((titleBar.getWidth() / 2) - (stringWith / 2), 0, stringWith + 20, titleBar.getHeight());

        g2d.drawImage(Icon.getIcon(icon, 16).getImage(), 0, (titleBar.getHeight() / 2) - (metrics.getHeight() / 2) + 5, null);
        g2d.drawString(title, 20, (titleBar.getHeight() / 2) - (metrics.getHeight() / 2) + 20);
		super.paintComponent(g);
	}
	
	public void setTitle(String title) {this.title = title;}
	
	public String getTitle() {return title;}

}
