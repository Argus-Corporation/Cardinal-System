package net.argus.image;

import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class ImageManager {
	
	public static ImageReader getReaderByMIMEType(String MIMEType) {
		return getReader(ImageIO.getImageReadersByMIMEType(MIMEType));
	}
	
	public static ImageReader getReaderBySuffix(String suffix) {
		return getReader(ImageIO.getImageReadersBySuffix(suffix));
	}
	
	public static ImageReader getReader(Iterator<ImageReader> readers) {
		if(readers.hasNext()) {
			ImageReader reader = (ImageReader) readers.next();
			return reader;
		}
		return null;
	}

}
