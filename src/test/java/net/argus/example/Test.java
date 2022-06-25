package net.argus.example;

import net.argus.gui.frame.Frame;
import net.argus.instance.CardinalProgram;
import net.argus.instance.Program;

@Program(instanceName = "Test")
public class Test extends CardinalProgram {
	
	public void main(String[] args) {
		Frame fen = new Frame();
		
		fen.setVisible(true);
		
				
    }
	
/*public static void main(String[] args) throws IOException {
		InitializationSystem.initSystem(args);
		System.out.println(FileManager.getMainPath());
	}*/
	
}
