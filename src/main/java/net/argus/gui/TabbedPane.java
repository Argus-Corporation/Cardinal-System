package net.argus.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import net.argus.lang.Lang;
import net.argus.lang.LangRegister;

public class TabbedPane extends JTabbedPane implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -644580638598011050L;
	
	private List<String> names = new ArrayList<String>();
	
	public TabbedPane() {
		LangRegister.addElementLanguage(this);
		BackgoundRegister.addElement(this);
		ForegroundRegiter.addElement(this);
		FontRegister.addElement(this);
	}
	
	@Override
	public void addTab(String name, Component component) {
		names.add(name);
		super.addTab(getTitle(name), component);
	}
	
	@Override
	public void addTab(String name, Icon icon, Component component) {
		names.add(name);
		super.addTab(getTitle(name), icon, component);
	}
	
	@Override
	public void addTab(String name, Icon icon, Component component, String tip) {
		names.add(name);
		super.addTab(getTitle(name), icon, component, tip);
	}
	
	@Override
	public void removeTabAt(int index) {
		names.remove(index);
		super.removeTabAt(index);
	}
	
	@Override
	public void setTitleAt(int index, String name) {
		super.setTitleAt(index, getTitle(name));
	}

	@Override
	public void setText() {
		for(int i = 0; i < getTabCount(); i++)
			setTitleAt(i, names.get(i));
	}
	
	private String getTitle(String name) {
		return Lang.get(TAB + "." + name + ".name");
	}

	@Override
	public String getElementName() {
		return "TabbedPane";
	}

}
