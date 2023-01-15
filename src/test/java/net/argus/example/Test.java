package net.argus.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import net.argus.beta.com.CardinalSocketFactory;
import net.argus.crypto.CryptoRSA;
import net.argus.exception.InstanceException;
import net.argus.instance.CardinalProgram;
import net.argus.instance.Program;
import net.argus.util.Counter;

@net.argus.annotation.Test(info = "test class")
@Program(instanceName = "test")
public class Test extends CardinalProgram {

	public void main(String[] args) throws InstanceException {
		
		new Thread(() -> {
			try {
				ServerSocket socket = new ServerSocket(1575);
				CryptoRSA rsa = new CryptoRSA();
				
				CardinalSocketFactory.createServerConnection(socket.accept(), rsa);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		
		try {
			CardinalSocketFactory.createClientConnection("localhost", 1575);
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	//	System.out.println(Counter.countOccurrences("hello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis trophello World blblbl ! je suis t", 'h'));
		
		/*int[] i = new int[] {1, 2, 3, 4};
		int[] j = new int[] {5, 6, 7, 8};
		int[] k = new int[8];
		
		System.arraycopy(i, 2, k, 2, i.length - 2);
		System.arraycopy(j, 0, k, i.length, j.length);
 		for(int l : k)
 			System.out.println(l);*/
	}
		
}
