package net.argus.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.argus.file.FileManager;

public class Launcher {
	
	private File thisJar;
	private Manifest manifest;
	
	private String[] uArgs;
	private String[] vmArgs;
	private String mainClass;
	
	private String nativePath;
	
	private List<String> classpaths = new ArrayList<String>();
	
	public Launcher(boolean launchThisJar) throws Exception {
		thisJar = getCodeSourceLocation();
		manifest = new Manifest(thisJar);
		
		
		nativePath = Temp.getTempDir();
	    mainClass = manifest.getValue("Launcher-Main-Class");
	    String vmArgs = manifest.getValue("Launcher-VM-Args");
	    String uArgs = manifest.getValue("Launcher-Args");
	    
	    if(vmArgs != null)this.vmArgs = vmArgs.split(" ");
	    if(uArgs != null)this.uArgs = uArgs.split(" ");
	    
	    if(launchThisJar) addClassPath(thisJar.getAbsoluteFile().toString());
	}
	
	public void launch(List<String> args) throws Exception {
		launch((String[]) args.toArray(new String[args.size()]));
	}
	
	public void launch(String[] args) throws Exception {
		List<String> arguments = new ArrayList<String>();
	    
	    try {
	    	extractNatives();
	    	
	    	arguments.add("java");
	    	
	    	if(vmArgs != null)
	    		for(String vmArg : vmArgs) 
	    			arguments.add(vmArg); 
	    	
	    	arguments.add("-cp");
	    	arguments.add(getClassPath());
	    	
	    	arguments.add("-Djava.library.path=" + nativePath);
	    	arguments.add(mainClass);
	    	
	    	arguments.add("-temp");
	    	arguments.add(nativePath);
	    	
	    	if(uArgs != null)
	    		for(String uArg : uArgs)
	    			arguments.add(uArg);
	    	
	    	if(args != null)
	    		for(String arg : args)
	    			arguments.add(arg);
	    	
<<<<<<< HEAD
		    	start(arguments);
=======
	    	start(arguments);
>>>>>>> dev
	    }finally {
	    	FileManager.delete(nativePath);
	    }
	    
	}
	
	public static void start(List<String> arguments) throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
    	processBuilder.redirectErrorStream(true);
    	
    	Process process = processBuilder.start();
    	writeConsoleOutput(process);
    	
    	process.waitFor();
	}
	  
	public static void writeConsoleOutput(Process process) throws Exception {
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		while((line = br.readLine()) != null)
			System.out.println(line);
	}
	  
	public static void extractNatives() throws Exception {
		CopyTemp cop = new CopyTemp();
		
		List<String> ext = new ArrayList<String>();
		ext.add("*.dll");
		ext.add("*.so");
		
		cop.copy(ext);
	}
	
	public static File getCodeSourceLocation() {
		try {return new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		}catch(URISyntaxException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
<<<<<<< HEAD
	public void addClassPath(String str) {classpaths.add(str + ";");}
=======
	public static String getSeperator() {return OS.getOS()==OS.WINDOWS?";":":";}
	
	public void addClassPath(String str) {classpaths.add(str + getSeperator());}
>>>>>>> dev
	
	public String getClassPath() {
		String classPath = "";
    	for(String cp : classpaths)
    		classPath += cp;
    	return classPath;
	}
<<<<<<< HEAD
=======
	
>>>>>>> dev
	public String[] getArgs() {return uArgs;}
	  
	public static void main(String[] args) throws Exception {new Launcher(true).launch(args);}

}
