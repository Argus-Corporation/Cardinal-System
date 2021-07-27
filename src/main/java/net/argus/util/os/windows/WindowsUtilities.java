package net.argus.util.os.windows;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.filechooser.FileSystemView;

import net.argus.util.ArrayManager;
import net.argus.util.os.OSUtilities;

public class WindowsUtilities extends OSUtilities {
	
	public static final String REGSTR_TOKEN = "REG_SZ";
	
	public static Process getProcess(String query) throws IOException {
		return Runtime.getRuntime().exec(query);
	}
	
	public static String getRegisterReturn(String path, String key) throws IOException {
		Process p = getProcess("reg query \"" + path + "\" /v \"" + key + "\"");
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String query = "";
		String line = "";
		
		while((line = reader.readLine()) != null)
			query += line;
		
		int i = query.indexOf(REGSTR_TOKEN);

		if(i == -1)
			return null;
		
		return query.substring(i + REGSTR_TOKEN.length()).trim();
	}
	
	@Override
	public String getDesktopPath() {
		FileSystemView view = FileSystemView.getFileSystemView();
		File file = view.getHomeDirectory();
		
		return file.getAbsolutePath();
	}

	@Override
	public Process getShellProcess() throws IOException {
		return getShellProcess(null);
	}

	@Override
	public Process getShellProcess(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		args = ArrayManager.add(new String[] {"cmd.exe"}, args);
		return runtime.exec(args);
	}

}
