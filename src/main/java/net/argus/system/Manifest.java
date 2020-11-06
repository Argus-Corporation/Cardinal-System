package net.argus.system;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class Manifest {
	
	private JarFile jar;
	private java.util.jar.Manifest manifestFile;
	private Attributes attribute;
	
	public Manifest(File file) throws IOException {
		jar = new JarFile(file);
		manifestFile = jar.getManifest();
		attribute = manifestFile.getMainAttributes();
	}
	
	public String getValue(String name) {
		return attribute.getValue(name);
	}

}
