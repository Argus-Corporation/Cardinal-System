package net.argus.system.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RunningProgram {
	
	private Process process;
	
	private Thread read;
	private Thread write;
	private Thread writeError;
	
	public RunningProgram(Process process) {
		this.process = process;
		
		read = new Thread(getReadConsoleInput());
		read.setName("reader");
		
		write = new Thread(getWriteConsoleOutput());
		write.setName("writer");
		
		writeError = new Thread(getWriteErrorConsoleOutput());
		writeError.setName("writer error");
	}
	
	public void writeConsoleOutput() {write.start();}
	public void writeErrorConsoleOutput() {writeError.start();}
	
	public void readConsoleInput() {read.start();}
	
	@SuppressWarnings("deprecation")
	private Runnable getWriteConsoleOutput() {
		return () -> {
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			
			try {
				while((line = in.readLine()) != null)
					System.out.println(line);
			}catch(IOException e) {}
			read.stop();
		};
	}
	
	@SuppressWarnings("deprecation")
	private Runnable getWriteErrorConsoleOutput() {
		return () -> {
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line;
			
			try {
				while((line = in.readLine()) != null)
					System.err.println(line);
			}catch(IOException e) {}
			read.stop();
		};
	}
	
	@SuppressWarnings("resource")
	private Runnable getReadConsoleInput() {
		return () -> {
			PrintStream stream = new PrintStream(process.getOutputStream());
			
			Scanner scan = new Scanner(System.in);
			try {
				while(true) {
					stream.println(scan.nextLine());
					stream.flush();
				}
			}catch(NoSuchElementException e) {}
		};
	}
	
	public void waitFor() throws InterruptedException {process.waitFor();}
	
	public int exitValue() {return process.exitValue();}

}
