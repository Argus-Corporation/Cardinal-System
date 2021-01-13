package net.argus.file;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;

public class Properties extends AbstractFileSave {
	
	private static final String EXTENTION = "properties";
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param path
	 */
	public Properties(File path) {
		super(path);
	}
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param rep
	 */
	public Properties(String fileName, String rep) {
		this(fileName, EXTENTION, rep);
	}
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param path
	 */
	public Properties(String fileName, File path) {
		this(fileName, EXTENTION, path);
	}
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param fileName 
	 * @param extention
	 * @param rep
	 */
	protected Properties(String fileName, String extention, String rep) {
		super(fileName, extention, rep);
	}
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param fileName 
	 * @param extention
	 * @param path
	 */
	protected Properties(String fileName, String extention, File path) {
		super(fileName, extention, path);
	}
	
	/**
	 * Cette méthode permer d'écrire dans un fichier properties
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void write(String key, String value) throws IOException {
		super.write(key + "=" + value);
	}
	
	/**
	 * Cette méthode permer d'écrire toutes les lines de la List<String> dans le fichier
	 * @param lines
	 * @throws IOException
	 */
	public void write(List<String> lines) throws IOException {
		for(int i = 0; i < lines.size(); i++) super.write(lines.get(i));
	}
	
	@Deprecated
	/**
	 * Cette methode permer de creer une nouvelle clef
	 * @param key
	 * @param value
	 * @throws IOExeption
	 */
	public void addKey(String key, String value) throws IOException {
		write(key, value);
	}
	
	/**
	 * Cette methode permer de remplacer une clef par une autre
	 * @param key
	 * @param value
	 * @throws IOException 
	 */
	public void setKey(String key, String value) throws IOException {
		copyFile();	
		int line = getIdKey(key);
		
		data.set(line - 1, key + "=" + value);
		
		clear();
		write(data);
	}
	
	/**
	 * Cette methode permer de remplacer une clef par une autre
	 * @param key
	 * @param value
	 * @throws IOException 
	 */
	public void setKey(String key, Object value) throws IOException {
		copyFile();	
		int line = getIdKey(key);
		
		data.set(line - 1, key + "=" + value.toString());
		
		clear();
		write(data);
	}
	
	/**
	 * Cette methode permer de remplacer une clef par une autre
	 * @param key
	 * @param value
	 * @throws IOException 
	 */
	public void setKey(String key, int value) throws IOException {
		setKey(key, Integer.toString(value));
	}
	
	/**
	 * Cette methode retourne la ligne ou se touve le parametre
	 * @param key
	 * @return numberLine
	 */
	public int getIdKey(String key) {
		copyFile();
		try {
			for(int i = 1; i < getNumberLine() + 1; i++) {
				String lineKey;
				
					lineKey = getLine(i);
				
				lineKey = lineKey.substring(0, lineKey.indexOf('='));
				
				if(lineKey.equals(key))
					return i;
			}
		}catch(IOException e) {}
		
		return -1;
	}
	
	/**
	 * Cette methode retourne la clef String corespondante a la clef
	 * @param key
	 * @return result
	 */
	public String getString(String key) {
		String line = null;
		
		try {line = getLine(getIdKey(key));}
		catch(IOException e) {}
		
		return line!=null?getValue(line):null;
	}

	/**
	 * Cette methode retourne la clef int corespondante a la clef
	 * @param key
	 * @return result
	 */
	public int getInt(String key) {
		try {return Integer.valueOf(getString(key));}
		catch(NumberFormatException e) {return 0;}
	}
	
	/**
	 * Cette methode retourne la clef boolean corespondante a la clef
	 * @param key
	 * @return result
	 * @throws FileNotFoundException 
	 */
	public boolean getBoolean(String key) {
		return Boolean.valueOf(getString(key));
	}
	
	@Deprecated
	/**
	 * Cette methode retourne la clef Color corespondante a la clef
	 * @param kek
	 * @return
	 */
	public Color getColor(String key) {
		try {return Color.decode("#" + getString(key));}
		catch(NumberFormatException e) {return null;}
		
	}
	
	@Deprecated
	/**
	 * Cette methode retourne la clef Font corespondante a la clef
	 * @param key
	 * @return font
	 */
	public Font getFont(String key) {
		String family = null;
		int style = 0, size = 0;
		
		family = getString(key + ".family");
		style = getInt(key + ".style");
		size = getInt(key + ".size");
		
		return new Font(family, style, size);
	}
	
	/**
	 * Cette methode retourne la clef Dimension corespondante a la clef
	 * @param key
	 * @return dimention
	 */
	public Dimension getDimension(String key) {
		String[] line = getString(key).split(":");
		
		try {return new Dimension(Integer.valueOf(line[0]), Integer.valueOf(line[1]));
		}catch(IndexOutOfBoundsException | NumberFormatException e) {return null;}
	}
	
	/**
	 * Cette methode retourne la clef Point corespondante a la clef
	 * @param key
	 * @return point
	 */
	public Point getPoint(String key) {
		String[] line = getString(key).split(":");
		
		try {return new Point(Integer.valueOf(line[0]), Integer.valueOf(line[1]));
		}catch(IndexOutOfBoundsException | NumberFormatException e) {return null;}
	}
	
	/**
	 * Cette methode retourne la clef Rectangle corespondante a la clef
	 * @param key
	 * @return rectangle
	 * @throws FileNotFoundException 
	 */
	public Rectangle getRectangle(String key)  {
		String[] line = getString(key).split(":");
		
		try {return new Rectangle(Integer.valueOf(line[0]), Integer.valueOf(line[1]),
				Integer.valueOf(line[2]), Integer.valueOf(line[3]));
		}catch(IndexOutOfBoundsException | NumberFormatException e) {return null;}
	}

	/**
	 * Cette méthode retourne les clef String[] corespondante a la clef
 	 * @param key
	 * @return
	 */
	public String[] getMultiString(String key) {
		return getString(key).split(":");
	}
	
	/**
	 * Cette méthode retourne les clef int[] corespondante a la clef
 	 * @param key
	 * @return
	 */
	public int[] getMultiInteger(String key) {
		String[] strs = getMultiString(key);
		int[] is = new int[strs.length];
		
		for(int i = 0; i < is.length; i++)
			is[i] = Integer.valueOf(strs[i]);
		
		return is;
	}
	
	/**
	 * Cette methode retourne vrai si la clef exist
	 * @param key
	 * @return exist
	 */
	public boolean containsKey(String key) {
		try {
			for(int i = 1; i < getNumberLine(); i++) {
				if(getKey(getLine(i)).equals(key)) {
					return true;
				}
			}
		}catch(IOException e) {}
		return false;
	}
	
	/**
	 * Cette méthode retourne la clef de la ligne
	 * @param line
	 * @return
	 */
	protected String getKey(String line) {
		return line.substring(0, line.indexOf('='));
	}
	
	/**
	 * Cette méthode retourne la valeur de la ligne
	 * @param line
	 * @return
	 */
	public String getValue(String line) {
		return line.substring(line.indexOf('=') + 1);
	}

	public static void main(String[] args) throws IOException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		
		Properties config = new Properties("config", "bin");
		System.out.println(config.getMultiString("test")[9]);
	}
	
}

