package net.argus.util;

public enum Direction {
	
	UP, DOWN, LEFT, RIGHT;
	
	public static Direction getOppose(Direction dir) {
		switch(dir) {
			case UP: return DOWN;
			case DOWN: return UP;
			case LEFT: return RIGHT;
			case RIGHT: return LEFT;
		}
		return null;
	}

}
