package net.argus.database.state.loader;

import java.util.List;

import net.argus.database.state.TableMapState;
import net.argus.database.state.TableState;

public class TableStateKey extends StateKey {

	public TableStateKey() {
		super("table");
	}

	@Override
	public TableState getState(List<Character> chars) {
		String name = getNextElement(chars, '@');

		StateKey key = next(chars);
		TableMapState mapState = (TableMapState) key.getState(chars);
		
		return new TableState(name, mapState);
	}

}
