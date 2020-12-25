package net.argus.game;

public abstract class CardiGame extends Thread {
	
	protected Graphics g;
	
	private long normalTPS = 60;
	
	public static boolean endRender;
	
	private boolean tick, render;
	private boolean running;
	
	public CardiGame() {
		this.g = new Graphics();
	}
	
	public void run() {
		running = true;
		try {
			loop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loop() throws InterruptedException {
		init();
		
		long timer = System.currentTimeMillis();
		
		long before = System.nanoTime();
		double elapsed = 0;
		double nanoSeconds = 1000000000.0 / normalTPS;
		
		//@SuppressWarnings("unused")
		int ticks = 0;
		//@SuppressWarnings("unused")
		int frames = 0;
		
		while(running) {
		/*	if(Display.isCloseRequested()) stop();
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
			
			if(tick) {
				update();
				
			}
			if(render) {
				render(g);
			}
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(frames + "  " + ticks);
				ticks = 0;
				frames = 0;
			}
		}
		
	}
	
	public abstract void init();
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public long getNormalTPS() {return normalTPS;}
	
	public void setNormalTPS(long normalTPS) {this.normalTPS = normalTPS;}

}
