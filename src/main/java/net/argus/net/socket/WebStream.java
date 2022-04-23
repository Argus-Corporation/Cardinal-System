package net.argus.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.argus.net.pack.PackageAnalizer;
import net.argus.net.pack.key.PackageKey;
import net.argus.util.debug.Debug;

public class WebStream extends Stream {
	
	private InputStream in;

	public WebStream(Socket sock) throws IOException {
		super(sock);
		in = sock.getInputStream();
	}
	
	@Override
	public List<PackageKey> nextPackage() throws IOException {
		List<String> listLines = new ArrayList<String>();
		
		while(sock.isConnected() && !sock.isClosed()) {
			String pack = decodeMessage();
			String[] lines = pack.split("\r\n");
			
			for(String line : lines) {
				listLines.add(line);
			}
			
			if(listLines.size() == 1 && listLines.get(0).toUpperCase().equals("PING")) {
				Debug.log("Ping request");
				send("PONG");
				
				listLines.clear();
			}else
				break;
		}
		return PackageAnalizer.convertToKey(listLines);
	}
	
	@Override
	public String nextLine() throws IOException {
		return decodeMessage();
	}
	
	@Override
	public void send(Object pack) {
		try {
			sock.getOutputStream().write(encode(pack.toString()));
			sock.getOutputStream().flush();
		}catch(IOException e) {e.printStackTrace();}
	}
	
	private String decodeMessage() throws IOException {
		byte[] data = new byte[1024];
		int size = in.read(data);
		return decode(data, size);
	}
	
	public static String decode(byte[] data, int size) {
	    try {
	        byte[] decoded = new byte[size-6];
	        byte[] key = new byte[] {data[2], data[3], data[4], data[5]};
	        
	        for(int i = 0; i < size - 6; i++)
	            decoded[i] = (byte) (data[i+6] ^ key[i & 0x3]);
	        
	        
	        return new String(decoded, "UTF-8");
	    }catch(IOException ex){
	        ex.printStackTrace();
	    }
	    return "Type: 0\r\n";
	}
	
	public static byte[] encode(String decoded) {
	    return encode(decoded.getBytes());
	}
	
	public static byte[] encode(byte[] decoded) {
	    byte[] data = new byte[decoded.length + 6];
		data[0] = (byte) -127;
		data[1] = (byte) (-128+decoded.length);

		byte[] key = genKey();
		
		for(int i = 0; i < key.length; i++)
			data[i+2] = key[i];
		
		for(int i = 0; i < data.length - 6; i++)
		    data[i+6] = (byte) (decoded[i] ^ key[i & 0x3]);
		
		return data;
	}
	
	private static byte[] genKey() {
		byte[] key = new byte[4];
		Random rand = new Random();
		rand.nextBytes(key);
		
		return key;
	}

}
