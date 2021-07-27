package net.argus.util.os.linux.shortcut;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.file.CardinalFile;
import net.argus.file.Filter;
import net.argus.instance.Instance;

@Deprecated
public class ShortcutFile extends CardinalFile {
	
	public static final String EXTENTION = "desktop";
	public static final Filter FILTER = new Filter(EXTENTION, "desktop file");
	
	public static final String ENTRY = "[Desktop Entry]";

	public ShortcutFile(File file) {
		super(file);
	}
	
	public ShortcutFile(String path) {
		super(path);
	}
	
	public ShortcutFile(String fileName, String extention, String rep, Instance instance) {
		super(fileName, extention, rep, instance);
	}
	
	public ShortcutFile(String fileName, String extention, String rep) {
		super(fileName, extention, rep);
	}
	
	public ShortcutFile(String fileName, String rep, Instance instance) {
		super(fileName, EXTENTION, rep, instance);
	}
	
	public ShortcutFile(String fileName, String rep) {
		super(fileName, EXTENTION, rep);
	}
	
	public void create(Type type, String version, String name, String comment, String icon, String exec, String path, boolean terminal, String url) throws IOException {
		if(type == null)
			throw new IllegalArgumentException("type is null");
		
		if(name == null || name.equals(""))
			throw new IllegalArgumentException("name is null");
		
		if(type == Type.LINK && (url == null || url.equals("")))
			throw new IllegalArgumentException("url is null");
		
		if(!exists())
			createFile();
		
		List<String> entrys = new ArrayList<String>();
		entrys.add(ENTRY);
		
		entrys.add("Type=" + type.toString());
		if(version != null && !version.equals(""))
			entrys.add("Version=" + version);
		
		entrys.add("Name=" + name);
		
		if(comment != null && !comment.equals(""))
			entrys.add("Comment=" + comment);
		
		if(icon != null && !icon.equals(""))
			entrys.add("Icon=" + icon);
		
		if(exec != null && !exec.equals(""))
			entrys.add("Exec=" + exec);
		
		if(path != null && !path.equals(""))
			entrys.add("Path=" + path);
		
		entrys.add("Terminal=" + terminal);
		
		if(url != null && !url.equals(""))
			entrys.add("Url=" + url);
		
		write((String[]) entrys.toArray(new String[entrys.size()]));
			
	}

}
