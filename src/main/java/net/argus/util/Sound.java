package net.argus.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.argus.file.CardinalFile;
import net.argus.file.Properties;

@Deprecated
public class Sound {
	
	private static AudioFormat format;
	  
	private static byte[] samples;
	  
	private static String path;
	  
	public Sound(String fileName, String rep, String extention, Properties config) {
		path = CardinalFile.valueOf(getClass().getResource("/assets/images/google.png").toString());
		path = path.substring(path.indexOf("/") + 1, path.lastIndexOf(config.getString("project.name")));
		path = path + rep + "/" + fileName+ "." + extention;
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(path));
			format = stream.getFormat();
			samples = getSamples(stream);
		}catch(UnsupportedAudioFileException e) {
			e.printStackTrace();
	    }catch(IOException e) {
	    	e.printStackTrace();
	    } 
	}
	  
	  public byte[] getSamples() {
		  return samples;
	  }
	  
	  public static byte[] getSamples(AudioInputStream stream) {
		  int length = (int)(stream.getFrameLength() * format.getFrameSize());
		  byte[] samples = new byte[length];
		  DataInputStream in = new DataInputStream(stream);
		  try {
			  in.readFully(samples);
		  }catch(IOException e) {
	    	e.printStackTrace();
		  } 
		  return samples;
	  }
	  
	  public void play(InputStream source) {
		  new Thread(new Runnable() {
			@Override
			public void run() {
				SourceDataLine line;
				int bufferSize = format.getFrameSize() * java.lang.Math.round(format.getSampleRate() / 10.0F);
				byte[] buffer = new byte[bufferSize];
				try {
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
					line = (SourceDataLine)AudioSystem.getLine(info);
					line.open(format, bufferSize);
				}catch(LineUnavailableException e) {
					e.printStackTrace();
					return;
				} 
				line.start();
				try {
					int numBytesRead = 0;
					while(numBytesRead != -1) {
						numBytesRead = source.read(buffer, 0, buffer.length);
						if(numBytesRead != -1)
							line.write(buffer, 0, numBytesRead); 
					} 
				}catch(IOException e) {
					e.printStackTrace();
				} 
				line.drain();
				line.close();
			}
		}).start();
	  }
	  
	  public static void newSound(String fileName, String rep, String extention, Properties config) {
		  Sound sound = new Sound(fileName, rep, extention, config);
		  InputStream stream = new ByteArrayInputStream(sound.getSamples());
		  sound.play(stream);
	  }
	  
	  public static void main(String[] args) {
		  Properties config = new Properties("config", "bin");
		  newSound("navigationStart", "res/sound", "wav", config);
	}
}
