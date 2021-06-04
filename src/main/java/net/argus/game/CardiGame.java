package net.argus.game;

import net.argus.exception.GameException;

public abstract class CardiGame extends Thread {
	
	protected Graphics g;
	
	private long normalTPS = 60;
		
	private boolean tick, render;
	private boolean running;
	
	public CardiGame() {
		this.g = new Graphics();
	}
	
	@Override
	public void run() {
		running = true;
		try {loop();}
		catch(GameException e) {e.printStackTrace();}
	}
	
	public void loop() throws GameException {
		init();
		
		long timer = System.currentTimeMillis();
		
		long before = System.nanoTime();
		double elapsed = 0;
		double nanoSeconds = 1000000000.0 / normalTPS;
		
		@SuppressWarnings("unused")
		int ticks = 0;
		@SuppressWarnings("unused")
		int frames = 0;
		
		while(running) {
			/*if(Display.isCloseRequested()) stop();
			DisplayManager.update();*/
			
			tick = false;
			render = false;
			
			long now = System.nanoTime();
			elapsed = now - before;
			
			if(elapsed > nanoSeconds) {
				before += nanoSeconds;
				tick = true;
				ticks++;
			}else {
				render = true;
				frames++;
			}
			
			if(tick)
				update();
				
			if(render)
				render(g);
				
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(frames + "  " + ticks);
				ticks = 0;
				frames = 0;
			}
		}
		
	}
	
	public abstract void init() throws GameException;
	
	public abstract void update() throws GameException;
	
	public abstract void render(Graphics g) throws GameException;
	
	
	public long getNormalTPS() {return normalTPS;}
	
	public void setNormalTPS(long normalTPS) {this.normalTPS = normalTPS;}

}
