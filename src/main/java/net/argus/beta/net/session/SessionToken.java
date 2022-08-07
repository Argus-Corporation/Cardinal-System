package net.argus.beta.net.session;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SessionToken {
	
	public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	private String userName;
	private String sessionToken;
	
	public SessionToken(String userName, String sessionToken) {
		this.userName = userName;
		this.sessionToken = sessionToken;
	}
	
	public static SessionToken genSessionToken(String userName, String password) {
		return new SessionToken(userName, getSessionToken(userName, password));
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getSessionToken() {
		return sessionToken;
	}
	
	private static String getSessionToken(String userName, String password) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(password.getBytes(), HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			return toHexString(mac.doFinal(userName.getBytes()));
		}catch(NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String toHexString(byte[] bytes) {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		
		for(byte b : bytes)
			formatter.format("%02x", b);

		return formatter.toString();
	}

	@Override
	public String toString() {
		return userName + "@" + sessionToken;
	}

}
