package net.argus.server.command.structure;

public class Structures {
	
	public static final Structure TARGET = new Structure().add("target").add("argument", true);
	public static final Structure NEW_VALUE = new Structure().add("new value");
	public static final Structure MESSAGE = new Structure().add("target").add("message");
	public static final Structure PASSWORD = new Structure().add("password");
	public static final Structure IP = new Structure().add("ip");

}
