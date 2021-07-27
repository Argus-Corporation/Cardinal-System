package net.argus.image.cr;

import java.io.File;
import java.io.IOException;

public class CR {

	public static void main(String[] args) throws IOException {
		CRImage cr = new CRImage(1, new File("C:\\Users\\Django\\Desktop\\test.png"));
		//cr.write("@");
		System.out.println(cr.read());
		
		//System.out.println(new Binary("1010").toInt());
	}

}
