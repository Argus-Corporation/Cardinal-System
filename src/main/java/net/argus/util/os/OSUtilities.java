package net.argus.util.os;

import java.io.IOException;

import net.argus.system.OS;
import net.argus.util.os.linux.LinuxUtilities;
import net.argus.util.os.windows.WindowsUtilities;

public abstract class OSUtilities {
	
	public static final WindowsUtilities WINDOWS_UTILITIES = new WindowsUtilities();
	public static final LinuxUtilities LINUX_UTILITIES = new LinuxUtilities();
	
	public abstract Process getShellProcess() throws IOException;
	public abstract Process getShellProcess(String[] args) throws IOException;
	
	public abstract String getDesktopPath();
	
	public static OSUtilities getOSUtilities() {
		switch(OS.currentOS()) {
			case WINDOWS:
				return WINDOWS_UTILITIES;
			case LINUX:
				return LINUX_UTILITIES;
		}
		return null;
	}

}
