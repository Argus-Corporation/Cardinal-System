package net.argus.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import net.argus.file.Properties;

public class Panel extends JPanel implements Element, GUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7969497498299198938L;
	
	private static final String nameType = "panel";
	private static final boolean isBack = true;
	private static final boolean isFore = false;
	private static final boolean isFont = false;
	
	public static ArrayList<Image> img = new ArrayList<Image>();
	public static ArrayList<String> imgPaths = new ArrayList<String>();
	public static ArrayList<Panel> allPanelImg = new ArrayList<Panel>();
	public static ArrayList<Panel> allPanel = new ArrayList<Panel>();
	public ArrayList<Image> backImg = new ArrayList<Image>();
	public String imgPath;
	public int index;
	public boolean isBackImg;
	public boolean isRondBorder;
	
	private static Panel selected;
	private static Panel triggered;
	
	private Frame fen;

	public Panel(Properties config, boolean isRondBorder) {
		super();
		this.isRondBorder = isRondBorder;
		allPanel.add(this);
		nameTypes.add(nameType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
		this.isBackImg = false;
		this.setBackground(config.getColor(nameType + ".color.background"));
	}
	
	public Panel(Properties config, String imgPath, Frame fen) {
		super();
		allPanel.add(this);
		this.fen = fen;
		this.imgPath = imgPath;
		this.isBackImg = true;
		this.isRondBorder = false;
		allPanelImg.add(this);
		imgPaths.add(imgPath);
		this.isBackImg = true;
		this.index = imgPaths.size() - 1;
		nameTypes.add(nameType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
		this.setBackground(config.getColor(nameType + ".color.background"));
	}
	
	public Panel() {
		super();
		allPanel.add(this);
		this.isBackImg = false;
		this.isRondBorder = false;
		
		BackgoundRegister.addElement(this);
		ForegroundRegiter.addElement(this);
	}
	
	public static void setSelected(Panel selected) {
		if(Panel.selected != null) Panel.selected.setBackground(new Color(Panel.selected.getBackground().getRed() - 50, Panel.selected.getBackground().getGreen() - 50, Panel.selected.getBackground().getBlue() - 50));
		Panel.selected = selected;
		selected.setBackground(new Color(selected.getBackground().getRed() + 50, selected.getBackground().getGreen() + 50, selected.getBackground().getBlue() + 50));
		unTriggered();
	}
	
	public static void setTriggered(Panel triggered) {
		if(selected != null) {
			if(!selected.equals(triggered)) {
				if(Panel.triggered != null) Panel.triggered.setBackground(new Color(Panel.triggered.getBackground().getRed() - 25, Panel.triggered.getBackground().getGreen() - 25, Panel.triggered.getBackground().getBlue() - 25));
				Panel.triggered = triggered;
				triggered.setBackground(new Color(triggered.getBackground().getRed() + 25, triggered.getBackground().getGreen() + 25, triggered.getBackground().getBlue() + 25));			
			}
		}else {
			if(Panel.triggered != null) Panel.triggered.setBackground(new Color(Panel.triggered.getBackground().getRed() - 25, Panel.triggered.getBackground().getGreen() - 25, Panel.triggered.getBackground().getBlue() - 25));
			Panel.triggered = triggered;
			triggered.setBackground(new Color(triggered.getBackground().getRed() + 25, triggered.getBackground().getGreen() + 25, triggered.getBackground().getBlue() + 25));			
		
		}
	}
	
	public static void unTriggered() {
		if(Panel.triggered != null) Panel.triggered.setBackground(new Color(Panel.triggered.getBackground().getRed() - 25, Panel.triggered.getBackground().getGreen() - 25, Panel.triggered.getBackground().getBlue() - 25));
		Panel.triggered = null;
	}
	
	public static void repaintAll() {
		for(int i = 0; i < allPanel.size(); i++) {
			allPanel.get(i).repaint();
		}
	}
	
	public void add(Component[] comp) {
		for(int i = 0; i < comp.length; i++) {
			this.add(comp[i]);
		}
	}
	public void add(String[] str) {
		for(int i = 0; i < str.length; i++) {
			this.add(new Label(str[i], false));
		}
	}
	
	public Image getBackgroundImageFloat() {return backImg.get(0);}
	public Image getBackgroundImageFull() {return backImg.get(1);}
	
	@Override
	public void paintComponent(Graphics g) {
		if(isBackImg) {
			if(fen.isMaximized()) {g.drawImage(backImg.get(1), 0, 0, null);}
			else {if(backImg.size() > 0) {g.drawImage(backImg.get(0), 0, 0, null);}}
		}else if(isRondBorder) {
			g.setColor(getBackground());
			g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);	
		}else {
			
			
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			super.paintComponent(g);
		}
	}

	@Override
	public void setText() {}

	@Override
	public String getElementName() {
		return "Panel";
	}
	
}
