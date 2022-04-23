package net.argus.system.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import net.argus.event.program.EventProgram;
import net.argus.event.program.ProgramEvent;
import net.argus.event.program.ProgramListener;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class RunningProgram {
	
	private Process process;
	
	private List<Thread> reads = new ArrayList<Thread>();
	private Thread write;
	private Thread writeError;
	
	private EventProgram event = new EventProgram();
	
	public RunningProgram(Process process) {
		this.process = process;
		
		write = new Thread(getWriteConsoleOutput());
		write.setName("writer");
		
		writeError = new Thread(getWriteErrorConsoleOutput());
		writeError.setName("writer error");
	}
	
	public void writeConsoleOutput() {write.start();}
	public void writeErrorConsoleOutput() {writeError.start();}
	
	public void readConsoleInput() {readConsoleInput(System.in);}
	public void readConsoleInput(InputStream in) {
		Thread th = new Thread(getReadConsoleInput(in));
		th.setName("reader");
		reads.add(th);
		
		th.start();
	}
	
	private Runnable getWriteConsoleOutput() {
		return () -> {
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			
			try {
				while((line = in.readLine()) != null) {
					System.out.println(line);
					event.startEvent(EventProgram.OUTPUT, new ProgramEvent(line));
				}
			}catch(IOException e) {}
			readStop();
		};
	}
	
	private Runnable getWriteErrorConsoleOutput() {
		return () -> {
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line;
			
			try {
				while((line = in.readLine()) != null) {
					System.err.println(line);
					event.startEvent(EventProgram.OUTPUT, new ProgramEvent(line));
				}
			}catch(IOException e) {}
			readStop();
		};
	}
	
	@SuppressWarnings("resource")
	private Runnable getReadConsoleInput(InputStream in) {
		return () -> {		
			if(in == null)
				Debug.log("InputStream null", Info.ERROR);
			
			PrintStream stream = new PrintStream(process.getOutputStream());
			
			Scanner scan = new Scanner(in);
			try {
				while(true) {
					String nextLine = scan.nextLine();

					stream.println(nextLine);
					stream.flush();
					event.startEvent(EventProgram.INPUT, new ProgramEvent(nextLine));

				}
			}catch(NoSuchElementException e) {}
		};
	}
	
	@SuppressWarnings("deprecation")
	private void readStop() {
		for(Thread th : reads)
			if(th.isAlive())
				th.stop();
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		if(write.isAlive())
			write.stop();
		
		if(writeError.isAlive())
			writeError.stop();
		
		for(Thread read : reads) {
			if(read.isAlive())
				read.stop();
		}
		
		process.destroy();
		reads.clear();
	}
	
	public void waitFor() throws InterruptedException {process.waitFor();}
	
	public int exitValue() {return process.exitValue();}
	
	public void addProgramListener(ProgramListener listener) {event.addListener(listener);}

}
