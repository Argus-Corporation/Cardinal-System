package net.argus.instance;

import net.argus.exception.InstanceException;

@Program(instanceName = "test")
public class Test extends CardinalProgram {

	@Override
	public int main(String[] args) throws InstanceException {
		throw new InstanceException("fsdiwhjgos");
		//setInstance(null);
		//new CardinalFile("test", "djj", "");
		//return 0;
	}
	
}