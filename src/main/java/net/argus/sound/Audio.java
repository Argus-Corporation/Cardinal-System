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

public class Audio {
	
	private URL path;
	private AudioInputStream audioIn;
	private Clip clip;
	private FloatControl gain;
	private boolean isPlay;
	private boolean isStoped;

	public Audio(String path) {
		try {
			this.path = new File(path).toURI().toURL();
			this.audioIn= AudioSystem.getAudioInputStream(this.path);
			this.clip = AudioSystem.getClip();
			
		}catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();}
	}
	
	public void play() {
		try {
			if(!isStoped) {
				clip.open(audioIn);				
			}
			clip.start();
			isPlay = true;
		}catch(LineUnavailableException | IOException e) {e.printStackTrace();}
	}
	
	public void pause() {
		clip.stop();
		isPlay = true;
	}
	
	public void resume() {
		clip.start();
		isPlay = true;
	}
	
	public void stop() {
		clip.setMicrosecondPosition(0);
		clip.stop();
		isStoped = true;
		isPlay = false;
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
	
	
	public boolean isPlay() {return isPlay;}
	public Clip getClip() {return clip;}

}
