package net.argus.image.cr;

import java.io.File;
import java.io.IOException;

public class CR {

	public static void main(String[] args) throws IOException {
		CRImage cr = new CRImage(1, new File("D:\\Django\\Document 1\\Git\\Eclipse\\test.png"));
		//cr.write("Test");
		System.out.println(cr.read());
		
		//System.out.println(new Binary("1010").toInt());
	}

}
