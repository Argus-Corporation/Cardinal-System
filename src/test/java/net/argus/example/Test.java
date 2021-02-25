package net.argus.example;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.argus.gui.Button;
import net.argus.gui.EditorPane;
import net.argus.gui.ForegroundRegiter;

public class Test {
	
	public Test() {

	}
	
	public static void main(String[] args) {
	//	InitializationSystem.initSystem(args);
		JFrame fen = new JFrame();
		JPanel pan = new JPanel();
		fen.setDefaultCloseOperation(3);
		fen.setSize(1200, 700);
		
		EditorPane ep = new EditorPane();
		Button b = new Button("test", false);
		
		ep.setText("qsfdsg\r\n" + 
				"dsf\r\n" + 
				"fd\r\n" + 
				"sf\r\n" + 
				"sd\r\n" + 
				"f\r\n" + 
				"sdfsdfs\r\n" + 
				"fsd\r\n" + 
				"f\r\n" + 
				"sd\r\n" + 
				"fds\r\n" + 
				"f\r\n" + 
				"sd\r\n" + 
				"fsdfs^dpojnfmodf");
		ep.setEditable(false);
		
		pan.add(ep);
		pan.add(b);
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UIManager.put("EditorPane.foreground", Color.PINK);
				
				ForegroundRegiter.update();
			}
		});
		
		fen.add(pan);
		
		fen.setVisible(true);
	}
	
}