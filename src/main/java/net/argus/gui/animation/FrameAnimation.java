package net.argus.gui.animation;

import java.awt.Dimension;

import net.argus.gui.Frame;

public class FrameAnimation extends Animation {
	
	private Frame fen;
	
	public FrameAnimation(Frame fen) {
		this.fen = fen;
	}

	@Override
	public void play() {
		Dimension first = fen.getSize();
		
		Dimension old;
		
		for(float f = 1f;f > 0;f = f - 0.02f) {
			fen.setOpacity(f);
			old = fen.getSize();
			fen.setSize(new Dimension((int) (f * first.width / 1f), (int) (f * first.height / 1f)));
			
			fen.setLocation(fen.getLocation().x + (old.width - fen.getSize().width) / 2, fen.getLocation().y + (old.height - fen.getSize().height) / 2);
			fen.repaint();
			try {Thread.sleep(1);}
			catch(InterruptedException e) {e.printStackTrace();}
		}
		
	}

}
