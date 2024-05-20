package net.argus.example;

import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JPanel;

import net.argus.gui.CFrame;
import net.argus.instance.CardinalProgram;
import net.argus.instance.Program;
import net.argus.system.InitializationSystem;

@Program(instanceName = "test")
public class Test extends CardinalProgram {
	
	@Override
	public void main(String[] args) {
		InitializationSystem.initSystem(args);
		CFrame fen = new CFrame("fdfd");
		
		fen.setFullscreenable(true);
		fen.setTitleBarTransparent(false);
		fen.setFullWindowContent(true);
		fen.setVisibleTitle(false);
		
		Desktop desktop = Desktop.getDesktop();
		if( desktop.isSupported( Desktop.Action.APP_ABOUT ) ) {
		    desktop.setAboutHandler( e -> {
		       
		    } );
		}
		if( desktop.isSupported( Desktop.Action.APP_PREFERENCES ) ) {
		    desktop.setPreferencesHandler( e -> {
		        // show preferences dialog
		    } );
		}
		if( desktop.isSupported( Desktop.Action.APP_QUIT_HANDLER ) ) {
		    desktop.setQuitHandler( (e, response) -> {
		        boolean canQuit = true;
		        if( canQuit )
		            response.performQuit();
		        else
		            response.cancelQuit();
		        
		    } );
		}
		JPanel pan = new JPanel();
		pan.setBackground(Color.PINK);
		
		fen.setContentPane(pan);
		
		fen.setDefaultCloseOperation(3);
		fen.setSize(1200, 700);
		fen.setVisible(true);
				
	}
		
}
