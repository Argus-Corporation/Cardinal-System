package net.argus.util.os;

import java.io.IOException;

import net.argus.system.OS;
import net.argus.util.os.linux.LinuxUtilities;
import net.argus.util.os.osx.OSXUtilities;
import net.argus.util.os.windows.WindowsUtilities;

@SuppressWarnings("deprecation")
public abstract class OSUtilities {
	
	public static final WindowsUtilities WINDOWS_UTILITIES = new WindowsUtilities();
	public static final LinuxUtilities LINUX_UTILITIES = new LinuxUtilities();
	public static final OSXUtilities OSX_UTILITIES = new OSXUtilities();
	
	public abstract Process getShellProcess() throws IOException;
	public abstract Process getShellProcess(String[] args) throws IOException;
	
	public abstract String getDesktopPath();
	
	public static OSUtilities getOSUtilities() {
		switch(OS.currentOS()) {
			case WINDOWS:
				return WINDOWS_UTILITIES;
			case LINUX:
				return LINUX_UTILITIES;
			case OSX:
<<<<<<< Updated upstream
				return null;
=======
				return OSX_UTILITIES;
>>>>>>> Stashed changes
		}
		return null;
	}

}
