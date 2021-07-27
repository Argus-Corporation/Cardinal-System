package net.argus.gui.frame.top;

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
		
	public Title(String title, TitleBar titileBar) {
		this.titleBar = titileBar;
		this.title = title;
		
		setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(g2d.getFont().deriveFont(18f).deriveFont(1));
        
        FontMetrics metrics = g2d.getFontMetrics();
        int stringWith = metrics.stringWidth(title);
        
        int x = (titleBar.getWidth() / 2) - (stringWith / 2);
        int y = (titleBar.getHeight() / 2) - (metrics.getHeight() / 2);
        
        setBounds(titleBar.getBounds());
        
        if(icon != null && icon.getImage() != null)
        	g2d.drawImage(Icon.getIcon(icon, 22).getImage(), x, y, null);
        
        g2d.drawString(title, x + 27, y + 20);
	}
	
	public void setTitle(String title) {this.title = title;}
	public void setIcon(ImageIcon icon) {this.icon = icon; repaint();}
	
	public String getTitle() {return title;}
	public ImageIcon getIcon() {return icon;}

}
