package net.argus.server.role;

import java.util.ArrayList;
import java.util.List;

import net.argus.server.command.Command;

public class Role {
	
	private static List<Role> roles = new ArrayList<Role>();
	
	private String name;
	private String password;
	private int rank;
	
	public Role(String name) {
		setName(name);
	}
	
	public static Role getRole(String password) {
		Role r = null;
		for(int i = 0; i < roles.size(); i++) {
			if(roles.get(i).getPassword().equals(password)) {
				r = roles.get(i);
				i = roles.size();
			}
		}
		return r;
	}
	
	public static Role getRoleByName(String name) {
		Role r = null;
		for(int i = 0; i < roles.size(); i++) {
			if(roles.get(i).getName().equals(name)) {
				r = roles.get(i);
				i = roles.size();
			}
		}
		return r;
	}
	
	public boolean isValidExecuteCommand(Command com) {return this.rank >= com.getRank();}
	public boolean isRoleNameExist(String name) {
		for(int i = 0; i < roles.size(); i++) {
			if(roles.get(i).getName().equals(name)) return true;
		}
		return false;
	}
	
	public String getName() {return name;}
	public String getPassword() {return password;}
	public int getRank() {return rank;}
	
	public Role setName(String name) {
		this.name = name;
		int count = 1;
		while(isRoleNameExist(this.name)) {
			this.name = name + "(" + count + ")";
			count++;
		}
		return this;
	}
	public Role setRank(int rank) {this.rank = rank!=0?rank:0; return this;}
	public Role setPassword(String password) {this.password = password!=null?password:""; return this;}
	
	public Role registry() {roles.add(this); return this;}

}
