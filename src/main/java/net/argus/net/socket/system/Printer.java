package net.argus.net.socket.system;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.PackageType;
import net.argus.net.pack.Package;

public abstract class Printer {
	
	private static List<Printer> printers = new ArrayList<Printer>();
	
	public static final Printer INFO = new PrinterInfo();
	public static final Printer SYSTEM = new PrinterSystem();
	
	private PackageType type;
	
	public Printer(PackageType type) {
		this.type = type;
		
		printers.add(this);
	}
	
	public static Printer getPrinter(Package pack) {
		return getPrinter(pack.getType());
	}
	
	public static Printer getPrinter(PackageType type) {
		for(Printer pr : printers)
			if(pr.getType().equals(type))
				return pr;
		return null;
	}
	
	public static void printPackage(Package pack, PrintStream out) {
		Printer printer = getPrinter(pack);

		if(printer == null)
			return;
		
		printer.print(pack, out);
	}
	
	public void print(Package pack, PrintStream out) {
		String[] regex = regex();
		
		for(String line : regex)
			out.println(getLine(pack, line));
		out.flush();
	}
	
	private String getLine(Package pack, String line) {
		String ret = "";
		
		for(int i = 0; i < line.length(); line = line.substring(1)) {
			char car = line.charAt(i);
			if(car == '$') {
				String key = nextKey(line);
				
				Object[] val = new String[] {pack.getValue(key)};
				if(val[0] == null)
					val = pack.getArray(key);
				
				if(val.length > 0) {
					String befor = ret;
					
					ret += val[0] + "\n";
					for(int j = 1; j < val.length; j++)
						ret += befor + val[j] + "\n";
					
				}
				line = line.substring(key.length());
				continue;
				
			}
			
			ret += car;
		}
		
		if(ret.endsWith("\n"))
			ret = ret.substring(0, ret.length() - 1);
		
		return ret;
	}
	
	private String nextKey(String line) {
		String key = line.substring(line.indexOf('$') + 1);
		int i = key.indexOf(' ');
		
		key = key.substring(0, i!=-1?i:key.length());
		
		return key;
	}

	public abstract String[] regex();
	
	public PackageType getType() {return type;}
	
}
