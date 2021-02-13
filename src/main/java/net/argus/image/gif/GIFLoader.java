package net.argus.image.gif;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.argus.image.ImageManager;

public class GIFLoader {
	
	public static GIF load(String file) {
		return load(new File(file));
	}
	
	public static GIF load(File file) {
		BufferedInputStream in = null;
		
		ImageInputStream stream = null;
		try {
			in = new  BufferedInputStream(new FileInputStream(file));
			stream = ImageIO.createImageInputStream(in);
		}catch(IOException e) {
			return null;
		}
		
		ImageReader r = ImageManager.getReaderByMIMEType("image/gif");
		if(r == null) return null;
		
		List<BufferedImage> img = new ArrayList<BufferedImage>();
		try {            
			r.setInput(stream, true, false);
			
			try {
				for(int i = 0;; i++)
					img.add(r.read(i));
				
			}catch(Exception e2) {}
			
			r.dispose();
			in.close();
		}catch(Exception e) {return null;}

		return new GIF(img.toArray(new BufferedImage[img.size()]));
	}

}
