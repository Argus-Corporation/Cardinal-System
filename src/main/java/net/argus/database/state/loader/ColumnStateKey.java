package net.argus.database.state.loader;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.Type;
import net.argus.database.state.ColumnInfoState;
import net.argus.database.state.ColumnState;

public class ColumnStateKey extends StateKey {

	public ColumnStateKey() {
		super("column");
	}

	@Override
	public ColumnState getState(List<Character> chars) {
		String name = StateKey.getNextElement(chars, '@');
		Type type = Type.valueOf(StateKey.getNextElement(chars, '}').toUpperCase());
		
		chars.remove(0); //remove '='
		chars.remove(0); //remove '{'

		/*if(chars.get(0) == '}')
			return;*/
		
		List<Object> values = new ArrayList<Object>();

		while(chars.size() > 0) {
			if(chars.get(0) == '}') {
				chars.remove(0);
				break;
			}
			if(chars.get(0) == ',')
				chars.remove(0);
			String val = StateKey.getNextElementWithLast(chars);
			values.add(convert(val));
		}
		
		return new ColumnState(new ColumnInfoState(name, type), values);
	}
	
	private Object convert(String val) {
		if(val.startsWith("'"))
			return val.substring(1, val.length() - 1);
		if(val.equals("true") || val.equals("false"))
			return Boolean.valueOf(val);
		
		return Integer.valueOf(val);
	}

}
