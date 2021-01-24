package net.argus.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import net.argus.file.FileManager;

public class FontManager extends Font {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5912713464727479375L;
	
	public static final Font ARGUS_UI = loadFont(new File(FileManager.getPathInJar("font/ARGUS-UI.ttf")), 0, 25);
	public static final Font SF_UI = loadFont(new File(FileManager.getPathInJar("font/SF-UI.otf")), 0, 15);
	
	private FontManager(String name, int style, int size) {
		super(name, style, size);
	}
		
	public static Font loadFont(File file, int style, int size) {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, file);
			font = font.deriveFont(style, size);
		}catch(FontFormatException | IOException e) {e.printStackTrace();}
		
		return font;
	}
}
