package net.argus.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.argus.file.FileManager;
import net.argus.instance.Loader;
import net.argus.util.debug.Debug;

public class Launcher {
	
	private String[] uArgs;
	private String[] vmArgs;
	
	private String nativePath;
	
	private List<String> classpaths = new ArrayList<String>();
	
	public Launcher(boolean launchThisJar) throws Exception {
		File thisJar = FileManager.getCodeSourceLocation();
		Manifest manifest = new Manifest(thisJar);
		
		nativePath = Temp.getTempDir();

		String vmArgs = manifest.getValue("Launcher-VM-Args");
	    String uArgs = manifest.getValue("Launcher-Args");
	    
	    if(vmArgs == null)
	    	vmArgs = "";
	    vmArgs += " -Dinstance.class=" + manifest.getValue("Main-Instance-Class");
	    
	    if(vmArgs != null) this.vmArgs = vmArgs.split(" ");
	    if(uArgs != null) this.uArgs = uArgs.split(" ");
	    
	    if(launchThisJar) addClassPath(thisJar.getAbsoluteFile().toString());
	}
	
	public int launch(List<String> args) throws Exception {
		return launch((String[]) args.toArray(new String[args.size()]));
	}
	
	public int launch(String[] args) throws Exception {
		List<String> arguments = new ArrayList<String>();
		int exit;
		
	    try {
	    	extractNatives();
	    	
	    	arguments.add("java");
	    	
	    	if(vmArgs != null)
	    		for(String vmArg : vmArgs) 
	    			arguments.add(vmArg); 
	    	
	    	arguments.add("-cp");
	    	arguments.add(getClassPath());
	    		
	    	arguments.add("-Djava.library.path=" + nativePath);
	    	arguments.add(Loader.class.getCanonicalName());
	    	
	    	arguments.add("-temp");
	    	arguments.add(nativePath);
	    	
	    	if(uArgs != null)
	    		for(String uArg : uArgs)
	    			arguments.add(uArg);
	    	
	    	if(args != null)
	    		for(String arg : args)
	    			arguments.add(arg);
	    	
	    	exit = start(arguments);
	    }finally {
	    	FileManager.delete(nativePath);
	    }
	    
	    return exit;
	    
	}
	
	@SuppressWarnings("deprecation")
	public static int start(List<String> arguments) throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
    	processBuilder.redirectErrorStream(true);
    	
    	Process process = processBuilder.start();
    	
    	Thread in = new Thread(readConsoleInput(process));
    	in.start();
    	
    	writeConsoleOutput(process);
    	
    	process.waitFor();
    	in.stop();
    	
    	int exit = process.exitValue();
    	Debug.log("Program was terminated with exit code: " + exit);
    	
    	return exit;
	}
	  
	public static void writeConsoleOutput(Process process) throws Exception {
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		while((line = br.readLine()) != null)
			System.out.println(line);
	}
	
	@SuppressWarnings("resource")
	public static Runnable readConsoleInput(Process process) throws Exception {
		return () -> {
			OutputStream out = process.getOutputStream();
			PrintStream stream = new PrintStream(out);
			
			Scanner scan = new Scanner(System.in);
			while(true) {
				stream.println(scan.nextLine());
				stream.flush();
			}
		};
	}
	  
	public static void extractNatives() throws Exception {
		ExtractTemp cop = new ExtractTemp();
		
		List<String> ext = new ArrayList<String>();
		ext.add("*.dll");
		ext.add("*.so");
		
		cop.copy(ext);
	}
	
	public static String getSeperator() {return OS.currentOS()==OS.WINDOWS?";":":";}
	
	public void addClassPath(String str) {classpaths.add(str + getSeperator());}
	
	public String getClassPath() {
		String classPath = "";
    	for(String cp : classpaths)
    		classPath += cp;
    	return classPath;
	}
	
	public String[] getArgs() {return uArgs;}
	  
	public static void main(String[] args) throws Exception {System.exit(new Launcher(true).launch(args));}

}
