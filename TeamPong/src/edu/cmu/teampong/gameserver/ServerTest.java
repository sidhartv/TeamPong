package edu.cmu.teampong.gameserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerTest {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		@SuppressWarnings("resource")
		Socket sock = new Socket ("localhost",1302);
		DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
		while(true) {
			if (Math.random() < 0.5) {
				if (Math.random() < 0.5) {
					dos.writeBytes("L-1\n");
				} else {
					dos.writeBytes("L1\n");
				}
			} else {
				if (Math.random() < 0.5) {
					dos.writeBytes("R-1\n");
				} else {
					dos.writeBytes("R1\n");
				}
			}
			
				
		}
		
		
		
		
		
	}
}
