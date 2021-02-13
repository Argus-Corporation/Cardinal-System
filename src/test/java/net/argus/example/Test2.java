package net.argus.example;

public class Test2 extends Test {
	
	{
		System.out.println("test2.1");
	}
	
	static {
		System.out.println("test2.2");
	}
	
	public static void main(String[] args) {
		new Test();
		System.out.println("hello");
	}
	
}
