package net.argus.graphic;

import net.argus.util.Math;

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
	
	public Vector2(Vector2 v) {
		this(v.x, v.y);
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);	
	}
	
	public Vector2 normalize() {
		x /= length();
		y /= length();
		
		return this;
	}
	
	public Vector2 add(Vector2 v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vector2 sub(Vector2 v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public Vector2 mul(Vector2 v) {
		this.x *= v.x;
		this.y *= v.y;
		return this;
	}
	
	public Vector2 div(Vector2 v) {
		this.x /= v.x;
		this.y /= v.y;
		return this;
	}

	public float getX() {return x;}
	public float getY() {return y;}

	public void setX(float x) {this.x = x;}
	public void setY(float y) {this.y = y;}
	
	public Vector2 addX(float x) {this.x += x; return this;}
	public Vector2 addY(float y) {this.y += y; return this;}
	public Vector2 subX(float x) {this.x -= x; return this;}
	public Vector2 subY(float y) {this.y -= y; return this;}
	public Vector2 mulX(float x) {this.x *= x; return this;}
	public Vector2 mulY(float y) {this.y *= y; return this;}
	public Vector2 divX(float x) {this.x /= x; return this;}
	public Vector2 divY(float y) {this.y /= y; return this;}
	
	@Override
	public String toString() {return "[x: " + x + ", y: " + y + "]";}
	
}
