package net.argus.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.argus.file.FileError;

public class Error {
	
	public static void createErrorFileLog(Throwable e) {
		FileError errorFile = new FileError("err" + new SimpleDateFormat("YYMMddHHmm").format(new Date()));
		try {errorFile.writeError(e);}
		catch(IOException e1) {e1.printStackTrace();}
	}

}
