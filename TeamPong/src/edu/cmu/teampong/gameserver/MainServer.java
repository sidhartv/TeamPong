package edu.cmu.teampong.gameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import edu.cmu.teampong.game.Game;

public class MainServer {
	@SuppressWarnings("resource")
	public static void main (String[] args) {
		Game game = new Game();
		Runnable gameBackground = new Runnable() {
			@Override
			public void run() {
				game.gameLoop();
			}
		};
		Thread t = new Thread(gameBackground);
		t.start();
		ServerSocket servSock = null;
		try {
			servSock = new ServerSocket(1302);

		} catch(IOException e) {
			System.err.println("Server socket binding fail: Port 1302");
			return;
		}
		while(true) {
			Socket clientSock = null;
			try {
				clientSock = servSock.accept();
				game.receiveStart();
				if (clientSock != null) {
					System.out.println("Accepted from " + clientSock.getRemoteSocketAddress());
				}
			} catch (IOException e) {
				System.err.println("Failed to accept socket connection");
				return;
			}
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
			} catch (IOException e) {
				System.err.println("Failed to generate buffered reader");
				return;
			}
			long begin = System.currentTimeMillis();
			int[] totals = {0,0};
			while(true) {
				String readString = null;
				try {
					readString = reader.readLine();
				} catch (IOException e) {
					System.err.println("Failed to read in bytes from source socket");
					return;
				}

				if (readString != null) {
					Order order = Order.parse(readString);
					if (System.currentTimeMillis() - begin <= 500) {
						if (order.getSide() == Side.LEFT) {
							totals[0]+=order.getOrderedY();
						} else {
							totals[1]+=order.getOrderedY();
						}
					} else {
						begin = System.currentTimeMillis();
						game.receiveValues(0, totals[0]);
						game.receiveValues(1, totals[1]);
						System.out.println("LEFT = " + totals[0] + " RIGHT = " + totals[1]);
						if (order.getSide() == Side.LEFT) {
							totals[0] = order.getOrderedY();
							totals[1] = 0;
						} else {
							totals[0] = 0;
							totals[1] = order.getOrderedY();
						}
					}
					System.out.println(order);
				}
			}
		}
	}
}
