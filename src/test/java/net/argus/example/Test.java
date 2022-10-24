package net.argus.example;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Test {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		JFrame f = new JFrame("title");
		f.setDefaultCloseOperation(3);
		JInternalFrame fen = new JInternalFrame("coucuo");
		
		
		
		//fen.setDefaultCloseOperation(3);
		f.add(fen);
		fen.setVisible(true);
		f.setVisible(true);
	}
		
}
