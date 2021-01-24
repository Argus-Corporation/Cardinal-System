package net.argus.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.argus.file.FileManager;

public class Launcher {
	
	public Launcher(String[] args) throws Exception {
		File file = getCodeSourceLocation();
		Manifest manifest = new Manifest(file);
		
		List<String> arguments = new ArrayList<String>();
		
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
	    	
	    	arguments.add("-temp");
	    	arguments.add(nativePath);
	    	
	    	for(String uArg : userArgs)
	    		arguments.add(uArg);
	    	
	    	for(String arg : args)
	    		arguments.add(arg);
	    	
	    	ProcessBuilder processBuilder = new ProcessBuilder(arguments);
	    	processBuilder.redirectErrorStream(true);
	    	
	    	Process process = processBuilder.start();
	    	writeConsoleOutput(process);
	    	
	    	process.waitFor();
	    }finally {
	    	FileManager.delete(nativePath);
	    }
	    
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
	
	public File getCodeSourceLocation() {
		try {return new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		}catch(URISyntaxException e) {
			e.printStackTrace();
			return null;
		} 
	}
	  
	public static void main(String[] args) throws Exception {new Launcher(args);}

}
