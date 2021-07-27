package net.argus.sound;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio implements Cloneable {
	
	private URL path;
	private AudioInputStream audioIn;
	private Clip clip;
	private FloatControl gain;
	private boolean played;
	private boolean opened;

	public Audio(String path) {
		try {
			this.path = new File(path).toURI().toURL();
			this.audioIn = AudioSystem.getAudioInputStream(this.path);
			this.clip = AudioSystem.getClip();
			
		}catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();}
	}
	
	public void play() {
		try {
			if(!opened) {
				clip.open(audioIn);
				opened = true;
			}
			if(clip.getMicrosecondPosition() != 0)
				stop();
			
			clip.start();
			played = true;
		}catch(LineUnavailableException | IOException e) {e.printStackTrace();}
	}
	
	public void pause() {
		clip.stop();
		played = false;
	}
	
	public void resume() {
		clip.start();
		played = true;
	}
	
	public void stop() {
		clip.setMicrosecondPosition(0);
		clip.stop();

		played = false;
	}
	
	public void setLevel(double level) {
		this.gain = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
		float dB = (float) (Math.log(level) / Math.log(10) * 20);
		gain.setValue(dB);
	}
	
	public float getLevel() {
		this.gain = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
		return gain.getValue();
	}
	
	@Override
	public Audio clone() throws CloneNotSupportedException {
		return (Audio) super.clone();
	}
	
	public boolean isPlaying() {return played;}
	public Clip getClip() {return clip;}

}
