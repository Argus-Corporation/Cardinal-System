package net.argus.gui.bubble;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.UIManager;

import net.argus.gui.FontRegister;
import net.argus.gui.GUI;

public class Bubble implements GUI {
	
	private static int MAX_WIDTH = 200;
	
	protected String info;
	protected String text;
	
	protected String[] lines;
	protected Font infoFont;
	protected Font font;
	protected FontMetrics metrics;
	
	protected Component parent;
	
	protected int width;
	protected int height;
		
	public Bubble(String text) {
		super();
		this.text = text;
		
		FontRegister.addElement(this);
		FontRegister.addElement(getBubbleInfoGUI());

		if(UIManager.get("Bubble.font") == null)
			setFont(new Font("roboto", 0, 17));
		else
			setFont(UIManager.getFont("Bubble.font"));
		
		if(UIManager.get("Bubble.info.font") == null)
			infoFont = new Font("roboto", 0, 14);
		else
			infoFont = UIManager.getFont("Bubble.info.font");

	}
	
	protected String[] getLines(String text, int maxW) {
		List<String> lines = new ArrayList<String>();
		
		int lastSpace = 0;
		String tempLine = "";

		for(String word : text.split(" ")) {
			for(char ch : word.toCharArray()) {
				tempLine += ch;
				lastSpace++;
				
				if(ch == '\n') {
					addLine(lines, tempLine);
					tempLine = "";
					lastSpace = 0;
					continue;
				}
				
				if(width(tempLine) > maxW - 40) {
					if(lastSpace > 10) {
						addLine(lines, tempLine);
						tempLine = "";
						lastSpace = 0;
					}else {
						String tempWord = tempLine.substring(tempLine.length() - lastSpace);
						tempLine = tempLine.substring(0, tempLine.length() - lastSpace);
						addLine(lines, tempLine);
						tempLine = tempWord;
						lastSpace = 0;
					}
					
				}
				
			}
			lastSpace = 0;
			tempLine += " ";
		}
		
		if(!tempLine.equals(" "))
			addLine(lines, tempLine);

		return (String[]) lines.toArray(new String[lines.size()]);
	}
	
	private void addLine(List<String> lines, String line) {
		while(line.endsWith(" "))
			line = line.substring(0, line.length() -1);
		lines.add(line);
	}
	
	protected int width(String str) {
		return metrics.stringWidth(str);
	}
	
	protected int width(Font font, String str) {
		FontMetrics metrics = new JLabel().getFontMetrics(font);
		
		return metrics.stringWidth(str);
	}
	
	public void draw(Graphics2D g, int x, int y, int pos, BubbleColor color) {        
		drawBack(g, x, y, color);
		
		drawText(g, x, y, color.getBackground());
		drawInfo(g, x, y, pos, parent!=null?parent.getBackground():Color.WHITE);
	}
	
	public void drawBack(Graphics2D g, int x, int y, BubbleColor color) {
		g.setColor(color.getBackground());
		g.fillRoundRect(x, y  + (isInfo()?infoFont.getSize():0), width, height, 22, 22);
	}
	
	public void drawText(Graphics2D g, int x, int y, Color background) {
		g.setColor(BubbleColor.getForeground(background));
		g.setFont(font);
		
		int x0 = (width - getGratestWidth(lines)) / 2;

		for(int i = 0; i < lines.length; i++)
			g.drawString(lines[i], x + x0, y + metrics.getHeight() + i*metrics.getHeight() + (isInfo()?infoFont.getSize():0));
	}
	
	public void drawInfo(Graphics2D g, int x, int y, int pos, Color background) {
		if(info == null || info.equals(""))
			return;
		
		g.setColor(BubbleColor.getForeground(background));
		g.setFont(infoFont);
		
		int x0 = (pos==BubblePanel.RIGHT?(width - width(infoFont, info)):10) - 15;

		for(int i = 0; i < lines.length; i++)
			g.drawString(info, x + x0 + 10, y +10);
	}
	
	protected int getGratestWidth(String[] text) {
		int w = -1;
		for(String t : text) {
			int w0 = width(t);
			if(w0 > w)
				w = w0;
		}
		
		return w;
	}
	
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	
	public int getTotalHeight() {
		return height + (isInfo()?infoFont.getSize():0) + 5;}
	
	public void setFont(Font font) {
		this.font = font;

		reload();
	}
	
	public void reload() {
		reload(text);
	}
	
	public void reload(String text) {
		this.metrics = new JLabel(text).getFontMetrics(font);
		Rectangle2D fm = metrics.getStringBounds(text, null);
		
		width = (int) (fm.getWidth() + 20);
		height = (int) (fm.getHeight());
		
		this.lines = getLines(text, MAX_WIDTH);
		
		if(this.lines == null)
			return;
				
		if(width > MAX_WIDTH) {
			width = getGratestWidth(this.lines) + 20;
			height = metrics.getHeight() * (this.lines.length);
		}
			height += 10;
	}
	
	public static void setMaxWidth(int max) {MAX_WIDTH = max;}
	
	public void setParent(Component parent) {
		this.parent = parent;
		reload();
	}
	
	public void setInfo(String info) {
		if(info.length() > 15)
			info = info.substring(0, 15);
		
		this.info = info;
	}
	
	private GUI getBubbleInfoGUI() {
		return new GUI() {
			
			@Override
			public void setText() {}
			
			@Override
			public void setForeground(Color fore) {}
			
			@Override
			public void setFont(Font font) {infoFont = font;}
			
			@Override
			public void setBackground(Color bg) {}
			
			@Override
			public String getElementName() {return "Bubble.info";}
		};
	}

	@Override
	public void setForeground(Color fore) {}

	@Override
	public void setBackground(Color bg) {}

	@Override
	public void setText() {}
	
	protected boolean isInfo() {return info != null && !info.equals("");}

	@Override
	public String getElementName() {
		return "Bubble";
	}

}
