package net.argus.beta.net.session;

import java.util.ArrayList;
import java.util.List;

public class SessionTokenAuthority {
	
	private static List<SessionTokenAuthority> authorities = new ArrayList<SessionTokenAuthority>();
	
	private List<SessionToken> tokens = new ArrayList<SessionToken>();
	
	private String name;
	
	public SessionTokenAuthority(String name) {
		this.name = name;
		authorities.add(this);
	}
	
	public static SessionTokenAuthority createSessionTokenAuthority(String name) {
		return new SessionTokenAuthority(name);
	}
	
	public void addToken(SessionToken token) {
		tokens.add(token);
	}
	
	public static SessionTokenAuthority getTokenAuthority(String name) {
		for(SessionTokenAuthority aut : authorities)
			if(aut.name.equals(name))
				return aut;
		return null;
	}
	
	@Override
	public String toString() {
		return name + "@" + tokens;
	}
	
	static {
		createSessionTokenAuthority("ctp");
		createSessionTokenAuthority("cql");
	}

}
