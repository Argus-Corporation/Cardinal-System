package net.argus.net;

import net.argus.net.server.role.Role;
import net.argus.util.debug.Debug;

public class Profile {
	
	private String name = "user";
	private UID uid;
	
	private Role role = Role.DEFAULT;
	
	private String password;
	
	public Profile() {}
	
	public Profile(String name) {
		setName(name);
		uid = new UID();
	}
	
	public void setName(String name) {
		if(name != null && !name.equals(""))
			this.name = name;
	}
	
	public void setRole(Role role) {
		if(this.role != role) {
			this.role = role;
			Debug.log("Role Changed to \"" + role.getName() + "\"");
		}
	}
	
	public void setPassword(String password) {this.password = password;}
	
	public String getName() {return name;}
	public UID getUID() {return uid;}
	public Role getRole() {return role;}
	public String getPassword() {return password;}
	
	@Override
	public String toString() {
		return "profile@" + name + ":" + uid;
	}

}
