package net.argus.file;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import net.argus.util.ArrayManager;

public class JarFile {

	public static final String EXTENTION = "jar";
	
	public static final Filter JAR_FILTER = new Filter(EXTENTION, "JAR File");
	
	private String path;
	private String mainRootLocation;
	private List<String> fileName = new ArrayList<String>();
	private List<java.util.jar.JarFile> jarFiles = new ArrayList<java.util.jar.JarFile>();
	
	public JarFile(String path) {
		this.path = path;
	}
	
	public void addFile(String fileName) {
		this.fileName.add(ArrayManager.remplace(fileName, new char[] {'/', '\\'}, File.separatorChar));
	}
	
	public void addJarFile(String filePath) throws IOException {
		jarFiles.add(new java.util.jar.JarFile(filePath));
	}
	
	public void write(String mainRootLocation) throws FileNotFoundException, IOException {
		this.mainRootLocation = mainRootLocation;
		
		JarOutputStream out = new JarOutputStream(new FileOutputStream(new File(path)));
		List<String> name = new ArrayList<String>();
		List<String> extentions = new ArrayList<String>();
		List<String> directory = new ArrayList<String>();
		List<String> allFile = getAllFile(mainRootLocation);
		boolean all = false;
		
		int len = 0;
		byte[] buffer = new byte[1024];
		for(String file : fileName) {
			if(!file.equals("*")) {	
					
				if(file.contains("*."))
					extentions.add(file.substring(file.lastIndexOf('.') + 1));
				
				if(file.contains(".*"))
					name.add(file.substring(file.lastIndexOf(File.separator) + 1, file.lastIndexOf('.')));
				
				if(file.contains(File.separator + "*"))
					directory = getAllDirectory(this.mainRootLocation + file.substring(0, file.lastIndexOf(File.separator) + 1));
			}else all = true;
		}
		
		for(String file : allFile) {
			if(!all) {
				String fileName = file.substring(file.lastIndexOf(File.separator) + 1, file.lastIndexOf('.'));
				String fileExtention = file.substring(file.lastIndexOf('.') + 1);
				
				if(file.lastIndexOf(File.separator) != -1) {
					String dir = file.substring(0, file.lastIndexOf(File.separator) + 1);
					
					if(directory.contains(dir))
						write(buffer, len, out, file);
				}
				
				if(extentions.contains(fileExtention) || this.fileName.contains(file) || name.contains(fileName))
					write(buffer, len, out, file);
				
			}else write(buffer, len, out, file);
		}
		write(buffer, len, out);
		
		out.close();
	}
	
	public List<String> getAllFile(String rootLocation) {
		List<String> fileName = new ArrayList<String>();
		for(File file : new File(rootLocation).listFiles()) {
			String thisRootLocation = file.getPath().substring(file.getPath().lastIndexOf(rootLocation) + this.mainRootLocation.length());
			
			if(file.isDirectory()) {
				new ArrayManager<String>().add(fileName, getAllFile(file.getPath() + File.separator));
			}else
				fileName.add(thisRootLocation);		
		}
		return fileName;
	}
	
	public List<String> getAllDirectory(String rootlocation) {
		List<String> dirName = new ArrayList<String>();
		dirName.add(rootlocation.substring(rootlocation.lastIndexOf(this.mainRootLocation) + this.mainRootLocation.length()));
		for(File file : new File(rootlocation).listFiles()) {
			String thisRootLocation = file.getPath().substring(file.getPath().lastIndexOf(rootlocation) + this.mainRootLocation.length());
			
			if(file.isDirectory()) {
				dirName.add(thisRootLocation);
				new ArrayManager<String>().add(dirName, getAllDirectory(file.getPath() + File.separator));
			}
		}
		return dirName;
	}
	
	public void write(byte[] buffer, int len, JarOutputStream out) throws FileNotFoundException, IOException {
		for(java.util.jar.JarFile file : jarFiles) {
			Enumeration<JarEntry> entities = file.entries();
			
			while(entities.hasMoreElements()) {
				JarEntry entry = entities.nextElement();
				
				out.putNextEntry(entry);
				DataInputStream in = new DataInputStream(file.getInputStream(file.getEntry(entry.getName())));
				while((len = in.read(buffer, 0, buffer.length)) != -1)
					out.write(buffer, 0, len);
				
				in.close();
				out.closeEntry();
			}
		}
	}
	
	public void write(byte[] buffer, int len, JarOutputStream out, String fileName) throws FileNotFoundException, IOException {
		JarEntry entry = new JarEntry(fileName);
		
		out.putNextEntry(entry);
		InputStream in = new BufferedInputStream(new FileInputStream(mainRootLocation + fileName));
		while((len = in.read(buffer, 0, buffer.length)) != -1)
			out.write(buffer, 0, len);
		
		in.close();
		out.closeEntry();
	}
	
	@SuppressWarnings("resource")
	public Attributes getMainAttributes() throws IOException {
		return new java.util.jar.JarFile(new File(path)).getManifest().getMainAttributes();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		JarFile file = new JarFile("D:\\Django\\Document 1\\Git\\www\\Client.jar");
		//file.addFile("*");
		//file.addFile("test/*");
		file.addFile("*.css");
		
		file.addJarFile("D:\\Django\\Document 1\\Java\\Build\\Example\\Chat\\Serveur.jar");
		
		file.write("D:\\Django\\Document 1\\Git\\www\\");
	}

}
