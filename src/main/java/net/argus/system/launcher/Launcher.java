package net.argus.system.launcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.file.FileManager;
import net.argus.instance.Loader;
import net.argus.system.ExtractTemp;
import net.argus.system.Manifest;
import net.argus.system.OS;
import net.argus.system.Temp;
import net.argus.util.ArrayManager;
import net.argus.util.debug.Debug;

public class Launcher {
	
	private ProgramState state = ProgramState.NEW;
	
	private List<String> classPaths = new ArrayList<String>();
	
	private List<String> vmArgs = new ArrayList<String>();
	private List<String> args = new ArrayList<String>();
	
	private String mainInstanceClass;
	
	private boolean extractNatives;
	
	public Launcher() throws IOException {
		this(new String[0]);
	}
	
	public Launcher(String[] progArgs) throws IOException {
		Manifest manifest = Manifest.getManifest();
		extractNatives = Boolean.valueOf(manifest.getValue("Extract-Natives"));
		
		String classPath = valueOf(manifest.getValue("Launcher-Class-Path"));
		String vmArgs = valueOf(manifest.getValue("Launcher-VM-Args"));
	    String args = valueOf(manifest.getValue("Launcher-Args"));
	    
	    mainInstanceClass = manifest.getValue("Main-Instance-Class");

	    for(String cp : classPath.split(";")) {
	    	addClassPath(cp);
	    }
	    
	    if(vmArgs != null) this.vmArgs = ArrayManager.toList(vmArgs.split(" "));
	    if(args != null) this.args = ArrayManager.toList(args.split(" "));
	    
	    this.args = ArrayManager.toList(ArrayManager.add((String[]) this.args.toArray(new String[this.args.size()]), progArgs));
	}
	
	public static String valueOf(String str) {
		if(str == null)
			return str;
		
		for(boolean valid = false; !valid;) {
			if(str.contains("%path%"))
				str = valueOf(str, System.getProperty("user.dir") + "/");
			else if(str.contains("%name%"))
				str = valueOf(str, FileManager.getFileName(FileManager.getCodeSourceLocation()));
			else if(str.contains("%suf%"))
				str = valueOf(str, FileManager.getFileSuffix(FileManager.getCodeSourceLocation()));
			else if(str.contains("%temp%"))
				str = valueOf(str, Temp.getTempDir() + "/");
			else
				valid = true;
		}
						
		return str;
	}
	
	private static String valueOf(String str, String add) {
		String first = str.substring(0, str.indexOf("%"));
		String second = str.substring(str.indexOf("%") + 1);
		second = second.substring(second.indexOf("%") + 1);

		return first + add + second;
	}
	
	public void setExtractNatives(boolean extract) {this.extractNatives = extract;}
	public void setMainInstanceClass(String mainInstanceClass) {this.mainInstanceClass = mainInstanceClass;}
	
	public void addClassPath(String cp) {classPaths.add(cp);}
	public void addVMArg(String arg) {vmArgs.add(arg);}
	public void addArg(String arg) {args.add(arg);}
	
	public ProgramState getState() {return state;}
	
	public String getClassPath() {
		String classPath = "";
    	for(String cp : classPaths)
    		classPath += cp + getSeperator();
    	return classPath;
	}
	
	public int start() throws Exception {
		state = ProgramState.LAUNCHING;
		
		List<String> commands = new ArrayList<String>();
		commands.add("java");
		
		commands.add("-cp");
		commands.add(getClassPath());
				
		if(extractNatives) {
			extractNatives();
			commands.add("-Djava.library.path=" + Temp.getTempDir() + "/");
			commands.add("-Dtemp=" + Temp.getTempDir());
		}else
			commands.add("-Djava.library.path=.");
		
		commands.add("-Dinstance.class=" + mainInstanceClass);
		
		for(String vmArg : vmArgs) {
			if(vmArg != null && !vmArg.equals(" "))
				commands.add(vmArg);
		}
		
		commands.add(Loader.class.getCanonicalName());
		
		for(String arg : args)
			commands.add(arg);
								
		state = ProgramState.RUNNING;
		int status = launch(commands);
		state = ProgramState.TERMINATED;
		
		FileManager.delete(Temp.getTempDir() + "/");
		
		return status;
	}
	
	public static String getSeperator() {return OS.currentOS()==OS.WINDOWS?";":":";}
	
	private static int launch(List<String> commands) throws Exception {
		ProcessBuilder builder = new ProcessBuilder(commands);
		
		RunningProgram run = new RunningProgram(builder.start());
		run.readConsoleInput();
		run.writeErrorConsoleOutput();
		run.writeConsoleOutput();
		
		run.waitFor();
		
		int status = run.exitValue();
    	Debug.log("Program was terminated with exit code: " + status);

		return status;
	}
	
	public static void extractNatives() throws Exception {
		ExtractTemp cop = new ExtractTemp();
		
		List<String> ext = new ArrayList<String>();
		ext.add("*.dll");
		ext.add("*.so");
		
		cop.copy(ext);
	}
	
}
