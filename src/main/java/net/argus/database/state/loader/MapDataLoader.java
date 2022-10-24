package net.argus.database.state.loader;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.state.ColumnState;

public class MapDataLoader {
	
	public static List<ColumnState> next(List<Character> chars) {
		List<ColumnState> cols = new ArrayList<ColumnState>();
		while(chars.size() > 0) {
			chars.remove(0);
			StateKey key = StateKey.next(chars);
			if(key == null)
				return null;
			cols.add((ColumnState) key.getState(chars));

			if(chars.get(0) == ']') {
				chars.remove(0);
				break;
			}
			if(chars.get(0) == ',') {
				chars.remove(0);
			}
		}
		return cols;
	}

}
