package net.argus.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Launcher {
	
	public Launcher() throws Exception {
		File file = getCodeSourceLocation();
		Manifest manifest = new Manifest(file);
		
		ArrayList<String> arguments = new ArrayList<String>();
		
		String nativePath = Temp.getTempDir();
	    String mainClass = manifest.getValue("Launcher-Main-Class");
	    String vmArgs = manifest.getValue("Launcher-VM-Args");
	    String[] userArgs = manifest.getValue("Launcher-Args").split(" ");
	    StringTokenizer vmArgsToken = new StringTokenizer(vmArgs, " ");
	    int count = vmArgsToken.countTokens();
	    
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
		CopyTemp cop = new CopyTemp();
		
		List<String> ext = new ArrayList<String>();
		ext.add("*.dll");
		ext.add("*.so");
		
		cop.copy(ext);
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
		try {return new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		}catch(URISyntaxException e) {
			e.printStackTrace();
			return null;
		} 
	}
	  
	public static void main(String[] args) throws Exception {new Launcher();}

}
