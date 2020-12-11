package net.argus.sound;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import net.argus.util.debug.Debug;

@Deprecated
public class Input extends Sound {
 
    private TargetDataLine line;
    
    public Input(String path, AudioFileFormat.Type fileType) {
		this.file = new File(path + "." + fileType.getExtension());
		this.fileType = fileType;
	}
    
    private Runnable getRecordRunable() {
    	return new Runnable() {
    		public void run() {
    			try {
    				AudioFormat format = getAudioFormat();
		            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		 
		            // checks if system supports the data line
		            if (!AudioSystem.isLineSupported(info)) {
		            	Debug.log("Line not supported");
		            	stop();
		            	return;
		            }
		            line = (TargetDataLine) AudioSystem.getLine(info);
		            line.open(format);
		            line.start();   // start capturing
		 
		         //   AudioInputStream ais = new AudioInputStream(line);
		            Debug.log("Start recording");
		            // start recording
		      //     AudioSystem.write(ais, fileType, file);
		            
		 
		        } catch (LineUnavailableException e) {}
			}
		};
    }
    
    /**
     * Captures the sound and record into a WAV file
     */
    public void rec() {
       Thread rec = new Thread(getRecordRunable());
       rec.setName("recording");
       
       rec.start();
    }
    
    /**
     * Closes the target data line to finish capturing and recording
     */
    private void stop() {
       if(line != null) {
    	   line.stop();
    	   line.close();
    	   Debug.log("Finished");
       }
    }
	
	public static void main(String[] args) throws InterruptedException {
		Input recorder = new Input("D:\\Django\\bureau 1\\testLol", AudioFileFormat.Type.WAVE);
 
        // start recording
        recorder.rec();
        
        Thread.sleep(5000);
        
        recorder.stop();
	}

}
