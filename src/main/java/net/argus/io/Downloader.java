package net.argus.io;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import net.argus.file.cjson.CJSON;
import net.argus.file.cjson.CJSONFile;
import net.argus.file.cjson.CJSONObject;
import net.argus.file.cjson.CJSONPareser;
import net.argus.gui.DialogProgress;
import net.argus.system.InitializationSystem;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class Downloader {
	
	private CJSONObject mainObj;
	private Download dow;
	
	private DialogProgress progress;
	
	public Downloader(CJSON file) throws MalformedURLException {
		this.mainObj = file.getObject("download");
		
		dow = new Download(new URL(mainObj.getValue("url").toString() + mainObj.getValue("folder").toString()));
	}
	
	public void download(File dowPath) {
		if(progress != null) progress.show(0, getNumberFile(), "fichier telechargee sur", false);
		
		for(CJSONObject obj : mainObj.getArrayValue("file")) {
			dow.download(obj.toString(), dowPath);
			
			if(progress != null) progress.setValue(progress.getValue() + 1);
		}
		
		if(progress != null) progress.exit();
	}
	
	public void setDialogProgress(DialogProgress progress) {this.progress = progress;}
	
	public void setMultiDownload(boolean multiDown) {dow.setMultiDownload(multiDown);}
	public void setNewThread(boolean b) {dow.setNewThread(b);}
	
	public int getNumberFile() {return mainObj.getArrayValue("file").length;}
	
	public static void main(String[] args) throws MalformedURLException {
		InitializationSystem.initSystem(args);
		Debug.addBlackList(ThreadManager.THREAD_MANAGER);
		
		Downloader dow = new Downloader(CJSONPareser.parse(new CJSONFile(new File("D:\\Django\\Document 1\\Git\\Installing\\resource\\manifest.cjson"))));
		dow.setMultiDownload(true);
		
		dow.download(new File("D:\\Django\\Document 1\\Git\\Installing\\resource\\"));
	}

}
