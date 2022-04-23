package net.argus.database.state.loader;

import net.argus.database.state.DataBaseState;

public class DataBaseStateLoader {
	
	private String saved;
	
	public DataBaseStateLoader(String saved) {
		this.saved = saved;
	}
	
	public DataBaseState load() {
		StateKey key = StateKey.next(saved);
		if(key == null)
			return null;
		
		key.getState(saved);
		
		return null;
	}

}
