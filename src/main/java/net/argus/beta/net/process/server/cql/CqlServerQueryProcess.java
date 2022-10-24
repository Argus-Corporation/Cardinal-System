package net.argus.beta.net.process.server.cql;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.PackagePrefab;
import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.ProcessReturn;
import net.argus.beta.net.process.server.ServerProcessRegister;
import net.argus.cjson.value.CJSONString;
import net.argus.database.DataBase;
import net.argus.database.cql.CQLParser;
import net.argus.database.cql.CQLRequestReturn;

public class CqlServerQueryProcess extends CqlServerProcess {
	
	public CqlServerQueryProcess(SSLSocket socket, ServerProcessRegister register) throws IOException {
		super("query", socket, register);
	}

	@Override
	protected ProcessReturn securityProcess(PackageReturn pack) throws IOException {
		DataBase base = getLinkedBase();
		if(base == null)
			return new ProcessReturn(false, "base not connected");
		
		if(pack.getValue("query") == null || !(pack.getValue("query") instanceof CJSONString))
			return new ProcessReturn(false, "query is not defined or is not a string");
		
		String query = pack.getString("query");
		CQLRequestReturn ret = CQLParser.analize(query, base);

		if(ret.isError())
			return new ProcessReturn(false, "query error");
		
		String result = "";
		if(ret.getValue() instanceof List<?>) {
			List<?> objs = (List<?>) ret.getValue();
			if(objs.size() < 1) {
				close();
				return new ProcessReturn(false, "query return empty");
			}
			
			if(objs.get(0) instanceof List<?>) {
				for(Object obj : objs) {
					List<?> list  = (List<?>) obj;
					result += pack(list) + ", ";
				}
				if(result.length() >= 2)
					result = result.substring(0, result.length() - 2);
			}else
				result = pack(objs);
			
		}
		
		send(PackagePrefab.getCqlQueryResultPackage(result));
		
		close();
		
		return new ProcessReturn(true);
	}
	
	private String pack(List<?> objs) {
		String result = "";
		for(Object obj : objs)
			result += obj.toString()  +", ";
		
		
		if(result.length() >= 2)
			result = result.substring(0, result.length() - 2);
		
		return result;
	}

	@Override
	public CqlServerQueryProcess create(SSLSocket socket) throws IOException {
		return new CqlServerQueryProcess(socket, getRegister());
	}

}
