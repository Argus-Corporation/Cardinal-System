package net.argus.net.socket.system;

import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.PackageType;

public class PrinterInfo extends Printer {

	public PrinterInfo() {
		super(PackageType.INFO);
	}

	@Override
	public String[] regex() {
		List<String> line = new ArrayList<String>();
		
		line.add("Info {");
		line.add("  $Infos");
		line.add("}");
		
		return (String[]) line.toArray(new String[line.size()]);
	}

}
