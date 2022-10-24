package net.argus.beta.net.session;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.argus.beta.net.pack.PackageReturn;
import net.argus.cjson.value.CJSONValue;

public class SessionToken {
	
	public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	public static final String DEFUALT_KEY = "myhardworkbythesewordsguardedpleasedontsteal(c)ArgusInc";
	
	private String name;
	private String sessionToken;
	
	public SessionToken(String name, String sessionToken) {
		this.name = name;
		this.sessionToken = sessionToken;
	}
	
	public static SessionToken genSessionToken(String userName, String password) {
		return new SessionToken(userName, getSessionToken(userName, password));
	}
	
	public String getName() {
		return name;
	}
	
	public String getSessionToken() {
		return sessionToken;
	}
	
	private static String getSessionToken(String name, String password) {
		try {
			if(password == null || password.equals(""))
				password = DEFUALT_KEY;
			
			SecretKeySpec signingKey = new SecretKeySpec(password.getBytes(), HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			return toHexString(mac.doFinal(name.getBytes()));
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
		return name + "@" + sessionToken;
	}

}
