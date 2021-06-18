package net.argus.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontManager {
	
	private Font font;
	
	private File path;
	private int style = 0;
	private float size = 15;
	
	public FontManager(File path) {
		this.path = path;
	}
	
	public FontManager(String path) {
		this(new File(path));
	}
		
	public static Font loadFont(File file, int style, float size) {
		Font font = loadFont(file);
		font = font.deriveFont(style, size);
		
		return font;
	}
	
	public static Font loadFont(File file) {
		Font font = null;
		try {font = Font.createFont(Font.TRUETYPE_FONT, file);}
		catch(FontFormatException | IOException e) {e.printStackTrace();}
		
		return font;
	}
	
	public static void registerFont(Font font) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		ge.registerFont(font);
	}
	
	public Font getFont() {
		if(font == null)
			font = loadFont(path, style, size);
		
		return font;
	}
	
	public void setStyle(FontStyle style) {
		this.style = style.getId();
	}
	
}
