package net.argus.image.cr;

import net.argus.number.Binary;

public class Case {
	
	private CRImage cr;
	boolean bol;
	private int offX, offY;
	private int w, h;
	
	public Case(int x, int y, CRImage cr, boolean straight) {
		this.bol = straight;
		this.offX = x;
		this.offY = y;
		
		this.w = straight?2:4;
		this.h = straight?4:2;
		
		this.cr = cr;
	}
	
	public int read() {
		String i = "";
		for(int x = w -1, y = h -1; y >= 0; y = x==0?y-1:y, x = x==0?w-1:x-1) {
			//System.out.println(read(x + offX, y + offY));
			i += cr.read(x + offX, y + offY);
		}
		return new Binary(i).toInt();
	}
	
	public void drawInt(int num) {
		int[] bin = Binary.valueOf(num).getNumber();
		
		//System.out.println(Binary.valueOf(num).toInt());
		for(int i = bin.length-1, x = 0, y = 0; i >= 0; i--, y += x==w-1? 1 : 0, x = x<w-1? x + 1: 0) {
			if(bin[i] == 1) draw(x + offX, y + offY, CRImage.BLACK);
		}
	}
	
	public void draw(int offX, int offY, int color) {
		cr.draw(offX, offY, color);
		/*for(int i = 0, x = 0, y = 0; i < cr.coef * cr.coef; i++, y += x == cr.coef-1 ? 1 : 0, x = x < cr.coef - 1 ? x + 1: 0) {
			//System.out.println(((x + offX)*coef) + "  " + ((y + offY)*coef));
			//System.out.println((x + (offX * coef)) + "  " + (y + (offY * coef)));
			try {cr.img.setRGB((x + (offX * cr.coef)), (y + (offY * cr.coef)), color);}
			catch(ArrayIndexOutOfBoundsException e) {return;}
		}*/
	}
	
	public int read(int x, int y) {
		//try {
			switch(CRImage.getColor(x * cr.coef, y * cr.coef, cr.img)) {
				case CRImage.WHITE:
					return 0;
					
				case CRImage.BLACK:
					return 1;
					
				default:
					return 2;
			}
		//} catch(ArrayIndexOutOfBoundsException e) {return -1;}
	}

}
