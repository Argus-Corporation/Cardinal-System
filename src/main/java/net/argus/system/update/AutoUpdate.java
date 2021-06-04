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
import net.argus.io.download.Download;
import net.argus.system.Copy;
import net.argus.system.InitializationSplash;
import net.argus.system.SystemProcess;
import net.argus.system.Temp;
import net.argus.system.UserSystem;
import net.argus.util.debug.Debug;

public class AutoUpdate {
	
	private CJSON manifest;
	
	private String version;
	
	public AutoUpdate(CJSONFile manifest) {
		this.manifest = CJSONParser.getCJSON(manifest);
	}
	
	public boolean isLatestVersion() {
		CJSON newManifest = CJSONParser.getCJSON(downloadManifest());
		String currentVersion = manifest.getString("manifest.version");
		version = newManifest.getString("manifest.version");
		
		Debug.log("Current version: " + currentVersion);
		
		return !currentVersion.equals(version);
	}
	
	public void check() {
		boolean newVersion = isLatestVersion();
		if(newVersion) {
			Debug.log("New version available");
			
			if(InitializationSplash.getSplash() != null) InitializationSplash.getSplash().hideSplash();
			
			int result = OptionPane.showConfirmDialog(null, UIManager.get("Text.newVersion"), "New Version", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
			if(result == JOptionPane.YES_OPTION)
				UserSystem.update.downloadNewVersion();
			
			else
				if(InitializationSplash.getSplash() != null) InitializationSplash.getSplash().setVisible(true);
				
		}
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
			
			if(!fileOut.exists()) {
				fileOut.mkdirs();
				fileOut = new File(fileOut.getAbsolutePath() + "/manifest.cjson");
				fileOut.createNewFile();
			}
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(fileOut));
			
			out.write(data);
			out.close();
		}catch(IOException e) {e.printStackTrace();}
		
		return new CJSONFile(new File(Temp.getTempDir() + "/manifest.cjson"));
	}

}
