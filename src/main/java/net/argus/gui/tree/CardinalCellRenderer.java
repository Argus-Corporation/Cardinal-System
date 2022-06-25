package net.argus.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;

import net.argus.gui.GUI;
import net.argus.lang.Lang;

public class CardinalCellRenderer implements TreeCellRenderer {
	
	private static final String OPEN = "openIcon";
	private static final String CLOSE = "closedIcon";
	private static final String LEAF = "leafIcon";
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		String text = Lang.get(GUI.TREE + "." + value + ".name");
		Icon icon = getIcon(leaf, expanded);

		JComponent comp = getComponent(icon, text, selected);
		comp.setPreferredSize(new Dimension(getWidthMax(icon, text), 20));

		return comp;
	}
	
	public Icon getIcon(boolean leaf, boolean expanded) {
		String type = LEAF;
		if(leaf)
			type = LEAF;
		else if(expanded)
			type = OPEN;
		else if(!expanded)
			type = CLOSE;
		
		return ((Icon) UIManager.get("Tree." + type));

	}
	
	public int getWidthMax(Icon icon, String text) {
		JLabel lab = new JLabel(text);
		lab.setFont(UIManager.getFont("Tree.font"));
		
		FontMetrics metrics = lab.getFontMetrics(UIManager.getFont("Tree.font"));

		return icon.getIconWidth() + 4 + metrics.stringWidth(text);
	}
	
	public JComponent getComponent(Icon icon, String value, boolean selected) {
		return new JComponent() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -824591763231941478L;			
			
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				icon.paintIcon(this, g2, 0, 0);
				
				Font font = UIManager.getFont("Tree.font");
				if(font  != null)
					g2.setFont(font);
				
				
				FontMetrics metrics = g2.getFontMetrics();
				
				if(selected) {
					g2.setColor(new Color(0, 118, 255));
					g2.fillRect(20, 0, metrics.stringWidth(value) + 10, metrics.getHeight() + 10);
					g2.setColor(Color.WHITE);

				}else
					g2.setColor(Color.BLACK);
				
				
				g2.drawString(value, 20, metrics.getHeight() - 2);
			}
		};
	}

}
