package net.argus.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import net.argus.exception.PositionException;
import net.argus.system.InitializationSystem;
import net.argus.system.UserSystem;
import net.argus.util.Display;
import net.argus.util.debug.Debug;

@Deprecated
public class AbstractFileSave {
	
	protected String path;
	protected File file;
	
	protected String fileName;
	protected String extention;
	protected String rep;
	
	protected ArrayList<String> data = new ArrayList<String>();

	public AbstractFileSave(String fileName, String extention, String rep) {
		this.fileName = fileName;
		this.extention = extention;
		this.rep = rep;
		
		if(this.rep == null) this.rep = "";
		this.rep += File.separator;
		
		this.path = FileManager.getPath(null);
		
		this.path = path.substring(0, path.length() - 1);
		
		if(!this.rep.equals(File.separator))
			this.path += File.separator;
		
		new File(this.path + this.rep).mkdirs();
		
		this.path += this.rep + fileName + "." + extention;
		this.file = new File(this.path);
		
		try {if(!this.file.exists()) {this.file.createNewFile(); Debug.log("File Created: " + getPath());}
		}catch(IOException e) {e.printStackTrace();}
		
		Debug.log("File Loaded: " + getPath());
	}
	
	public AbstractFileSave(String fileName, String extention, File path) {
		this.fileName = fileName;
		this.extention = extention;
		this.path = path.getPath();

		path.mkdirs();
		this.path += "/" + fileName + "." + extention;
		this.file = new File(this.path);
		
		try {if(!this.file.exists()) {this.file.createNewFile(); Debug.log("File Created: " + getPath());}
		}catch(IOException e) {e.printStackTrace();}
		
		Debug.log("File Loaded: " + getPath());
	}
	
	public AbstractFileSave(File path) {
		this.fileName = path.toString().substring(path.toString().lastIndexOf(File.separator) + 1, path.toString().lastIndexOf('.'));
		this.extention = path.toString().substring(path.toString().lastIndexOf('.') + 1);
		this.path = path.getPath();
		
		this.path = regulary(this.path);
		
		this.file = path;
		
		/*try {if(!this.file.exists()) {this.file.createNewFile(); Debug.log("File Created: " + getPath());}
		}catch(IOException e) {e.printStackTrace();}*/
		
		Debug.log("File Loaded: " + getPath());
	}
	
	
	/**
	 * Cette methode permer de retourner le nombre total de ligne dans le fichier
	 * @param line
	 * @return
	 * @throws IOException 
	 */
	public String getLine(int line) throws IOException {
		if(line == -1)
			return null;
		
		@SuppressWarnings("resource")
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		
		for(int i = 1; i < line; i++) {
			try{read.readLine();}
			catch(NoSuchElementException e) {}

		}
		
		String str = null;
		try {str = read.readLine();}
		catch(NoSuchElementException e) {}
		//System.out.println(file);
		return regulary(str);
	}

	/**
	 * Cette methode permer de retourner le nombre total de ligne dans le fichier
	 * @return numberLine
	 */
	public int getNumberLine() {
		int line = 1;
		try {
			if(getLine(line) == null) return 0;
			
			while(getLine(line) != null)
				line++;
		}catch(IndexOutOfBoundsException | IOException e) {}
		return line - 1;
	}

	
	/**
	 * Cette methode retourne la line o?? se trouve le string line
	 * @param line
	 * @return int
	 * @throws FileNotFoundException 
	 */
	protected int getLine(String line) throws FileNotFoundException {
		try {
			for(int i = 1; i < getNumberLine() + 1; i++)	
				if(getLine(i) != null && getLine(i).contains(line)) return i;	
		}catch(IOException e) {e.printStackTrace();}
		return 0;
	}
	
	/**
	 * Cette m??thode retourne le fichier compler sous forme de tableau
	 * @return str[]
	 */
	public String[] getFile() {
		String[] str = new String[getNumberLine()];
		try {
			for(int i = 0; i < str.length; i++) {
				str[i] = getLine(i + 1);
			}
		}catch(IOException e) {}
		return str;
	}
	
	public String getFileInOneLine() {
		String lines = "";
		for(String line : getFile())
			lines += line;
		
		return lines;
	}
	
	/**
	 * Cette methode ???crie dans le fichier la valeur a la suite du fichier
	 * @param value
	 * @throws IOException 
	 */
	public void write(String value) throws IOException {
		PrintWriter pWriter = new PrintWriter(new FileWriter(path, true));
		pWriter.println(value);
		pWriter.close();
	}
	
	/**
	 * Cette methode permer de suprimer le contenu du fichier
	 * @throws IOException 
	 */
	public void clear() throws IOException {
		PrintWriter writer = new PrintWriter(new FileOutputStream(file));
		writer.print("");
		writer.close();
	}
	
	/**
	 * Cette methode permer de copier l'integraliter du fichier dans l'ArrayList data
	 */
	protected void copyFile() {
		data.clear();
		try {for(int i = 1; i < getNumberLine() + 1; i++) data.add(getLine(i));}
		catch(IOException e) {}
	}
	
	/**
	 * regularise le String value
	 * @param value
	 * @param afs
	 * @return value
	 */
	public static String regulary(String value) {
		if(value == null)
			return value;
		
		String first = null;
		String second = null;
		for(boolean valid = false; !valid;) {
			if(value.contains("%path%")) {
				first = value.substring(0, value.lastIndexOf("%"));
				first = first.substring(0, first.lastIndexOf("%"));
				second = value.substring(value.lastIndexOf("%") + 1);
				
				value = first + FileManager.getMainPath() + "/" + second;
			}else if(value.contains("%widthDisplay%")){
				first = value.substring(0, value.lastIndexOf("%"));
				first = first.substring(0, first.lastIndexOf("%"));
				second = value.substring(value.lastIndexOf("%") + 1);
				
				value = first + Integer.toString(Display.getWidth()) + second;
			}else if(value.contains("%heightDisplay%")){
				first = value.substring(0, value.lastIndexOf("%"));
				first = first.substring(0, first.lastIndexOf("%"));
				second = value.substring(value.lastIndexOf("%") + 1);
				
				value = first + Integer.toString(Display.getHeight()) + second;
			}else if(value.contains("%20")) {
				first = value.substring(0, value.lastIndexOf("%20"));
				second = value.substring(value.lastIndexOf("%20") + 3);
				
				value = first + " " + second;
			}else {
				valid = true;
			}
		}
		return value;
	}
	
	public String getPath() {return path;}
	public String getFileName() {return fileName;}
	public String getExtention() {return extention;}
	public String getRepertory() {return rep;}
	
	public void setPath(String path) {this.path = path;}
	public void setFileName(String fileName) {this.fileName = fileName;}
	public void setExtention(String extention) {this.extention = extention;}
	public void setRepertory(String rep) {this.rep = rep;}
	
	public static void main(String[] args) throws IOException, PositionException {
		InitializationSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		
		//AbstractFileSave fs = new AbstractFileSave(new File("D:\\Django\\Document 1\\Git\\test.lang"));
		//FileLang fileLang = new FileLang(new Properties("config", "bin"));
		//System.out.println(fileLang.getElementString("info.ban.name"));
		UserSystem.exit(0);
	}
	
}