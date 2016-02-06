package edu.cmu.teampong.gameserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerTest {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket sock = new Socket ("localhost",1302);
		DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
		dos.writeBytes("L34\n");
		dos.writeBytes("R45\n");
		sock.close();
		
	}
}
