package net.argus.system;

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
import net.argus.util.DoubleStock;
import net.argus.util.Version;
import net.argus.util.Version.State;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class AutoUpdate {
			
	public AutoUpdate() {}
	
	public DoubleStock<Version, Version> getLatestVersion() {
		CJSONFile file = downloadManifest();
		
		if(file == null)
			return null;
		
		CJSON newManifest = CJSONParser.getCJSON(file);
		String version = newManifest.getString("manifest.version");
		String debug = newManifest.getString("manifest.debug");
		
		return new DoubleStock<Version, Version>(new Version(version), new Version(debug));
	}
	
	public UpdateInfo isLatestVersion() {
		DoubleStock<Version, Version> versions = getLatestVersion();
		
		Version newVersion = versions.getFirst();
		Version newDebug = versions.getSecond();
		
		if(newVersion == null)
			return new UpdateInfo(true);
		
		if(newDebug == null)
			return new UpdateInfo(true);
		
		Version currentVersion = UserSystem.getCurrentVersion();
		Version currentDebug = UserSystem.getCurrentDebug();
		
		if(newVersion.getState(currentVersion) == State.SUPERIOR ||
				(newVersion.getState(currentVersion) == State.EQUALS && newDebug.getState(currentDebug) == State.SUPERIOR)) {
			Debug.log("New version available: " + newVersion + "." + newDebug);
			return new UpdateInfo(false, newVersion, newDebug);
		}
		
		return new UpdateInfo(true);
	}
	
	public void check() {
		check(null);
	}
	
	public void check(SplashScreen splashScreen) {
		UpdateInfo info = isLatestVersion();
		if(!info.isLatest()) {			
			if(splashScreen != null) splashScreen.hideSplash();
			
			int result = showDialog(info);
			
			if(result == JOptionPane.YES_OPTION)
				downloadNewVersion();
			else
				if(splashScreen != null) splashScreen.setVisible(true);
				
		}
	}
	
	public int showDialog(UpdateInfo info) {
		if(info.isLatest())
			return -1;
		
		return showDialog(UIManager.get("Text.newVersion") + "  " + info.getNewVersion() + "." + info.getNewDebug(), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	}
	
	public int showDialog(Version newVersion, Version newDebug) {
		return showDialog(UIManager.get("Text.newVersion") + "  " + newVersion + "." + newDebug, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	}
	
	public int showDialog(Object text, int option, int messageType) {
		return OptionPane.showDialog(null, text, "Update", option, messageType);
	}
	
	public void downloadNewVersion() {
		try {Copy.copy(Temp.getTempDir() + "/manifest.cjson", FileManager.getMainPath() + "/manifest_temp.cjson");}
		catch (IOException e1) {e1.printStackTrace();}
		String args = "java -jar \"" + FileManager.getMainPath() + "/Update.jar\" -manifest \"" + FileManager.getMainPath() + "/manifest_temp.cjson\"";
		
		SystemProcess systemProcess = new SystemProcess(args);
		
		try {systemProcess.start();}
		catch(IOException e) {e.printStackTrace();}
    	
    	UserSystem.exit(0);
    	
	}
	
	private CJSONFile downloadManifest() {
		try {
			CJSON manifest = UserSystem.getManifest();
			if(manifest == null)
				return null;
			
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
