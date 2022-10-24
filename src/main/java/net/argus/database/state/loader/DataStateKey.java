package net.argus.database.state.loader;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.state.DataBaseState;
import net.argus.database.state.TableState;

public class DataStateKey extends StateKey {
	
	public DataStateKey() {
		super("data");
	}

	@Override
	public DataBaseState getState(List<Character> chars) {
		String name = getNextElement(chars, '@');
		
		List<TableState> tableStates = new ArrayList<TableState>();
		while(chars.size() != 0) {
			chars.remove(0); //remove '{'
			
			if(chars.get(0) == '}')
				break;

			StateKey key = next(chars);
			tableStates.add((TableState) key.getState(chars));
			if(chars.size() <= 0)
				return new DataBaseState(name, tableStates);
			if(chars.get(0) == null) {
				chars.remove(0);
				break;
			}
			if(chars.get(0) == ',') {
				chars.remove(0);
			}
		}
		
		return new DataBaseState(name, tableStates);
	}

}
