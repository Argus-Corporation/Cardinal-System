package net.argus.beta.net;

import java.io.IOException;

import net.argus.beta.net.cql.CqlClient;
import net.argus.beta.net.cql.CqlServerPlugin;
import net.argus.beta.net.ctp.CtpServer;
import net.argus.database.ColumnInfo;
import net.argus.database.ColumnValue;
import net.argus.database.DataBase;
import net.argus.database.LineValue;
import net.argus.database.TableSchema;
import net.argus.database.Type;
import net.argus.database.state.TableState;
import net.argus.system.InitializationSystem;
import net.argus.util.Version;

public class NetTest {
	
	public static void main(String[] args) throws IOException {
		InitializationSystem.initSystem(args);
		DataBase base = DataBase.genDataBase("Beta", TableState.getEmptyTableState("profiles", new TableSchema(new ColumnInfo("name", Type.STRING), new ColumnInfo("password", Type.STRING))));
		base.instert("profiles", new LineValue(new ColumnValue("name", "Django"), new ColumnValue("password", "Coucou")));
		base.instert("profiles", new LineValue(new ColumnValue("name", "Louise"), new ColumnValue("password", "Hello")));
		System.out.println(base);
		new Thread(() -> {
			try {
				CtpServer server = new CtpServer(new Version("0.0.1b"));
				server.addPlugin(new CqlServerPlugin(base));
				server.open();
			}catch(IOException e) {e.printStackTrace();}
		}).start();
		
		
		//PackagePrefab.addPackageDefaultHandler(new CqlQueryPackageDefault());
		
		CqlClient client = new CqlClient("127.0.0.1", new Version("0.0.0"));
		
		client.connect("Django", "passwordAzerty");
	
		client.query("SELECT 'profiles' 'name'");
		
	}
	
}
