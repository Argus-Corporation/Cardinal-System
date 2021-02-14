package net.argus.file.cjson;

import java.io.IOException;
import java.util.List;

import net.argus.system.InitializationSystem;
import net.argus.system.UserSystem;

public class CJSON {
	
	private List<CJSONItem> items;
	
	public CJSON(List<CJSONItem> items) {this.items = items;}
	
	public CJSONObject getObject(String name) {return getObject(new CJSONString(name));}
	public CJSONItem getItem(String name) {return getItem(new CJSONString(name));}
	
	public CJSONObject getObject(CJSONString name) {
		for(CJSONItem item : items)
			if(item.getName().equals(name)) return item.getValue();
		return null;
	}
	
	public CJSONItem getItem(CJSONString name) {
		for(CJSONItem item : items)
			if(item.getName().equals(name)) return item;
		return null;
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for(CJSONItem o : items)
			s += o.toString();
		
		return s;
	}
	
	public static void main(String[] args) throws IOException {
		InitializationSystem.initSystem(new String[] {"-name", "Cardinal-System", "-id", "0xdev", "-log", "false"});
		
<<<<<<< HEAD
		//Key key = new Key("*ù$ef^ù*$ù$%kjnkjfezdskpvù$êfdpvù$^dfpv$ù^pfeùv^pf$^vpùfqd^v$pqfd^vperpv$qe^rpv$^qerpqv^$eqrpv^*eqorlv^$*ep*v^*$*oeqr^voeqr^voeq^vpqêpr*vqê*ov^pe*ovqerov^qr*ovqrov^qrov£%*ù^654654fsdf%¨µ£%%sff£¨µ¨%/.§/");
=======
>>>>>>> dev
		CJSONFile file = new CJSONFile("tree", "/");
		CJSON cjson = CJSONPareser.parse(file);
		System.out.println();
		System.out.println(cjson.getObject("Config").getValue("Connection").getValue("bonjour"));
		//		.getValue("manifest").getValue("type").toString();
<<<<<<< HEAD
	
		/*System.out.println(key.decrypt(password).equals("szef¨%$ùDdgdDE854ED56FDfdgfsdgfgsdGsfdgffdGDFgF855551"));
		System.out.println(key.decrypt(password));*/
		
		//System.out.println(password);
		
		//System.out.println(key.crypt("Azef¨%$ùDdgdDE854ED56FDfdgfsdgfgsdGsfdgffdGDFgF855551"));
=======
			
		//System.out.println(password);
		
>>>>>>> dev
		
		/*CJSONBuilder build = new CJSONBuilder();
		
		CJSONObject man = new CJSONObject(new CJSONString("manifest"));
		
		CJSONObject[] sar = new CJSONObject[] {new CJSONString("test"), new CJSONString("bonjour")};
		
		/*List<CJSONItem> item = new ArrayList<CJSONItem>();
		CJSONItem i = new CJSONItem();
		i.setName(new CJSONString("test"));
		i.setValue(new CJSONString("ceci est un test"));
		
		item.add(i);
		
		sar[0].setItems(item);
		sar[1].setItems(item);
		
		CJSONObject ser = new CJSONObject();
		ser.addItem(new CJSONItem(new CJSONString("version"), new CJSONString("1.2")));
		ser.addItem(new CJSONItem(new CJSONString("id"), new CJSONString("true")));
		man.addItemArray(new CJSONItemArray(new CJSONString("array"), sar));
		
		man.addItem(new CJSONItem(new CJSONString("serveur"), ser));
		
		
		build.addObject(man);
		
		
		CJSONFile file = new CJSONFile("manifest", "");
		file.write(build.build());
		*/
		
		UserSystem.exit(0);
	}


}
