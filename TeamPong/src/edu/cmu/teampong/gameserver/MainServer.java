package edu.cmu.teampong.gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main (String[] args) {
		ServerSocket servSock = null;
		try {
			servSock = new ServerSocket(132);
		} catch(IOException e) {
			System.err.println("Server socket binding fail: Port 132");
		}
		while(true) {
			Socket clientSock = null;
			try {
				clientSock = servSock.accept();
			} catch (IOException e) {
				System.err.println("Failed to accept socket connection");
			}
			
			
		}
	}
	

}
