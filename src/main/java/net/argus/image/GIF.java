package net.argus.image;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class GIF {
	
	public static BufferedImage[] getImages(String f) {
		BufferedInputStream in = null;
		
		//stream image
		ImageInputStream stream = null;
		try {
			in = new  BufferedInputStream(new FileInputStream(new File(f)));
			stream = ImageIO.createImageInputStream(in);
		}catch(IOException e) {
			return null;
		}
		
		//reader d'image
		ImageReader r = getReader("GIF");
		if(r == null) return null;
		
		
		//tableau dynamique des images lues
		ArrayList<BufferedImage> img = new ArrayList<BufferedImage>();
		try {            
			r.setInput(stream, true, true);
			ImageReadParam param = r.getDefaultReadParam();
			
			//lecture des images

			try {
				int i = 0;
				//on lit tant qu'il y a pas d'exceptions !
				while(true) {
					//lecture et ajout
					img.add(r.read(i, param));
					i++;
				}
			}catch(Exception e2) {}
	            
	            
			r.dispose();
			in.close();
		}catch(Exception e) {
			return null;
		}

		//retourne un tableau
		return  img.toArray(new BufferedImage[img.size()]);
	}
	
	private static ImageReader getReader(String format) {
		Iterator<?> readers = ImageIO.getImageReadersBySuffix(format);
		if(readers.hasNext()) {
			ImageReader reader = (ImageReader) readers.next();
			return reader;
		}
		return null;
	}

}
