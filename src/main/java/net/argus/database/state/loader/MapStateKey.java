package net.argus.database.state.loader;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.state.ColumnInfoState;
import net.argus.database.state.ColumnState;
import net.argus.database.state.TableMapState;

public class MapStateKey extends StateKey {

	public MapStateKey() {
		super("map");
	}

	@Override
	public TableMapState getState(List<Character> chars) {
		chars.remove(0); // remove '['
		if(chars.get(0) == ']')
			return null;
		
		List<ColumnState> cols = MapDataLoader.next(chars);
		chars.remove(0);
		
		return extract(cols);
	}
	
	private TableMapState extract(List<ColumnState> cols) {
		List<ColumnInfoState> infos = new ArrayList<ColumnInfoState>();
		List<List<Object>> values = new ArrayList<List<Object>>();
		
		for(ColumnState stat : cols) {
			infos.add(stat.getInfo());
			values.add(stat.getValues());
		}
		
		return new TableMapState(infos, values);
	}

}
