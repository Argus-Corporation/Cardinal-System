package net.argus.beta.net.session;

import java.util.ArrayList;
import java.util.List;

public class SessionTokenAuthority {
	
	public static List<SessionTokenAuthority> authorities = new ArrayList<SessionTokenAuthority>();
	
	private List<SessionToken> tokens = new ArrayList<SessionToken>();
	
	private String name;
	
	private SessionTokenAuthority(String name) {
		this.name = name;
		authorities.add(this);
	}
	
	public void addToken(SessionToken token) {
		tokens.add(token);
	}
	
	public boolean validSessionToken(String sessionToken) {
		for(SessionToken token : tokens)
			if(token.getSessionToken().equals(sessionToken))
				return true;
		return false;
	}
	
	public static SessionTokenAuthority createSessionTokenAuthority(String name) {
		if(getTokenAuthority(name) == null)
			return new SessionTokenAuthority(name);
		return null;
	}
	
	public static SessionTokenAuthority getTokenAuthority(String name) {
		for(SessionTokenAuthority aut : authorities)
			if(aut.name.equals(name))
				return aut;
		return null;
	}
	
	public SessionToken getSessionToken(String sessionToken) {
		for(SessionToken token : tokens)
			if(token.getSessionToken().equals(sessionToken))
				return token;
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name + "@" + tokens;
	}
	
	static {
		createSessionTokenAuthority("ctp");
	}

}
