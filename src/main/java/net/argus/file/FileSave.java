package net.argus.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import net.argus.exception.PositionException;

public class FileSave extends AbstractFileSave {
	
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
	 * @param repertoir
	 * @param info[]
	 */
	public FileSave(String fileName, String rep, String[] info) {
		this(fileName, "save", rep, info);
	}
	
	/**
	 * Ce constructeur permer d'initialiser FileSave et de cr�er le fichier si il n'existe pas
	 * @param fileName
	 * @param extention
	 * @param path
	 * @param info[]
	 */
	public FileSave(String fileName, String extention, File path, String[] info) {
		super(fileName, extention, path);
		this.firstType = info[0];
		this.idType = info[1];
		this.secondType = info[2];
	}
	
	/**
	 * Cette methode permer de lire dans un fichier de type Save
	 * @param id
	 * @return result
	 * @throws FileNotFoundException 
	 */
	public String read(int id) {
		String line = null;
		String sId = Integer.toString(id);
		
		try {
			for(int i = 1; i < getNumberLine() + 1; i++) {
				line = getLine(i);
				if(line.contains(this.idType + sId)) {
					return line.substring(firstType.length() + idType.length() + sId.length() + secondType.length() + 3);
				}
			}
		}catch(IOException e) {}
		return null;
	}
	
	/**
	 * Cette methode permer d'�crire a la suite du fichier
	 * @param value
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void wirter(String value) throws FileNotFoundException, IOException {
		super.write(firstType + "." + idType + (getNumberLine() + 1) + "." + secondType + "=" + value);
	}
	
	/**
	 * Cette méthode permer d'écrire les valeur d'une ArrayList a la suite du fichier
	 * @param value
	 * @throws IOException
	 */
	public void wirter(List<String> value) throws IOException {
		for(int i = 0; i < value.size(); i++) this.wirter(value.get(i));
	}
	
	/**
	 * Cette methode permer de remplacer une value par une nouvelle
	 * @param oldValue
	 * @param newValue
	 * @throws IOException 
	 */
	public void remplaceValue(String oldValue, String newValue) throws IOException {
		copyValue();
		int oldLine = getId(oldValue);
		clear();
		
		data.set(oldLine - 1, newValue);
		wirter(data);
	}
	
	/**
	 * Cette methode permer de suprimer une value dans le fichier
	 * @param oldValue
	 * @throws IOException 
	 */
	public void deleteValue(String oldValue) throws IOException {
		copyValue();
		int oldLine = getId(oldValue);
		clear();
		
		data.remove(oldLine - 1);
		wirter(data);
		
	}
	
	/**
	 * Cette methode permer de monter une value dans le fichier
	 * @param value
	 * @throws IOException 
	 * @throws PositionExeption
	 */
	public void upValue(String value) throws PositionException, IOException {
		copyValue();
		int oldLine = getId(value);
		
		if(oldLine > 1) {
			clear();
			@SuppressWarnings("unchecked")
			List<String> temp = (List<String>) data.clone();
			
			data.set(oldLine - 2, data.get(oldLine - 1));
			data.set(oldLine - 1, temp.get(oldLine - 2));
			
			wirter(data);
			
		}else {
			throw new PositionException(oldLine, this.getNumberLine());
		}
	}
	
	/**
	 * Cette methode permer de desendre une value dans le fichier
	 * @param value
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws PositionExeption
	 */
	public void downValue(String value) throws PositionException, FileNotFoundException, IOException {
		copyValue();
		int oldLine = getId(value);
		
		if(oldLine < getNumberLine()) {
			clear();
			@SuppressWarnings("unchecked")
			List<String> temp = (List<String>) data.clone();

			data.set(oldLine - 1, data.get(oldLine));
			data.set(oldLine, temp.get(oldLine - 1));
			
			wirter(data);
			
		}else {
			throw new PositionException(oldLine, this.getNumberLine());
		}
	}
	
	/**
	 * Cette méthode copie toutes les valeur du fichier dans l'ArrayList data
	 * @throws FileNotFoundException
	 */
	public void copyValue() throws FileNotFoundException {
		data.clear();
		for(int i = 1; i < getNumberLine() + 1; i++) {
			data.add(read(i));
		}
	}
	
	/**
	 * Cette methode retourne le num�ro de la ligne o� se trouve "search"
	 * @param search
	 * @return int
	 * @throws IOException 
	 */
	public int getId(String search) throws IOException {		
		String line = null;
		
		for(int i = 1; i < getNumberLine() + 1; i++) {
			line = getLine(i);
			line = line.substring(line.indexOf("=") + 1);
			if(line.equals(search))
				return i;

		} 
			
		return 0;
	}
	
	public String getFirstType() {return firstType;}
	public String getIdType() {return idType;}
	public String getSceondType() {return secondType;}
	
	public void setFirstType(String firstType) {this.firstType = firstType;}
	public void setIdType(String idType) {this.idType = idType;}
	public void setSecondType(String secondType) {this.secondType = secondType;}

}
