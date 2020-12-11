package net.argus.example;

import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Test {
	
	public static void main(String[] args) throws IOException {
		/*Socket cli = new Socket("192.168.1.69", 4747);
		
		BufferedReader r = new BufferedReader(new InputStreamReader(cli.getInputStream()));
		
		String msg = "";
		while((msg = r.readLine()) != null) {
			System.out.println(msg);
		}*/
		
		JFrame fen = new JFrame("test");
		fen.setDefaultCloseOperation(3);
		fen.setSize(1200, 700);
		
		JEditorPane pan = new JEditorPane(new URL("http://192.168.1.69:4747"));
		JScrollPane sp = new JScrollPane(pan);
		
		fen.add(sp);
		
		fen.setVisible(true);
	}

}
