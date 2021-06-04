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
		
		String classPath = manifest.getValue("Launcher-Class-Path");
		String vmArgs = manifest.getValue("Launcher-VM-Args");
	    String args = manifest.getValue("Launcher-Args");
	    
	    mainInstanceClass = manifest.getValue("Main-Instance-Class");
	    
	    for(String cp : classPath.split(" "))
	    	addClassPath(cp);
	    
	    if(vmArgs != null) this.vmArgs = ArrayManager.convert(vmArgs.split(" "));
	    if(args != null) this.args = ArrayManager.convert(args.split(" "));
	    
	    this.args = ArrayManager.convert(ArrayManager.add((String[]) this.args.toArray(new String[this.args.size()]), progArgs));
	}
	
	public static String getSeperator() {return OS.currentOS()==OS.WINDOWS?";":":";}
	
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
		
		for(String vmArg : vmArgs)
			commands.add(vmArg);
		
		commands.add(Loader.class.getCanonicalName());
		
		commands.add("-instance.class");
		commands.add(mainInstanceClass);
		
		for(String arg : args)
			commands.add(arg);
		
		state = ProgramState.RUNNING;
		int status = launch(commands);
		state = ProgramState.TERMINATED;
		
		FileManager.delete(Temp.getTempDir() + "/");
		
		return status;
	}
	
	private static int launch(List<String> commands) throws Exception {
		ProcessBuilder builder = new ProcessBuilder(commands);
		builder.redirectErrorStream(true);
		
		RunningProgram run = new RunningProgram(builder.start());
		run.writeConsoleOutput();
		run.readConsoleInput();
		
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
