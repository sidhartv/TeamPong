package edu.cmu.teampong.gameserver;

public class Order {
	private Side side;
	private int orderedY;
	public Order (Side s, int orderedY) {
		this.side = s;
		this.orderedY = orderedY;
	}

	public Side getSide () {
		return this.side;
	}
	public int getOrderedY() {
		return this.orderedY;
	}
	public String toString() {
		switch(side) {
		case LEFT:
			return "LEFT requests " + orderedY;
		case RIGHT:
			return "RIGHT requests " + orderedY;

		default:
			return null;
		}
	}
	public static Order parse(String str) {
		if (str.charAt(0) == 'L') {
			int order = Integer.parseInt(str.substring(1));
			return new Order(Side.LEFT,order);
		}
		else if (str.charAt(0) == 'R') {
			int order = Integer.parseInt(str.substring(1));
			return new Order(Side.RIGHT,order);
		}
		else {
			throw new IllegalArgumentException("String " + str + " is not a valid order");
		}
	}
}
