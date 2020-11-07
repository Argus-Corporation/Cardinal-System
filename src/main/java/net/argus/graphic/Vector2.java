package net.argus.graphic;

public class Vector2 {
	
	private float x, y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(float[] xy) {
		this(xy[0], xy[1]);
	}
	
	public Vector2() {
		this(0, 0);
	}

	public float getX() {return x;}
	public float getY() {return y;}

	public void setX(float x) {this.x = x;}
	public void setY(float y) {this.y = y;}

}
