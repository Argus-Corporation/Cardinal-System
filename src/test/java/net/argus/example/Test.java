package net.argus.example;

import java.net.MalformedURLException;

import javax.swing.JFrame;

import net.argus.file.cjson.CJSONFile;
import net.argus.file.cjson.CJSONPareser;
import net.argus.gui.tree.CardinalTree;
import net.argus.gui.tree.Tree;
import net.argus.gui.tree.TreeListener;
import net.argus.system.InitializedSplash;
import net.argus.system.InitializedSystem;
import net.argus.util.Display;

public class Test {
	
	public static void main(String[] args) throws MalformedURLException {
		InitializedSystem.initSystem(args, new InitializedSplash("res/logo.png", Display.getWidhtDisplay() - 50, 0));
		InitializedSplash.getSplash().exit();
		JFrame fen = new JFrame();
		fen.setDefaultCloseOperation(3);
		
		Tree tree = new Tree(new CardinalTree(CJSONPareser.parse(new CJSONFile("config", "bin")), "Preference"));
		fen.add(tree);
		
		tree.addTreeListener(new TreeListener() {
			
			@Override
			public void selectedItemValue(int id) {
				System.out.println(id);
			}
		});
		
		tree.setSelection(1);
		fen.pack();
		
		fen.setVisible(true);
	}

}
