package net.argus.database;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;

public class TableSchema {
	
	private List<ColumnInfo> infos = new ArrayList<ColumnInfo>();
	
	public TableSchema(List<ColumnInfo> infos) {
		List<String> names = new ArrayList<String>();
		for(ColumnInfo inf : infos) {
			if(names.contains(inf.getName()))
				throw new IllegalArgumentException("\"" + inf.getName() + "\" is already registered !");
			names.add(inf.getName());
		}
		
		this.infos = infos;
	}
	
	public TableSchema(ColumnInfo ... infos) {
		this(ArrayManager.toList(infos));
	}
	
	public List<ColumnInfo> getInfos() {return infos;}
	
	public int size() {return infos.size();}
	
}
