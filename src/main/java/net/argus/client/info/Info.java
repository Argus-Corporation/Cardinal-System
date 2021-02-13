package net.argus.client.info;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import net.argus.util.ErrorCode;

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
	
	private ErrorCode code;
	private int type;
	
	public Info(ErrorCode code, int type) {
		this.code = code;
		this.type = type;
	}
	
	public void show(String msg) {
		JOptionPane.showMessageDialog(null, msg + ": " + code.getCode(), "Alert Server", type);
	}
	
	public static void show(ErrorCode code) {
		JOptionPane.showMessageDialog(null, code.getName() + ": " + code.getCode(), "Alert Server", code.getMessageType());
	}
	
	public static Info getInfo(ErrorCode code) {
		for(Info info : infos)
			if(info.code.equals(code))
				return info;
		
		return null;
	}
	
	public static Info getInfo(int code) {
		return getInfo(ErrorCode.valueOf(code));
	}
	
	public Info registry() {
		infos.add(this);
		return this;
	}

}
