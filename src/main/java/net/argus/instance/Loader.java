package net.argus.instance;

import net.argus.system.AnnotationManager;
import net.argus.system.ExitCode;
import net.argus.system.UserSystem;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class Loader {
	
	public static void load() {
		String[] classes = getInstancesClass();
		
		if(classes == null) {
			Debug.log("No instance found", Info.ERROR);
			return;
		}
		
		CardinalProgram program = null;
		for(String cp : classes)
			if((program = load(cp)) != null)
				ProgramRegister.addProgram(program);
	}
	
	public static CardinalProgram load(String classPath) {
		try {
			Class<?> clas = Loader.class.getClassLoader().loadClass(classPath);
			CardinalProgram program = (CardinalProgram) clas.newInstance();
			
			if(loadProgram(program) == -1)
				return null;
			
			return program;
		}catch(InstantiationException | IllegalAccessException | ClassNotFoundException | ClassCastException e) {
			if(e instanceof InstantiationException)	Debug.log("Instantiation error", Info.ERROR);
			if(e instanceof ClassNotFoundException)	{Debug.log(classPath + " not found", Info.ERROR); e.printStackTrace();}
			if(e instanceof ClassCastException) Debug.log("Class \"" + classPath + "\" not extend \"" + CardinalProgram.class.getCanonicalName() + "\"", Info.ERROR);
			if(e instanceof IllegalAccessException) Debug.log("Class \"" + classPath + "\" is not accessible", Info.ERROR);
		}
		return null;
	}
	
	public static int loadProgram(CardinalProgram program) {
		Program ano = AnnotationManager.getAnnotaion(program.getClass(), Program.class);
		if(ano == null) {
			Debug.log("Class \"" + program.getClass().getCanonicalName() + "\" is not annotated to \"" + Program.class + "\"", Info.ERROR);
			return -1;
		}
		program.setInstance(new Instance(ano.instanceName()));
		
		return 0;
	}
	
	public static String[] getInstancesClass() {
		String ins = UserSystem.getProperty("instance.class");
		if(ins == null)
			return null;
		
		return ins.split(";");
	}
	
	public static void main(String[] args) {
		Instance.setThreadInstance(Instance.SYSTEM);
		
		load();
		CardinalProgram main = ProgramRegister.getMainProgram();
		if(main == null) {
			Debug.log("No main programs found", Info.ERROR);
			UserSystem.exit(ExitCode.ERROR);
		}
		
		main.launch(args, true);
	}

}
