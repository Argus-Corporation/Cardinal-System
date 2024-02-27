package net.argus.example;

import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import net.argus.exception.InstanceException;
import net.argus.instance.CardinalProgram;
import net.argus.instance.Program;

@net.argus.annotation.Test(info = "test class")
@Program(instanceName = "test")
public class Test extends CardinalProgram {

	public void main(String[] args) throws InstanceException {
		JFrame fen = new JFrame();
		fen.setDefaultCloseOperation(3);
		fen.setSize(700, 1200);
		fen.setLocationRelativeTo(null);
		
		try {
			JEditorPane pan = new JEditorPane(); 
			pan.setEditable(false);
			pan.setPage(new URL("http://localhost:8100/log.html"));
	        JScrollPane scrollPane = new JScrollPane(pan);
			fen.add(scrollPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fen.setVisible(true);
		
				
	//	System.out.println(Counter.countOccurrences("hello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis t", 'h'));
		
		/*int[] i = new int[] {1, 2, 3, 4};
		int[] j = new int[] {5, 6, 7, 8};
		int[] k = new int[8];
		
		System.arraycopy(i, 2, k, 2, i.length - 2);
		System.arraycopy(j, 0, k, i.length, j.length);
 		for(int l : k)
 			System.out.println(l);*/
	}
		
}
