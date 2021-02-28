package net.argus.gui.animation;

import net.argus.util.ThreadManager;
import net.argus.util.notify.NotifyWindow;

public class NotifyAnimation extends Animation {
	
	public static final int OPEN = 0;
	public static final int CLOSE = 1;
	
	private NotifyWindow window;
	
	public NotifyAnimation(NotifyWindow window) {
		this.window = window;
	}

	@Override
	public void play(int id) {
		switch(id) {
			case OPEN:
				window.setOpacity(0.0f);
				while(window.getOpacity() != 1f)
					anim(0.01f);
				break;
				
			case CLOSE:
				while(window.getOpacity() != 0f)
					anim(-0.01f);
				break;
		}
	}
	
	private void anim(float add) {
		try {
			window.setOpacity(window.getOpacity() + add);
			ThreadManager.sleep(1);
		}catch(IllegalArgumentException e) {window.setOpacity(add<0?0:1);}

	}

}
