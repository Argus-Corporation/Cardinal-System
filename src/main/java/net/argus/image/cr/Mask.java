package net.argus.image.cr;

import java.util.ArrayList;
import java.util.List;

import net.argus.graphic.Function;
import net.argus.number.Binary;
import net.argus.util.Math;

public class Mask {
	
	private static List<Mask> masks = new ArrayList<Mask>();
	
	public static final Mask m000 = valueOf("000", (float x, float y)-> (y + x) % 2);
	public static final Mask m001 = valueOf("001", (float x, float y)-> y % 2);
	public static final Mask m010 = valueOf("010", (float x, float y)-> x % 3);
	public static final Mask m011 = valueOf("011", (float x, float y)-> (y + x) % 3);
	public static final Mask m100 = valueOf("100", (float x, float y)-> (y / 2 + x / 3) % 2);
	public static final Mask m101 = valueOf("101", (float x, float y)-> (y * x) % 2 + (y * x) % 3);
	public static final Mask m110 = valueOf("110", (float x, float y)-> ((y * x) % 3 + y * x) % 2);
	public static final Mask m111 = valueOf("111", (float x, float y)-> ((x * y) % 3 + x + y) % 2);
	
	private int[] point = new int[7*7];
	private String id;
	
	
	public Mask(String id, int[] point) {
		this.point = point;
		this.id = id;
		masks.add(this);
	}
	
	public int[] getPoint() {return point;}
	
	public static Mask valueOf(String id, Function func) {
		int[] p = new int[7*7];
		
		for(int i = 0, x = 0, y = 0; i < 7 * 7; i++, y += x == 7-1 ? 1 : 0, x = x < 7-1 ? x + 1: 0) {
			int bin = (int) func.f(x, y);
			bin = bin!=0?0:1;
			p[i] = bin;
		}
		
		return new Mask(id, p);
	}
	
	public static Mask getRandomMask() {
		int rand = Math.random(0, masks.size());
		System.out.println(rand);
		
		return masks.get(rand);
	}
	
	public static Mask getMask(String id) {
		for(Mask m : masks)
			if(m.getId().toString().equals(id))
				return m;
		
		return null;
	}
	
	public Binary getId() {return new Binary(id);}
	
}
