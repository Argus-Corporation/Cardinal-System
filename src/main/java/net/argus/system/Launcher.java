package net.argus.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Launcher {
	
	public Launcher() throws Exception {
		File file = getCodeSourceLocation();
		Manifest manifest = new Manifest(file);
		
		ArrayList<String> arguments = new ArrayList<String>();
		
		String nativePath = getNativePath();
	    String mainClass = manifest.getValue("Launcher-Main-Class");
	    String vmArgs = manifest.getValue("Launcher-VM-Args");
	    String[] userArgs = manifest.getValue("Launcher-Args").split(" ");
	    StringTokenizer vmArgsToken = new StringTokenizer(vmArgs, " ");
	    int count = vmArgsToken.countTokens();
	    System.out.println(nativePath + "   natpath");
	    try {
	    	extractNatives(file, nativePath);
	    	
	    	arguments.add("java");
	    	
	    	for(int i = 0; i < count; i++) {
	    		String str = vmArgsToken.nextToken();
	    		arguments.add(str); 
	    	}
	    	
	    	arguments.add("-cp");
	    	arguments.add(file.getAbsoluteFile().toString());
	    	arguments.add("-Djava.library.path=" + nativePath);
	    	arguments.add(mainClass);
	    	
	    	for(String uArg : userArgs)
	    		arguments.add(uArg);
	      
	    	ProcessBuilder processBuilder = new ProcessBuilder(arguments);
	    	processBuilder.redirectErrorStream(true);
	    	
	    	Process process = processBuilder.start();
	    	writeConsoleOutput(process);
	    	
	    	process.waitFor();
	    }finally {/*deleteNativePath(nativePath);*/}
	    
	}
	  
	public void writeConsoleOutput(Process process) throws Exception {
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		while((line = br.readLine()) != null)
			System.out.println(line);
	}
	  
	public void extractNatives(File file, String nativePath) throws Exception {
		JarFile jarFile = new JarFile(file, false);
		Enumeration<JarEntry> entities = jarFile.entries();
		
		while(entities.hasMoreElements()) {
	    	JarEntry entry = entities.nextElement();
	    	String nativeFile = entry.getName();
	    	
	    	if(isNativeFile(entry.getName())) {
	    		new File(String.valueOf(nativePath) + File.separator + nativeFile.substring(0, nativeFile.lastIndexOf('/'))).mkdirs();
	    		
		    	InputStream in = jarFile.getInputStream(jarFile.getEntry(entry.getName()));
		    	OutputStream out = new FileOutputStream(String.valueOf(nativePath) + File.separator + nativeFile);
		    	
		    	byte[] buffer = new byte[65536];
		    	int bufferSize;
		    	
		    	while((bufferSize = in.read(buffer, 0, buffer.length)) != -1)
		    		out.write(buffer, 0, bufferSize);
		    	
		    	in.close();
		    	out.close();
	    	}
		} 
		jarFile.close();
	}
	  
	public boolean isNativeFile(String entryName) {
		String osName = System.getProperty("os.name");
		String name = entryName.toLowerCase();
		
		System.out.print("[Os: " + osName + "  Amd: " + System.getProperty("os.arch"));
		System.out.print("  Name: " + name);
		System.out.println("  isOs: " + osName.startsWith("Linux") + "  isSo: " + name.endsWith(".so") + "]");
		
		
		if(name.endsWith(".dll") || name.endsWith(".so") || name.endsWith(".jnilib") || name.endsWith(".dylib"))
			return true; 
		
		/*if(osName.startsWith("Win")) {
		}else if(osName.startsWith("Linux")) {
		}else if((osName.startsWith("Mac") || osName.startsWith("Darwin")));*/
		
		return false;
	}
	  
	public String getNativePath() {
		String nativeDir = System.getProperty("deployment.user.cachedir");
		
		if(nativeDir == null || System.getProperty("os.name").startsWith("Win"))
			nativeDir = System.getProperty("java.io.tmpdir");
		nativeDir = String.valueOf(nativeDir) + File.separator + "natives" + (new Random()).nextInt();
		
		File dir = new File(nativeDir);
		
		if(!dir.exists())
			dir.mkdirs(); 
		
		return nativeDir;
	}
	  
	public void deleteNativePath(String directoryName) {
		File directory = new File(directoryName);
		File[] files = directory.listFiles();
		
		File[] arrayOfFile1;
		byte b;
		int i;
		
		for(i = (arrayOfFile1 = files).length, b = 0; b < i; ) {
			File file = arrayOfFile1[b];
			file.delete();
			b++;
		}
		
		directory.delete();
	}
	
	public File getCodeSourceLocation() {
		try {
			return new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		}catch(URISyntaxException e) {
			e.printStackTrace();
			return null;
		} 
	}
	  
	public static void main(String[] args) throws Exception {new Launcher();}

}
