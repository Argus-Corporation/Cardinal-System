package net.argus.gui.animation;

import java.awt.Dimension;

import net.argus.gui.Frame;

public class FrameAnimation extends Animation {
	
	public static final int CLOSE_MINIMIZE = 0;
	public static final int UNMINIIZE = 1;
	
	private Frame fen;
	
	public FrameAnimation(Frame fen) {
		this.fen = fen;
	}

	@Override
	public void play(int id) {
		switch(id) {
			case CLOSE_MINIMIZE:
				anim(-0.02f);
				break;
			case UNMINIIZE:
				anim(0.02f);
				break;
			
		}
		
	}
	
	private void anim(float add) {
		Dimension max = fen.getSize();
		Dimension old;
		
		float f;
		if(add < 0)
			f = 1f;
		else
			f = 0f;
		
		for(;valid(f, add); f = f + add) {
			fen.setOpacity(f);
			old = fen.getSize();
			
			int width = (int) (f * max.width / 1f);
			int height = (int) (f * max.height / 1f);
			
			if(width <= 0)
				width++;
			if(height <= 0)
				height++;
			
			fen.setSize(width, height);
						
			fen.setLocation(fen.getLocation().x + (old.width - fen.getSize().width) / 2, fen.getLocation().y + (old.height - fen.getSize().height) / 2);
			fen.repaint();
			
			try {Thread.sleep(1);}
			catch(InterruptedException e) {e.printStackTrace();}
		}
	}
	
	private boolean valid(float f, float add) {
		if(add < 0) return f > 0;
		else return f < 1;
	}

}
