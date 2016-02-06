package edu.cmu.teampong.gameserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
			} catch (IOException e) {
				System.err.println("Failed to generate buffered reader");
			}
			String readString = null;
			try {
				readString = reader.readLine();
			} catch (IOException e) {
				System.err.println("Failed to read in bytes from source socket");
			}
			Order order = Order.parse(readString);
			
			
		}
	}
	

}
