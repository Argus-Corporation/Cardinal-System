package net.argus.util.os.linux;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import net.argus.util.ArrayManager;
import net.argus.util.os.OSUtilities;

public class LinuxUtilities extends OSUtilities {
	
	@Override
	public Process getShellProcess() throws IOException {
		return getShellProcess(null);
	}

	@Override
	public Process getShellProcess(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		args = ArrayManager.add(new String[] {"/bin/sh"}, args);
		return runtime.exec(args);
	}

	@Override
	public String getDesktopPath() {
		FileSystemView view = FileSystemView.getFileSystemView();
		File file = view.getHomeDirectory();
		
		return file.getAbsolutePath();
	}

}
