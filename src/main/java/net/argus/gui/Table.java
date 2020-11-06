package net.argus.gui;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Table extends JTable implements Element {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4435804817310313942L;
	
	private static final String nameType = "table";
	private static final boolean isBack = true;
	private static final boolean isFore = true;
	private static final boolean isFont = true;
	
	private static Table selected;
	private static Table triggered;
	private boolean editable;
	
	public Table() {super(); common();}
	public Table(Object[][] value, Object[] header) {super(value, header); common();}
	public Table(int numRows, int numColumns) {super(numRows, numColumns); common();}
	
	private void common() {
		nameTypes.add(nameType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
	}
	
	public void setCellEditable(boolean editable) {this.editable = editable;}
	
	public void setCenterText() {
		DefaultTableCellRenderer custom = new DefaultTableCellRenderer(); 
		custom.setHorizontalAlignment(Label.CENTER); // centre les donn�es de ton tableau
		for(int i = 0 ; i < this.getColumnCount() ; i++) // centre chaque cellule de ton tableau
			this.getColumnModel().getColumn(i).setCellRenderer(custom); 
	}
	
	public static void setSelected(Table selected) {
		if(Table.selected != null) Table.selected.setBackground(new Color(Table.selected.getBackground().getRed() - 50, Table.selected.getBackground().getGreen() - 50, Table.selected.getBackground().getBlue() - 50));
		Table.selected = selected;
		selected.setBackground(new Color(selected.getBackground().getRed() + 50, selected.getBackground().getGreen() + 50, selected.getBackground().getBlue() + 50));
		unTriggered();
	}
	
	public static void setTriggered(Table triggered) {
		if(selected != null) {
			if(!selected.equals(triggered)) {
				if(Table.triggered != null) Table.triggered.setBackground(new Color(Table.triggered.getBackground().getRed() - 25, Table.triggered.getBackground().getGreen() - 25, Table.triggered.getBackground().getBlue() - 25));
				Table.triggered = triggered;
				triggered.setBackground(new Color(triggered.getBackground().getRed() + 25, triggered.getBackground().getGreen() + 25, triggered.getBackground().getBlue() + 25));			
			}
		}else {
			if(Table.triggered != null) Table.triggered.setBackground(new Color(Table.triggered.getBackground().getRed() - 25, Table.triggered.getBackground().getGreen() - 25, Table.triggered.getBackground().getBlue() - 25));
			Table.triggered = triggered;
			triggered.setBackground(new Color(triggered.getBackground().getRed() + 25, triggered.getBackground().getGreen() + 25, triggered.getBackground().getBlue() + 25));			
		
		}
	}
	
	public static void unTriggered() {
		if(Table.triggered != null) Table.triggered.setBackground(new Color(Table.triggered.getBackground().getRed() - 25, Table.triggered.getBackground().getGreen() - 25, Table.triggered.getBackground().getBlue() - 25));
		Table.triggered = null;
	}
	
	public boolean isCellEditable(int x, int y) {return editable;}

}
