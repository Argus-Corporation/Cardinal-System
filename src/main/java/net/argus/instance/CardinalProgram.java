package net.argus.instance;

import net.argus.exception.InstanceException;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public abstract class CardinalProgram {
	
	private Instance instance;
		
	public CardinalProgram() {}
	
	public abstract void main(String[] args) throws InstanceException;
	
	public void launch(String[] args, boolean wait) {
		Thread inst = new Thread(() -> {
			Thread.currentThread().setName(instance.getName());
			
			try {main(args);}
			catch (InstanceException e) {	
				Debug.log(("Error while running the instance") + (e.getMessage()!=null?": " + e.getMessage():""), Info.ERROR);
			}

			notify(this);
		});
		
		Instance.startThread(inst, instance);
		
		if(wait)
			wait(this);
	}
	
	protected void setInstance(Instance instance) {
		if(instance == null) {
			Debug.log("Instace null", Info.ERROR);
			return;
		}
		this.instance = instance;
	}
	
	public Instance getInstance() {return instance;}
	
	public static void wait(Object obj) {
		try {synchronized(obj) {obj.wait();}}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public static void notify(Object obj) {
		synchronized(obj) {obj.notify();}
	}
	
}
