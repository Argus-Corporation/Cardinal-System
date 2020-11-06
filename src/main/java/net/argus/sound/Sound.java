package net.argus.sound;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public abstract class Sound {
	
	protected File file;
	protected AudioFileFormat.Type fileType;
	
	protected float sampleRate = 16000;
	protected int sampleSizeInBits = 8;
	protected int channels = 2;
	protected boolean signed = true;
	protected boolean bigEndian = true;
	
	protected AudioFormat getAudioFormat() {
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
	
	
	public File getFile() {return file;}
	public AudioFileFormat.Type getFileType() {return fileType;}
	public float getSampleRate() {return sampleRate;}
	public int getSampleSizeInBits() {return sampleSizeInBits;}
	public int getChannels() {return channels;}
	public boolean isSigned() {return signed;}
	public boolean isBigEndian() {return bigEndian;}

	public void setFile(File file) {this.file = file;}
	public void setFileType(AudioFileFormat.Type fileType) {this.fileType = fileType;}
	public void setSampleRate(float sampleRate) {this.sampleRate = sampleRate;}
	public void setSampleSizeInBits(int sampleSizeInBits) {this.sampleSizeInBits = sampleSizeInBits;}
	public void setChannels(int channels) {this.channels = channels;}
	public void setSigned(boolean signed) {this.signed = signed;}
	public void setBigEndian(boolean bigEndian) {this.bigEndian = bigEndian;}
	

}
