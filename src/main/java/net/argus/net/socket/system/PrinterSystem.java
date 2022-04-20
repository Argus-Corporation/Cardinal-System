package net.argus.net.socket.system;

import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.PackageType;

public class PrinterSystem extends Printer {
	
	public PrinterSystem() {
		super(PackageType.SYSTEM);
	}

	@Override
	public String[] regex() {
		List<String> regex = new ArrayList<String>();
		
		regex.add("System {");
		regex.add("  $System-Info");
		regex.add("}");
		
		return (String[]) regex.toArray(new String[regex.size()]);
	}

}
