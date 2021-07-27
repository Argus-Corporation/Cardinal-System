package net.argus.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.exception.PositionException;
import net.argus.instance.Instance;

public class FileSave extends CardinalFile {
	
	protected String firstType;
	protected String idType;
	protected String secondType;
		
	/**
	 * Ce constructeur permer d'initialiser FileSave et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param extention
	 * @param repertoir
	 * @param info[]
	 */
	public FileSave(String fileName, String extention, String rep, String[] info) {
		super(fileName, extention, rep);
		this.firstType = info[0];
		this.idType = info[1];
		this.secondType = info[2];
	}
	
	/**
	 * Ce constructeur permer d'initialiser FileSave et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param extention
	 * @param repertoir
	 * @param info[]
	 * @param instance
	 */
	public FileSave(String fileName, String extention, String rep, String[] info, Instance instance) {
		super(fileName, extention, rep, instance);
		this.firstType = info[0];
		this.idType = info[1];
		this.secondType = info[2];
	}
	
	/**
	 * Ce constructeur permer d'initialiser FileSave et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param repertoir
	 * @param info[]
	 */
	public FileSave(String fileName, String rep, String[] info) {
		this(fileName, "save", rep, info);
	}
	
	/**
	 * Ce constructeur permer d'initialiser FileSave et de cr�er le fichier si il n'existe pas
	 * @param file
	 * @param info[]
	 */
	public FileSave(File file, String[] info) {
		super(file);
		this.firstType = info[0];
		this.idType = info[1];
		this.secondType = info[2];
	}
	
	/**
	 * Cette methode permer de lire dans un fichier de type Save
	 * @param id
	 * @return result
	 */
	public String getValue(int id) {
		List<String> data = toList();
		String sId = Integer.toString(id);
		
		for(int i = 0; i < data.size(); i++) {
			String line = data.get(i);
			if(line.startsWith(firstType + "." + this.idType + sId))
				return line.substring(firstType.length() + idType.length() + sId.length() + secondType.length() + 3);
		}
		return null;
	}
	
	/**
	 * Cette methode permer d'�crire a la suite du fichier
	 * @param value
	 * @throws IOException 
	 */
	public void writeLine(String value, int id) throws IOException {
		super.writeAppend(firstType + "." + idType + id + "." + secondType + "=" + value);
	}
	
	/**
	 * Cette méthode permer d'écrire les valeur d'une ArrayList a la suite du fichier
	 * @param value
	 * @throws IOException
	 */
	public void write(List<String> value) throws IOException {
		for(int i = 0; i < value.size(); i++) this.writeLine(value.get(i), i + 1);
	}
	
	public void addValue(String value) throws IOException {
		writeLine(value, toList().size());
	}
	
	/**
	 * Cette méthode permer de modifier une valueur
	 * @param id
	 * @param newValue
	 * @throws IOException
	 */
	public void setValue(int id, String newValue) throws IOException {
		List<String> data = copyValue();
		
		data.add(data.get(data.size()-1));
		
		for(int i = data.size() - 1; i > id - 1; i--)
			data.set(i, data.get(i - 1));
		
		data.set(id -1, newValue);
		clear();
		write(data);
	}
	
	/**
	 * Cette methode permer de suprimer une value dans le fichier
	 * @param oldValue
	 * @throws IOException 
	 */
	public int deleteValue(String oldValue) throws IOException {
		List<String> data = copyValue();
		int oldLine = getId(oldValue);

		if(oldLine > -1) {
			data.remove(oldLine);
			clear();
			write(data);
			
			return 0;
		}
		
		return -1;
	}
	
	/**
	 * Cette methode permer de monter une value dans le fichier
	 * @param value
	 * @throws IOException 
	 * @throws PositionExeption
	 */
	public void upValue(String value) throws PositionException, IOException {
		ArrayList<String> data = (ArrayList<String>) copyValue();
		int oldLine = getId(value);
		
		if(oldLine > 0) {
			@SuppressWarnings("unchecked")
			List<String> temp = (List<String>) data.clone();
			
			data.set(oldLine - 1, data.get(oldLine));
			data.set(oldLine, temp.get(oldLine - 1));
			
			clear();
			write(data);
		}else
			throw new PositionException(oldLine, data.size());
	}
	
	/**
	 * Cette methode permer de desendre une value dans le fichier
	 * @param value
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws PositionExeption
	 */
	public void downValue(String value) throws PositionException, FileNotFoundException, IOException {
		ArrayList<String> data = (ArrayList<String>) copyValue();
		int oldLine = getId(value);
		
		if(oldLine < data.size()) {
			@SuppressWarnings("unchecked")
			List<String> temp = (List<String>) data.clone();

			data.set(oldLine, data.get(oldLine + 1));
			data.set(oldLine + 1, temp.get(oldLine));
			
			clear();
			write(data);
		}else
			throw new PositionException(oldLine, data.size());
	}
	
	/**
	 * Cette methode retourne le num�ro de la ligne o� se trouve "search"
	 * @param search
	 * @return int
	 * @throws IOException 
	 */
	public int getId(String search) throws IOException {
		List<String> data = toList();
		String line = null;
		
		for(int i = 0; i < data.size(); i++) {
			line = data.get(i);
			line = line.substring(line.indexOf("=") + 1);
			if(line.equals(search))
				return i;

		} 
			
		return -1;
	}
	
	/**
	 * Cette methode retourne la list de toute les valeur du fichier
	 * @return
	 */
	public List<String> copyValue() {
		List<String> data = toList();
		
		for(int i = 0; i < data.size(); i++)
			data.set(i, data.get(i).substring(data.get(i).indexOf('=') + 1));
		
		return data;
	}
	
	public String getFirstType() {return firstType;}
	public String getIdType() {return idType;}
	public String getSceondType() {return secondType;}
	
	public void setFirstType(String firstType) {this.firstType = firstType;}
	public void setIdType(String idType) {this.idType = idType;}
	public void setSecondType(String secondType) {this.secondType = secondType;}

}
