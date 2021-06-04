package net.argus.util.debug;

import java.io.PrintStream;

public class PrintLogger implements Logger {
	
	private PrintStream outStream;
	private PrintStream errStream;
	
	public PrintLogger(PrintStream outStream, PrintStream errStream) {
		this.outStream = outStream;
		this.errStream = errStream;
	}
	
	public PrintLogger(PrintStream outStream) {
		this.outStream = outStream;
	}

	@Override
	public void log(String text) {
		outStream.println(text);
		outStream.flush();
	}

	@Override
	public void errorLog(String text) {
		if(errStream != null) {
			errStream.println(text);
			errStream.flush();
		}else
			log(text);
	}

}
