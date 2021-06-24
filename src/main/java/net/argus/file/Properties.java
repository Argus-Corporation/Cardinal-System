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

import net.argus.instance.Instance;
import net.argus.system.InitializationSystem;
import net.argus.system.UserSystem;

public class Properties extends CardinalFile {
	
	private static final String EXTENTION = "properties";
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param rep
	 */
	public Properties(String fileName, String rep) {
		super(fileName, EXTENTION, rep);
	}
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param rep
	 * @param instance
	 */
	public Properties(String fileName,  String rep, Instance instance) {
		super(fileName, EXTENTION, rep, instance);
	}
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param file
	 */
	public Properties(File file) {
		super(file);
	}
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param extention
	 * @param file
	 */
	public Properties(String path) {
		super(path);
	}
	
	/**
	 * Ce constructeur permer d'initialiser Properties et de cr�er le fichier si il n'existe pas
	 * @param path
	 * @param instance
	 */
	public Properties(String path, Instance instance) {
		super(path, instance);
	}
	
	/**
	 * Cette méthode permer d'écrire dans un fichier properties
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void write(String key, String value) throws IOException {
		super.writeAppend(key + "=" + value);
	}
	
	/**
	 * Cette méthode permer d'écrire toutes les lines de la List<String> dans le fichier
	 * @param lines
	 * @throws IOException
	 */
	public void write(List<String> lines) throws IOException {
		for(String line : lines) super.writeAppend(line);
	}
	
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
	 *  Cette methode permer de creer des nouvelle clef
	 * @param keys
	 * @param values
	 * @throws IOException
	 */
	public void addKeys(List<String> keys, List<String> values) throws IOException {
		int size = keys.size()<values.size()?keys.size():values.size();
		for(int i = 0; i < size; i++)
			addKey(keys.get(i), values.get(i));
	}
	
	/**
	 * Cette methode permer de supprimer une clef
	 * @param key
	 * @throws IOException
	 */
	public void removeKey(String key) throws IOException {
		List<String> data = toList();
		int line = indexOf(key);
		
		data.remove(line);
		
		clear();
		write(data);
	}
	
	/**
	 * Cette methode permer de remplacer une clef par une autre
	 * @param key
	 * @param value
	 * @throws IOException 
	 */
	public void setKey(String key, String value) throws IOException {
		List<String> data = toList();
		int line = indexOf(key);
		
		if(line != -1)
			data.set(line, key + "=" + value);
		else
			data.add(key + "=" + value);
		
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
		setKey(key, value.toString());
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
	 * @throws IOException 
	 */
	public int indexOf(String key) throws IOException {
		List<String> data = toList();
		
		for(int i = 0; i < data.size(); i++) {
			String line = data.get(i);
			
			int index = line.indexOf('=');
			if(index == -1)
				continue;
			
			line = line.substring(0, index);
			if(line.equals(key))
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Cette methode retourne la clef String corespondante a la clef
	 * @param key
	 * @return result
	 */
	public String getString(String key) {
		String line = null;
		
		try {
			int index = indexOf(key);

			if(index == -1)
				return null;
			
			line = read(index);
		}catch(IOException e) {}
		if(line != null)
			return getValue(line);
		else
			return null;
	}

	/**
	 * Cette methode retourne la clef int corespondante a la clef
	 * @param key
	 * @return result
	 */
	public int getInt(String key) {
		try {return Integer.valueOf(getString(key));}
		catch(NumberFormatException e) {return -1;}
	}
	
	/**
	 * Cette methode retourne la clef boolean corespondante a la clef
	 * @param key
	 * @return result
	 */
	public boolean getBoolean(String key) {
		return Boolean.valueOf(getString(key));
	}
	
	/**
	 * Cette methode retourne la clef Color corespondante a la clef
	 * @param key
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
		String[] line = getMultiString(key);
		
		try {return new Dimension(Integer.valueOf(line[0]), Integer.valueOf(line[1]));
		}catch(IndexOutOfBoundsException | NumberFormatException e) {return null;}
	}
	
	/**
	 * Cette methode retourne la clef Point corespondante a la clef
	 * @param key
	 * @return
	 */
	public Point getPoint(String key) {
		Dimension dim = getDimension(key);
		return new Point(dim.width, dim.height);
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
	 * @throws FileNotFoundException 
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
		String[] multiString = getMultiString(key);
		int[] result = new int[multiString.length];
		
		for(int i = 0; i < multiString.length; i++)
			result[i] = Integer.valueOf(multiString[i]);
		
		return result;
	}
	
	/**
	 * Cette methode retourne vrai si la clef exist
	 * @param key
	 * @return exist
	 */
	public boolean containsKey(String key) {
		try {
			String lineKey = null;
			for(int i = 0; i < countLine(); i++)
				if((lineKey = getKey(read(i))) != null && lineKey.equals(key))
					return true;
		}catch(IOException e) {}
		return false;
	}
	
	/**
	 * Cette méthode retourne la clef de la ligne
	 * @param line
	 * @return
	 */
	public String getKey(String line) {
		int index = line.indexOf('=');
		if(index > -1)
			return line.substring(0, index);
		else
			return null;
	}
	
	/**
	 * Cette méthode retourne la valeur de la ligne
	 * @param line
	 * @return value
	 */
	public String getValue(String line) {
		return line.substring(line.indexOf('=') + 1);
	}

	public static void main(String[] args) throws IOException {
		InitializationSystem.initSystem(new String[] {"-name", "Cardinal-System", "-id", "0xdev", "-log", "false"}, UserSystem.getDefaultInitializedSystemManager());
		
		Properties config = new Properties("config", "bin");
		System.out.println(config.getBoolean("lang.temp"));
	}
	
}
