package net.argus.system.update;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import net.argus.file.FileManager;
import net.argus.file.cjson.CJSON;
import net.argus.file.cjson.CJSONFile;
import net.argus.file.cjson.CJSONPareser;
import net.argus.gui.OptionPane;
import net.argus.io.Download;
import net.argus.system.InitializedSplash;
import net.argus.system.SystemProcess;
import net.argus.system.Temp;
import net.argus.system.UserSystem;
import net.argus.util.debug.Debug;

public class AutoUpdate {
	
	private CJSON manifest;
	
	public AutoUpdate(CJSONFile manifest) {
		this.manifest = CJSONPareser.parse(manifest);
	}
	
	public boolean isLatestVersion() {
		CJSON newManifest = CJSONPareser.parse(downloadManifest());
		String currentVersion = manifest.getObject("manifest").getValue("version").toString();
		String version = newManifest.getObject("manifest").getValue("version").toString();
		
		Debug.log("Current version: " + currentVersion);
		
		return !currentVersion.equals(version);
	}
	
	public void check() {
		boolean newVersion = isLatestVersion();
		if(newVersion) {
			Debug.log("New version available");
			
			if(InitializedSplash.getSplash() != null) InitializedSplash.getSplash().hideSplash();
			int result = OptionPane.showConfirmDialog(null, "Une nouvelle version est disponible souhaitez vous la telecharger", "Update", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
			if(result == JOptionPane.YES_OPTION)
				UserSystem.update.downloadNewVersion();
			
			else
				if(InitializedSplash.getSplash() != null) InitializedSplash.getSplash().setVisible(true);
				
		}
	}
	
	public void downloadNewVersion() {
		String args = "java -jar \"" + FileManager.getMainPath() + "/Update.jar\" -manifest " + Temp.getTempDir() + "/manifest.cjson "
				+ "-return \"" + System.getProperty("name") + ".jar\" ";
		SystemProcess systemProcess = new SystemProcess(args);
		
		try {systemProcess.start();}
		catch(IOException e) {e.printStackTrace();}
    	
    	UserSystem.exit(0);
    	
	}
	
	private CJSONFile downloadManifest() {
		try {
			String mainUrl = manifest.getObject("download").getValue("url").toString();
			String folder = manifest.getObject("download").getValue("folder").toString();
			
			URL url = new URL(mainUrl + "/" + folder + "/");
			Download d = new Download(url);
			
			d.setNewThread(false);
			new File(Temp.getTempDir()).mkdirs();
			d.download("manifest.cjson", new File(Temp.getTempDir() + "/"));
		}catch(MalformedURLException e) {}
		
		return new CJSONFile(new File(Temp.getTempDir() + "/manifest.cjson"));
	}

}
