package net.argus.util.os.osx;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import net.argus.util.os.OSUtilities;

@Deprecated
public class OSXUtilities extends OSUtilities {

	@Override
	public Process getShellProcess() throws IOException {
		return getShellProcess(null);
	}

	@Override
	public Process getShellProcess(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		
		return runtime.exec(args);
	}

	@Override
	public String getDesktopPath() {
		FileSystemView view = FileSystemView.getFileSystemView();
		File file = view.getHomeDirectory();
		
		return file.getAbsolutePath();
	}

}
