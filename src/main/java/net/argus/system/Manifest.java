package net.argus.system;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;

import net.argus.file.JarFile;

public class Manifest {
	
	private JarFile jar;
	private Attributes attribute;
	
	public Manifest(File file) throws IOException {
		this(new JarFile(file));
	}
	
	public Manifest(JarFile file) throws IOException {
		this.jar = file;
		attribute = this.jar.getMainAttributes();

	}
	
	public String getValue(String name) {
		return attribute.getValue(name);
	}
	
	public static Manifest getManifest() throws IOException {
		return new Manifest(new File(Launcher.getCodeSourceLocation().getPath()));
	}

}
