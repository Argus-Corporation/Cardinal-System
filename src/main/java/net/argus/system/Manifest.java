package net.argus.system;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;

import net.argus.file.JarFile;

public class Manifest {
	
	private JarFile jar;
	private Attributes attribute;
	
	public Manifest(File file) throws IOException {
		jar = new JarFile(file.getPath());
		attribute = jar.getMainAttributes();
	}
	
	public String getValue(String name) {
		return attribute.getValue(name);
	}

}
