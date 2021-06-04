package net.argus.file;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.argus.exception.InstanceException;
import net.argus.instance.Instance;
import net.argus.system.InitializationSystem;
import net.argus.system.UserSystem;
import net.argus.util.debug.Logger;

public class FileLogger extends CardinalFile implements Logger {
	
	public static final String EXTENTION = "log";
	
	public FileLogger(String rep) throws InstanceException {
		super(new SimpleDateFormat("YYYY-MM-dd_HH-mm-ss").format(new Date()), EXTENTION, rep, Instance.SYSTEM);
		
	}
	
	@Override
	public void log(String log) {
		try {
			if(InitializationSystem.isSystemInitialized() && UserSystem.getBooleanProperty("log"))
				writeAppend(log);
		}catch(IOException e) {e.printStackTrace();}
	}

	@Override
	public void errorLog(String text) {
		log(text);
	}
	
}
