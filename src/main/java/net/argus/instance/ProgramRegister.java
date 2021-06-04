package net.argus.instance;

import java.util.ArrayList;
import java.util.List;

public class ProgramRegister {
	
	private static List<CardinalProgram> programs = new ArrayList<CardinalProgram>();
	
	public static void addProgram(CardinalProgram program) {
		programs.add(program);
	}
	
	public static CardinalProgram getProgram(int index) {
		return programs.get(index);
	}
	
	public static CardinalProgram getMainProgram() {
		if(programs.size() > 0)
			return programs.get(0);
		return null;
	}
	
	public static List<CardinalProgram> getPrograms() {return programs;}

}
