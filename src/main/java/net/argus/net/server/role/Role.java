package net.argus.net.server.role;

import java.util.ArrayList;
import java.util.List;

import net.argus.net.server.command.Command;

public class Role {
	
	private static List<Role> roles = new ArrayList<Role>();
	
	public static final Role DEFAULT = new Role("user", "", 0);
	
	public static final Role SYSTEM = new Role("system");
	
	private String name, password;
	private int rank;
	
	private Role(String name) {
		this.name = name;
		this.password = "";
		this.rank = Integer.MAX_VALUE;
	}
	
	public Role(String name, String password, int rank) {
		this.name = name;
		this.password = password;
		this.rank = rank;
		
		roles.add(this);
	}
	
	public static Role getRoleByPassword(String password) {
		for(Role r : roles)
			if(r.password.equals(password))
				return r;
		return DEFAULT;
	}
	
	public static Role getRole(String name, String password) {
		if(password == null)
			password = "";
		
		for(Role r : roles)
			if(r.name.equals(name) && r.password.equals(password))
				return r;
		return null;
	}
	
	public static List<String> getNames() {
		List<String> names = new ArrayList<String>();
		
		for(Role r : roles)
			names.add(r.getName());
		
		return names;
	}
	
	public String getName() {return name;}
	public int getRank() {return rank;}
	
	public boolean isAllowed(Command command) {
		return rank >= command.getRank();
	}
	
	@Override
	public String toString() {
		return "role@" + name;
	}

}
