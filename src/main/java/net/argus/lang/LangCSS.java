package net.argus.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.argus.file.css.CSSFile;

public class LangCSS implements Iterable<CSSFile> {
	
	private List<CSSFile> css = new ArrayList<CSSFile>();
	
	public void addCSS(CSSFile file) {if(file != null) css.add(file);}
	
	public List<CSSFile> getCSS() {return css;}
	public CSSFile getCSS(int index) {return css.get(index);}

	@Override
	public Iterator<CSSFile> iterator() {
		return css.iterator();
	}

}
