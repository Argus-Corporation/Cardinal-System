package net.argus.gui.animation;

import java.awt.Dimension;

import net.argus.gui.frame.Frame;

public class FrameAnimation extends Animation {
	
	public static final int CLOSE = 0;
	public static final int OPEN = 1;
	
	public static final float ADD = 0.23f;
	public static final int SIZE_ADD = 10;
	
	private Frame fen;
	
	public FrameAnimation(Frame fen) {
		this.fen = fen;
	}

	@Override
	public void play(int id) {
		switch(id) {
			case CLOSE:
				anim(-ADD, -SIZE_ADD);
				break;
			case OPEN:
				anim(ADD-0.05f, SIZE_ADD);
				break;
		}
		
	}
	
	private void anim(float add, int sizeAdd) {
		float f;
		if(add < 0)
			f = 1f;
		else {
			f = 0f;
		}
		
		Dimension maxDim = fen.getSize();
		
		for(int i = 0; valid(f, add); f = f + add, i++) {
			fen.setOpacity(f);				
			
			if(add < 0)
				if((i % 2) == 0) {
					int width = fen.getWidth() + sizeAdd;
					int height = fen.getHeight() + sizeAdd;
					
					if(width <= 0)
						width++;
					if(height <= 0)
						height++;
					
					fen.setSize(width, height);
					fen.setLocation(fen.getX() - sizeAdd / 2, fen.getY() - sizeAdd / 2);
				}
			
		}
		
		if(add > 0)
			fen.setOpacity(1f);
		else
			fen.setSize(maxDim);
	}
	
	private boolean valid(float f, float add) {
		if(add < 0) return f > 0;
		else return f < 1;
	}

}
