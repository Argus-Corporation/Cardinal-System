package net.argus.net.socket;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import net.argus.crypto.Crypto;
import net.argus.crypto.Key;
import net.argus.net.pack.Package;
import net.argus.net.pack.key.ArrayKey;
import net.argus.net.pack.key.EndKey;
import net.argus.net.pack.key.PackageKey;
import net.argus.util.debug.Debug;

public class CryptoSocket extends CardinalSocket {
	
	private Crypto crypto;
	private Key cryptKey;

	public CryptoSocket(boolean example) {if(!example) crypto = Crypto.genCrypto();}
	
	public CryptoSocket(Socket socket) throws IOException {
		super(socket);
		crypto = Crypto.genCrypto();
	}

	@Override
	public void connect(String host, int port) throws UnknownHostException, IOException {
		setSocket(new Socket(host, port));
	}
	
	@Override
	public Object[] getInfos() {
		return new Object[] {
			crypto.getPublicKey().getA(),
			crypto.getPublicKey().getB()
		};
	}
	
	@Override
	public void setInfos(Object[] infos) {
		BigInteger a = new BigInteger(infos[0].toString());
		BigInteger b = new BigInteger(infos[1].toString());
		
		cryptKey = new Key(a, b);
	}
	
	@Override
	public Package nextPackage() throws IOException {
		List<PackageKey> keys = stream.nextPackage();

		for(int i = 0; i < keys.size(); i++) {
			if(keys.get(i) instanceof ArrayKey) {
				ArrayKey k = (ArrayKey) keys.get(i);
				
				keys.set(i, new ArrayKey(k.getName(), decrypt(k.getValues())));
			}else if(!(keys.get(i) instanceof EndKey)) {
				PackageKey k = keys.get(i);
				keys.set(i, new PackageKey(k.getName(), decrypt(k.getValue())));
			}
		}
		return new Package(keys);
	}
	
	@Override
	public void send(Package p) throws IOException {
		Package pack = null;
		try {pack = p.clone();}
		catch (CloneNotSupportedException e) {Debug.log("Error while cloning package"); return;}
		
		List<PackageKey> keys = pack.getKeys();
		for(int i = 0; i < keys.size(); i++) {
			if(keys.get(i) instanceof ArrayKey) {
				ArrayKey k = (ArrayKey) keys.get(i);
				
				keys.set(i, new ArrayKey(k.getName(), crypt(k.getValues())));
			}else if(!(keys.get(i) instanceof EndKey)) {
				PackageKey k = keys.get(i);
				keys.set(i, new PackageKey(k.getName(), crypt(k.getValue())));
			}
		}
		stream.send(new Package(keys));
	}

	@Override
	public CardinalSocket create() throws IOException {return new CryptoSocket(false);}

	@Override
	public CardinalSocket create(Socket socket) throws IOException {return new CryptoSocket(socket);}
	
	private Object[] decrypt(Object[] cr) {
		Object[] values = new Object[cr.length];
		
		for(int i = 0; i < values.length; i++)
			values[i] = decrypt(cr[i]);
		
		return values;
	}
	
	private Object decrypt(Object cr) {
		if(socketMatch)
			return crypto.decryptString(cr.toString());
		
		return cr;
	}
	
	private Object[] crypt(Object[] cr) {
		Object[] values = new Object[cr.length];
		
		for(int i = 0; i < values.length; i++)
			values[i] = crypt(cr[i]);
		
		return values;
	}
	
	private Object crypt(Object cr) {
		if(socketMatch)
			return cryptKey.crypt(cr.toString());
		
		return cr;
	}
	
}
