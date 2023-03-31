package net.argus.net.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;

import net.argus.crypto.CryptoRSA;
import net.argus.net.pack.Package;
import net.argus.net.pack.PackageAnalizer;
import net.argus.net.pack.key.ArrayKey;
import net.argus.net.pack.key.EndKey;
import net.argus.net.pack.key.PackageKey;
import net.argus.util.ArrayManager;
import net.argus.util.StringManager;

public class CryptoSocket extends CardinalSocket {
	
	private CryptoRSA crypto;

	public CryptoSocket(boolean example) {if(!example) crypto = new CryptoRSA();}
	
	public CryptoSocket(Socket socket) throws IOException {
		super(socket);
		crypto = new CryptoRSA();
	}

	@Override
	public void connect(String host, int port) throws UnknownHostException, IOException {
		setSocket(new Socket(host, port));
	}
	
	@Override
	public Object[] getInfos() {
		@SuppressWarnings("unused")
		String s = new String(Base64.getEncoder().encode(crypto.getPublicKey().getEncoded()));
		/*System.out.println(new String(crypto.getPublicKey().getEncoded()));
		System.out.println(new String(Base64.getDecoder().decode(s.getBytes())));*/
		//System.out.println(s);
		return new Object[] {new String(Base64.getEncoder().encode(crypto.getPublicKey().getEncoded()))};
	}
	
	@Override
	public void setInfos(Object[] infos) {
		try {
			byte[] b = Base64.getDecoder().decode(((String) infos[0]).getBytes());
			crypto.setPublicKey(b);
		}catch(NoSuchAlgorithmException | InvalidKeySpecException e) {e.printStackTrace();}
	}
	
	@Override
	public Package nextPackage() throws IOException {
		List<String> strs = stream.nextPackage();
		
		if(socketMatch) {
			String str = StringManager.convertStringToString(strs);
			str = (String) decrypt(str);
			strs = ArrayManager.toList(str.split("\n"));
		}

		List<PackageKey> keys = PackageAnalizer.convertToKey(strs);
		
		for(int i = 0; i < keys.size(); i++) {
			if(keys.get(i) instanceof ArrayKey) {
				ArrayKey k = (ArrayKey) keys.get(i);
				
				keys.set(i, new ArrayKey(k.getName(), k.getValues()));
			}else if(!(keys.get(i) instanceof EndKey)) {
				PackageKey k = keys.get(i);
				keys.set(i, new PackageKey(k.getName(), k.getValue()));
			}
		}
		return new Package(keys);
			
	}
	
	@Override
	public void send(Object p) throws IOException {
		@SuppressWarnings("unused")
		Package pack = null;
		/*try {pack = p.clone();}
		catch (CloneNotSupportedException e) {Debug.log("Error while cloning package"); return;}
		*/
		/*List<PackageKey> keys = pack.getKeys();
		for(int i = 0; i < keys.size(); i++) {
			if(keys.get(i) instanceof ArrayKey) {
				ArrayKey k = (ArrayKey) keys.get(i);
				
				keys.set(i, new ArrayKey(k.getName(), crypt(k.getValues())));
			}else if(!(keys.get(i) instanceof EndKey)) {
				PackageKey k = keys.get(i);
				keys.set(i, new PackageKey(k.getName(), crypt(k.getValue())));
			}
		}
		System.out.println(keys);
		stream.send(new Package(keys));*/
		
		stream.send(new String(crypt(p)));
	}

	@Override
	public CardinalSocket create() throws IOException {return new CryptoSocket(false);}

	@Override
	public CardinalSocket create(Socket socket) throws IOException {return new CryptoSocket(socket);}
	
	/*private Object[] decrypt(Object[] cr) {
		return cr;
		/*
		Object[] values = new Object[cr.length];
		
		for(int i = 0; i < values.length; i++)
			values[i] = decrypt(cr[i]);
		
		return values;*/
	//}
	
	private Object decrypt(Object cr) {
		if(socketMatch) {
			System.out.println(cr);
			return crypto.decrypt(cr.toString().getBytes());
		}
		
		return cr;
	}
	
	/*private Object[] crypt(Object[] cr) {
		return cr;/*
		Object[] values = new Object[cr.length];
		
		for(int i = 0; i < values.length; i++)
			values[i] = crypt(cr[i]);
		
		return values;*/
	//}
	
	private byte[] crypt(Object cr) {
		
		if(socketMatch)
			return crypto.encrypt(cr.toString());
		
		return cr.toString().getBytes();
	}
	
}
