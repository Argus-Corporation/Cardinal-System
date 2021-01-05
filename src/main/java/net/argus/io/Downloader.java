package net.argus.io;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import net.argus.file.cjson.CJSON;
import net.argus.file.cjson.CJSONFile;
import net.argus.file.cjson.CJSONObject;
import net.argus.file.cjson.CJSONPareser;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class Downloader {
	
	private CJSONObject mainObj;
	
	private Download dow;
	
	public Downloader(CJSON file) throws MalformedURLException {
		this.mainObj = file.getObject("download");
		
		dow = new Download(new URL(mainObj.getValue("url").toString() + mainObj.getValue("folder").toString()));
	}
	
	public void download(File dowPath) {
		for(CJSONObject obj : mainObj.getArrayValue("file"))
			dow.download(obj.toString(), dowPath);
	}
	
	public void setMultiDownload(boolean multiDown) {dow.setMultiDownload(multiDown);}
	
	public static void main(String[] args) throws MalformedURLException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		Debug.addBlackList(ThreadManager.THREAD_MANAGER);
		
		Downloader dow = new Downloader(CJSONPareser.parse(new CJSONFile(new File("D:\\Django\\Document 1\\Git\\Installing\\resource\\manifest.cjson"))));
		dow.setMultiDownload(true);
		
		dow.download(new File("D:\\Django\\Document 1\\Git\\Installing\\resource\\"));
	}

}
