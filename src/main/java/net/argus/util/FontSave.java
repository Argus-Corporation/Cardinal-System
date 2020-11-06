package net.argus.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontSave extends Font {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5912713464727479375L;

	public FontSave(String name, int style, int size) {
		super(name, style, size);
	}
	
	
	public static Font loadFont(File file, int style, int size) throws IOException {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, file);
			font = font.deriveFont(style, size);
		}catch(FontFormatException e) {e.printStackTrace();}
		
		return font;
	}
}
