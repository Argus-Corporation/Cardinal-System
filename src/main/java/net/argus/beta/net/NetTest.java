package net.argus.beta.net;

import java.io.IOException;

import net.argus.beta.net.cql.CqlClient;
import net.argus.beta.net.cql.CqlServerPlugin;
import net.argus.beta.net.ctp.CtpServer;
import net.argus.beta.net.ctp.CtpURLStreamHandler;
import net.argus.beta.net.pack.PackagePrefab;
import net.argus.beta.net.pack.cql.CqlQueryPackageDefault;
import net.argus.beta.net.pack.ctp.CtpRequestPackageDefault;
import net.argus.database.ColumnInfo;
import net.argus.database.DataBase;
import net.argus.database.TableSchema;
import net.argus.database.Type;
import net.argus.database.state.TableState;

public class NetTest {
	
	public static void main(String[] args) throws IOException {
		DataBase base = DataBase.genDataBase("Beta", TableState.getEmptyTableState("profiles", new TableSchema(new ColumnInfo("name", Type.STRING), new ColumnInfo("pasword", Type.STRING))));
		new Thread(() -> {
			try {
				CtpServer server = new CtpServer();
				server.addPlugin(new CqlServerPlugin(base));
				server.open();
			}catch(IOException e) {e.printStackTrace();}
		}).start();
		Protocol.createProtocol("ctp", new CtpURLStreamHandler());
		Protocol.register();
		
		PackagePrefab.addPackageDefaultHandler(new CtpRequestPackageDefault());
		PackagePrefab.addPackageDefaultHandler(new CqlQueryPackageDefault());
		
		CqlClient client = new CqlClient("localhost");
		
		client.connect("Django", "passwordAzerty");
		
		client.query("SELECT * 'test'");
		
	}
	
}
