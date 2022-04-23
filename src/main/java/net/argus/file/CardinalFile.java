package net.argus.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.argus.exception.InstanceException;
import net.argus.instance.Instance;
import net.argus.io.FileIO;
import net.argus.system.Temp;
import net.argus.util.ArrayManager;
import net.argus.util.Display;
import net.argus.util.debug.Debug;

public class CardinalFile {
	
	private File file;
	
	public CardinalFile(File file) {
		init(file);
	}
	
	public CardinalFile(String path) {
		init(path);
	}
	
	public CardinalFile(String fileName, String extention, String rep, Instance instance) {
		init(fileName, extention, rep, instance);
	}
	
	public CardinalFile(String fileName, String extention, String rep) {
		init(fileName, extention, rep);
	}
	
	/**--INIT--**/
	private void init(File file) {
		this.file = file;

		if(file.exists())	
			Debug.log("File Loaded: " + file.getAbsolutePath());
	}
	
	private void init(String path) {
		init(new File(path));

	}
	
	private void init(String fileName, String extention, String rep, Instance instance) {
		if(instance == null) 
			throw InstanceException.getInstanceNull();
		
		init(instance.getRootPath() + "/" + rep + "/" + fileName + "." + extention);
	}
	
	private void init(String fileName, String extention, String rep) {
		init(fileName, extention, rep, Instance.currentInstance());
	}
	
	/**
	 * exists
	 * @return
	 */
	public boolean exists() {
		return file.exists();
	}
	
	/**
	 * read line
	 * @param line
	 * @return
	 * @throws IOException
	 */
	public String read(int line) throws IOException {
		return valueOf(FileIO.readLine(file, line));
	}
	
	/**
	 * read
	 * @return
	 * @throws IOException
	 */
	public String read() throws IOException {
		return valueOf(FileIO.readString(file));
	}
	
	/**
	 * file to array
	 * @return
	 * @throws IOException
	 */
	public String[] toArray() {
		try {return valueOf(FileIO.readArray(file));}
		catch(IOException e) {e.printStackTrace();}
		return null;
	}
	
	/**
	 * file to list
	 * @return
	 */
	public List<String> toList() {
		return ArrayManager.toList(toArray());
	}
	
	/**
	 * count line
	 * @return
	 */
	public int countLine() {
		return toList().size();
	}
	
	/**
	 * write
	 * @param text
	 * @throws IOException
	 */
	public void write(String text) throws IOException {
		FileIO.write(file, text);
	}
	
	/**
	 * write array
	 * @param text
	 * @throws IOException
	 */
	public void write(String[] text) throws IOException {
		FileIO.write(file, text);
	}
	
	/**
	 * write line
	 * @param text
	 * @param line
	 * @throws IOException
	 */
	public void write(String text, int line) throws IOException {
		FileIO.write(file, text, line);
	}
	
	/**
	 * write append
	 * @param text
	 * @throws IOException
	 */
	public void writeAppend(String text) throws IOException {
		FileIO.writeAppend(file, text);
	}
	
	/**
	 * write append
	 * @param text
	 * @throws IOException
	 */
	public void writeAppend(Object[] text) throws IOException {
		FileIO.writeAppend(file, text);
	}
	
	/**
	 * Clear file
	 * @throws IOException
	 */
	public void clear() throws IOException {
		write("");
	}
	
	/**
	 * return file
	 * @return
	 */
	public File getFile() {return file;}
	
	/**
	 * Create parent folder
	 */
	public void mkdirs() {file.getParentFile().mkdirs();}
	
	/**
	 * Create file
	 */
	public void createFile() {
		try {
			mkdirs();
			file.createNewFile();
		}catch(IOException e) {e.printStackTrace();}
		
		Debug.log("File Created: " + file.getAbsolutePath());
	}
	
	/**
	 * value of array
	 * @param array
	 * @return
	 */
	public static String[] valueOf(String[] array) {
		for(int i = 0; i < array.length; i++)
			array[i] = valueOf(array[i]);
		return array;
	}
	
	/**
	 * Value Of
	 * @param str
	 * @return
	 */
	public static String valueOf(String str) {
		if(str == null)
			return str;
		
		String first = null;
		String second = null;
		for(boolean valid = false; !valid;) {
			if(str.contains("%path%")) {
				first = str.substring(0, str.lastIndexOf("%"));
				first = first.substring(0, first.lastIndexOf("%"));
				second = str.substring(str.lastIndexOf("%") + 1);
				
				str = first + FileManager.getMainPath() + "/" + second;
			}else if(str.contains("%widthDisplay%")){
				first = str.substring(0, str.lastIndexOf("%"));
				first = first.substring(0, first.lastIndexOf("%"));
				second = str.substring(str.lastIndexOf("%") + 1);
				
				str = first + Integer.toString(Display.getWidth()) + second;
			}else if(str.contains("%heightDisplay%")){
				first = str.substring(0, str.lastIndexOf("%"));
				first = first.substring(0, first.lastIndexOf("%"));
				second = str.substring(str.lastIndexOf("%") + 1);
				
				str = first + Integer.toString(Display.getHeight()) + second;
			}else if(str.contains("%20")) {
				first = str.substring(0, str.lastIndexOf("%20"));
				second = str.substring(str.lastIndexOf("%20") + 3);
				
				str = first + " " + second;
			}else if(str.contains("%temp%")) {
				first = str.substring(0, str.lastIndexOf("%"));
				first = first.substring(0, first.lastIndexOf("%"));
				second = str.substring(str.lastIndexOf("%") + 1);
				
				str = first + Temp.getTempDir() + "/" + second;
			}else
				valid = true;
		}
		return str;
	}
	
}
