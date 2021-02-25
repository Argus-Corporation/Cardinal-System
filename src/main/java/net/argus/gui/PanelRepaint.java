package net.argus.gui;

import java.awt.Container;
import java.awt.Dimension;

import net.argus.util.Display;

public class PanelRepaint {
	
	private Container fen;
	@SuppressWarnings("deprecation")
	private Splash sp;
	
	@SuppressWarnings("deprecation")
	public PanelRepaint(Container fen, Splash sp) {
		this.fen = fen;
		this.sp = sp;
	}
	
	@SuppressWarnings("deprecation")
	private void common() {
		sp.statusText = "initialization";
		for(int i = 0, j = 0; i < Panel.allPanel.size(); i++) {
			if(Panel.allPanel.get(i).isBackImg) {
				Panel.allPanel.get(i).backImg.clear();
				
				int widthScreen = Display.getWidth();
				int heightScreen = Display.getHeight();
				sp.statusText = Panel.imgPaths.get(j).substring(Panel.imgPaths.get(j).lastIndexOf("/") + 1);
				Panel.allPanel.get(i).backImg.add(Icon.getIconResize(Panel.imgPaths.get(j), fen.getSize()).getImage());
				Panel.allPanel.get(i).backImg.add(Icon.getIcon(Panel.imgPaths.get(j), widthScreen, heightScreen).getImage());
				j++;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void common(int diviseur) {
		sp.statusText = "initialization";
		for(int i = 0, j = 0; i < Panel.allPanel.size(); i++) {
			if(Panel.allPanel.get(i).isBackImg) {
				Panel.allPanel.get(i).backImg.clear();
				
				int widthScreen = Display.getWidth();
				int heightScreen = Display.getHeight();
				sp.statusText = Panel.imgPaths.get(j).substring(Panel.imgPaths.get(j).lastIndexOf("/") + 1);
				Panel.allPanel.get(i).backImg.add(Icon.getIconResize(Panel.imgPaths.get(j), new Dimension(fen.getSize().width / diviseur, fen.getSize().height)).getImage());
				Panel.allPanel.get(i).backImg.add(Icon.getIcon(Panel.imgPaths.get(j), widthScreen, heightScreen).getImage());
				j++;
			}
		}
	}
	
	public void initImage() {common();}
	
	public void initImage(int diviseur) {common(diviseur);}

}
