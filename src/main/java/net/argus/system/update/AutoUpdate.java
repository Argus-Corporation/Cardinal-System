package net.argus.system.update;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.argus.cjson.CJSON;
import net.argus.cjson.CJSONParser;
import net.argus.file.CJSONFile;
import net.argus.file.FileManager;
import net.argus.gui.OptionPane;
import net.argus.gui.splash.SplashScreen;
import net.argus.io.download.Download;
import net.argus.system.Copy;
import net.argus.system.SystemProcess;
import net.argus.system.Temp;
import net.argus.system.UserSystem;
import net.argus.util.Version;
import net.argus.util.Version.State;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class AutoUpdate {
	
	private CJSON manifest;
	
	private String version;
	
	public AutoUpdate(CJSONFile manifest) {
		this.manifest = CJSONParser.getCJSON(manifest);
	}
	
	public Version getCurrentVersion() {
		return new Version(manifest.getString("manifest.version"));
	}
	
	public Version getLatestVersion() {
		CJSONFile file = downloadManifest();
		
		if(file == null)
			return null;
		
		CJSON newManifest = CJSONParser.getCJSON(file);
		version = newManifest.getString("manifest.version");
		
		return new Version(version);
	}
	
	public boolean isLatestVersion() {
		Version newVersion = getLatestVersion();
		
		if(newVersion == null)
			return true;
		
		if(getCurrentVersion().getState(newVersion) == State.INFERIOR) {
			Debug.log("New version available: " + newVersion);
			return false;
		}
		return true;
	}
	
	public void check() {
		check(null);
	}
	
	public void check(SplashScreen splashScreen) {
		if(!isLatestVersion()) {			
			if(splashScreen != null) splashScreen.hideSplash();
			
			int result = showDialog(UIManager.get("Text.newVersion"));
			
			if(result == JOptionPane.YES_OPTION)
				UserSystem.update.downloadNewVersion();
			else
				if(splashScreen != null) splashScreen.setVisible(true);
				
		}
	}
	
	public int showDialog(Object text) {
		return showDialog(text, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	}
	
	public int showDialog(Object text, int option, int messageType) {
		return OptionPane.showConfirmDialog(null, text, "Update", option, messageType);
	}
	
	public void downloadNewVersion() {
		try {Copy.copy(Temp.getTempDir() + "/manifest.cjson", FileManager.getMainPath() + "/manifest_temp.cjson");}
		catch (IOException e1) {e1.printStackTrace();}
		String args = "java -jar \"" + FileManager.getMainPath() + "/Update.jar\" -manifest \"" + FileManager.getMainPath() + "/manifest_temp.cjson\" "
				+ "-return \"" + System.getProperty("name") + ".jar\"";
		
		SystemProcess systemProcess = new SystemProcess(args);
		
		try {systemProcess.start();}
		catch(IOException e) {e.printStackTrace();}
    	
    	UserSystem.exit(0);
    	
	}
	
	private CJSONFile downloadManifest() {
		try {
			String mainUrl = manifest.getString("download.url");
			
			URL url = new URL(mainUrl + "/manifest.cjson");
			byte[] data = Download.get(url);
				
			File fileOut = new File(Temp.getTempDir() + "/");
			
			if(!fileOut.exists())
				fileOut.mkdirs();
				
			fileOut = new File(fileOut.getAbsolutePath() + "/manifest.cjson");
			fileOut.createNewFile();

			DataOutputStream out = new DataOutputStream(new FileOutputStream(fileOut));
			
			out.write(data);
			out.close();
			
		}catch(IOException e) {Debug.log("Error: unable to download update manifest", Info.ERROR); return null;}
		
		return new CJSONFile(new File(Temp.getTempDir() + "/manifest.cjson"));
	}

}
