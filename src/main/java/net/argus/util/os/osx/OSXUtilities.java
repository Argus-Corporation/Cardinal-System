package net.argus.util.os.osx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.apple.eio.FileManager;

import net.argus.util.ArrayManager;
import net.argus.util.os.OSUtilities;

public class OSXUtilities extends OSUtilities {

	@Override
	public Process getShellProcess() throws IOException {
		return getShellProcess(null);
	}

	@Override
	public Process getShellProcess(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		args = ArrayManager.add(new String[] {"/bin/bash", "-c"}, args);
		
		return runtime.exec(args);
	}

	@Override
	public String getDesktopPath() {
		try {
			int desktopOSType = FileManager.OSTypeToInt("desk");
			String desktopFolderName = FileManager.findFolder(desktopOSType);
			
			return new File(desktopFolderName).getAbsolutePath();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		return new File(".").getAbsolutePath();
	}

}
