package net.argus.client.info;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Info {
	
	private static List<Info> infos = new ArrayList<Info>();
	
	public static final int  ERROR_MESSAGE = 0;
    /** Used for information messages. */
    public static final int  INFORMATION_MESSAGE = 1;
    /** Used for warning messages. */
    public static final int  WARNING_MESSAGE = 2;
    /** Used for questions. */
    public static final int  QUESTION_MESSAGE = 3;
    /** No icon is used. */
    public static final int   PLAIN_MESSAGE = -1;
	
	private String name, msg;
	private long code;
	private int type;
	
	public Info(String name, String msg, long code, int type) {
		this.name = name;
		this.msg = msg;
		this.code = code;
		this.type = type;
	}
	
	public void run() {
		JOptionPane.showMessageDialog(null, msg + ": " + code, "Alert Server", type);
	}
	
	public static Info getInfo(String name) {
		for(Info info : infos)
			if(info.name.toUpperCase().equals(name.toUpperCase()))
				return info;
		
		return null;
	}
	
	public Info registry() {
		infos.add(this);
		return this;
	}

}
