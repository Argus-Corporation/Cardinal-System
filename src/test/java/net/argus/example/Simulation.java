package net.argus.example;

public class Simulation {
	
	enum Type {
		CROIX, STAR, TRI, HEA, NULL
	}
	
	private Point p = new Point(0, 1);
	
	private Type[][] objs;
	
	public Simulation() {
		objs = new Type[5][5];
		objs[3][0] = Type.CROIX;
		objs[4][0] = Type.STAR;
		
		objs[1][1] = Type.CROIX;
		objs[2][1] = Type.STAR;
		objs[3][1] = Type.TRI;
		objs[4][1] = Type.STAR;
		
		objs[1][2] = Type.TRI;
		objs[2][2] = Type.HEA;
		
		objs[2][3] = Type.CROIX;
		objs[3][3] = Type.STAR;
		
		objs[2][4] = Type.HEA;
		objs[3][4] = Type.HEA;
		
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 5; y++) {
				if(objs[x][y] == null)		
					objs[x][y] = Type.NULL;
			}
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	public String start() {
		right();
		for(int i = 0; i < 246; i++) {
			switch(objs[p.getX()][p.getY()]) {
				case TRI:
					up();
					break;
				case CROIX:
					right();
					break;
				case STAR:
					down();
					break;
				case HEA:
					left();
					break;
			}
		}
		left();
		down();
		left();
		
		String pos = "";
		
		int x = p.getX();
		int y = p.getY();
		
		switch(x) {
		case 0:
			pos = "A";
			break;
		case 1:
			pos = "A";
			break;
		case 2:
			pos = "A";
			break;
		case 3:
			pos = "A";
			break;
		case 4:
			pos = "A";
			break;
		}
		
		pos += y+1;
		
		return pos;
	}
	
	private void right() {p.addX(1);}
	private void left() {p.addX(-1);}
	
	private void up() {p.addY(-1);}
	private void down() {p.addY(1);}
	
	class Point {
		
		private int x, y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void addX(int x) {this.x += x;}
		public void addY(int y) {this.y += y;}
		
		public int getX() {return x;}
		public int getY() {return y;}
		
	}
	
	public static void main(String[] args) {
		System.out.println(new Simulation().start());
	}

}
