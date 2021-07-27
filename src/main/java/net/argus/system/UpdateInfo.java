package net.argus.system;

import net.argus.util.Version;

public class UpdateInfo {
	
	private boolean latest;
	private Version newVersion, newDebug;
	
	public UpdateInfo(boolean latest) {
		this(latest, null, null);
	}
	
	public UpdateInfo(boolean latest, Version newVersion, Version newDebug) {
		this.latest = latest;
		this.newVersion = newVersion;
		this.newDebug = newDebug; 
	}
	
	public boolean isLatest() {return latest;}
	
	public Version getNewVersion() {return newVersion;}
	public Version getNewDebug() {return newDebug;}

}
