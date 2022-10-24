package net.argus.file;

import java.io.File;
import java.io.IOException;

import net.argus.database.DataBase;
import net.argus.database.state.DataBaseState;
import net.argus.database.state.loader.DataBaseStateLoader;
import net.argus.instance.Instance;

public class DataBaseFile extends CardinalFile {

public static final String EXTENTION = "cql";
	
	public static final Filter CJSON_FILTER = new Filter(EXTENTION, "CQL File");

	public DataBaseFile(String fileName, String extention, String rep) {super(fileName, extention, rep);}
	public DataBaseFile(String fileName, String extention, String rep, Instance instance) {super(fileName, extention, rep, instance);}
	
	public DataBaseFile(String fileName, String rep) {this(fileName, EXTENTION, rep);}
	public DataBaseFile(String fileName, String rep, Instance instance) {super(fileName, EXTENTION, rep, instance);}
	
	public DataBaseFile(String path) {super(path);}
	public DataBaseFile(File path) {super(path);}
	
	public void writeData(String data) throws IOException {
		this.write(DataBaseStateLoader.load(data));
	}
	
	public void write(DataBaseState state) throws IOException {
		this.write(new DataBase(state));
	}
	
	public void write(DataBase base) throws IOException {
		super.clear();
		super.write(base.toString());
	}
	
}
