package net.argus.beta.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Stream {
	
	public static final int DEFAULT_SIZE = 128;
	
	private InputStream in;
	private OutputStream out;
	
	private int size;
	
	public Stream(Socket socket, int size) throws IOException {
		in = socket.getInputStream();
		out = socket.getOutputStream();
		this.size = size;
	}
	
	public Stream(Socket socket) throws IOException {
		this(socket, DEFAULT_SIZE);
	}
	
	public byte[] read() throws IOException {
		int c = in.read();
		
		if(c == 1) {
			int len = 0;
			for(int i = 1; i < getHeaderSize(); i++)
				len += (in.read() - 128);
		
			byte[] b = new byte[getDataSize()];
			in.read(b, 0, b.length);
			byte[] res = new byte[len];
			System.arraycopy(b, 0, res, 0, res.length);
			
			return res;
		}
		
		byte[] b = new byte[getPackageSize()];
		b[0] = (byte) c;
		in.read(b, 1, b.length-1);
		
		byte[] nb = read();
		byte[] res = new byte[b.length + nb.length - getHeaderSize()];
		
		System.arraycopy(b, getHeaderSize(), res, 0, b.length - getHeaderSize());
		System.arraycopy(nb, 0, res, b.length - getHeaderSize(), nb.length);
		
		return res;
	}
	
	public synchronized void send(String datas) throws IOException {
		send(datas.getBytes());
	}

	public synchronized void send(byte[] datas) throws IOException {
		if(datas.length > getDataSize()) {  // 1024 - (1 bits pour l'entete)
			int c = datas.length / (getDataSize()) + 1;  // calcule le nombre de package
			
			byte[] b = new byte[getPackageSize()];
			b[0] = (byte) c;  // nb package
			System.arraycopy(datas, 0, b, getHeaderSize(), getDataSize());
			for(int i = 1; i < getHeaderSize(); i++)
				b[i] = (byte) (getDataSize() - 128);  // length
			
			out.write(b);
			out.flush();
			
			byte[] n = new byte[datas.length - getDataSize()];
			System.arraycopy(datas, getDataSize(), n, 0, datas.length - getDataSize());
			
			send(n);
			return;
		}
		
		byte[] b = new byte[getPackageSize()];
		b[0] = (byte) 1;  // nb package
		
		int lenM = datas.length;

		for(int i = 1; i < getHeaderSize(); i++) {
			if(lenM < 0)
				lenM = 0;
			
			if(lenM < 256) {
				b[i] = (byte) (lenM - 128);
				lenM -= 255;
			}else {
				b[i] = (byte) (getDataSize() - 128);
				lenM -= 255;
			}	
		}
		
		for(int j = 0; j < datas.length; j++)
			b[j+getHeaderSize()] = datas[j];
		
		out.write(b);
		out.flush();
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
	}
	
	public int getPackageSize() {return size;}
	public int getHeaderSize() {return 1 + (int) Math.ceil((getPackageSize() / 255d));}
	public int getDataSize() {return size-getHeaderSize();}

}
