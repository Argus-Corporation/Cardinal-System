package net.argus.file;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Loggeur extends AbstractFileSave {
	
	public static final String EXTENTION = "log";
	
	private boolean enable = true;

	public Loggeur(String rep) {
		super(new SimpleDateFormat("YYYY-MM-dd_HH-mm-ss").format(new Date()), EXTENTION, rep);
		
	}
	
	public void addLog(String log) throws IOException {
		if(enable) write(log);
	}
	
	public void setEnable(boolean enable) {this.enable = enable;}

}
