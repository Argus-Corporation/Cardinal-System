package net.argus.image.cr;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CRPosition extends CRImage {
	
	private int w, h;

	public CRPosition(File file) {
		super(0, file);
	}
	
	public void find() throws IOException {
		img = ImageIO.read(file);
		
		w = img.getWidth();
		h = img.getHeight();
		
		for(int i = 0, x = 0, y = 0; i < w * h; i++, y += x == w-1 ? 1 : 0, x = x < w-1 ? x + 1: 0) {
			
			if(getColor(x, y) == BLACK && getColor(x + 1, y) == BLACK && getColor(x + 2, y) == BLACK &&
					getColor(x, y + 1) == BLACK && getColor(x + 1, y + 1) == BLACK &&
					getColor(x + 2, y + 1) == BLACK &&
					getColor(x, y + 2) == BLACK && getColor(x + 1, y + 2) == BLACK &&
					getColor(x + 2, y + 2) == BLACK) {
				
				System.out.println(x + "  " + y);
				
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		CRPosition pos = new CRPosition(new File("D:\\Django\\Document 1\\Git\\res\\pageCode.png"));
		pos.find();
	}
	
}
