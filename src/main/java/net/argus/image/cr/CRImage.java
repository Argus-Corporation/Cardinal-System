package net.argus.image.cr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.argus.util.ASCII;
import net.argus.util.ArrayManager;

public class CRImage {
	
	protected BufferedImage img;
	protected File file;
	
	public static final int WHITE = 0xffffffff;
	public static final int BLACK = 0xff000000;
	
	protected int tolerance;
	
	protected Case[] cass;
	
	protected int coef = 1;
	
	public CRImage(int tolerance, File file) {
		this.tolerance = tolerance;
		this.file = file;
	}
	
	public void initCase() {
		List<Case> cass = new ArrayList<Case>();
		
		for(int i = 0, x = 19, y = 17; i < 18; i++, x = y<10?x-2:x, y = y<10?17:y-4)
			cass.add(new Case(x, y, this, true));
			
		cass.add(new Case(7, 9, this, true));
		
		cass.add(new Case(4, 9, this, true));
		cass.add(new Case(2, 9, this, true));
		cass.add(new Case(0, 9, this, true));
		
		cass.add(new Case(9, 0, this, true));
		cass.add(new Case(11, 0, this, true));
		
		cass.add(new Case(9, 4, this, false));
		cass.add(new Case(9, 7, this, false));
		
		this.cass = (Case[]) cass.toArray(new Case[cass.size()]);
		
	}
	
	public List<Character> toCharList(String text) {
		List<Character> chars = new ArrayList<Character>();
		
		char[] cars = text.toCharArray();
		for(int i = 0, j = 0; i < cass.length; i++) {
			if(!ArrayManager.isExist(cars, j)) j = 0;
			chars.add(cars[j++]);
		}
		
		return chars;
	}
	
	public String parser(List<String> words) {
		String mainWord = words.get(0);
		char[] mainChar = mainWord.toCharArray();
		
		String result = "";
		
		for(int i = 0; i < mainChar.length; i++) {
			char[] res = new char[words.size()];
			
			for(int j = 0; j < words.size(); j++)
				res[j] = words.get(j).toCharArray()[i];
			
			List<Character> oldTest = new ArrayList<Character>();
			int[] numChar = new int[res.length];
			
			for(int k = 0; k < res.length; k++) {
				if(!oldTest.contains(res[k])) {
					int count = 0;
					for(int l = 0; l < res.length; l++)
						if(res[k] == res[l])
							count++;
							
					oldTest.add(res[k]);
					numChar[k] = count;
				}
			}
			
			int numMax = 0;
			int posMax = 0;
			for(int m = 0 ; m < numChar.length; m++) {
				if(numChar[m] != 0) {
					if(numChar[m] > numMax) {
						numMax = numChar[m];
						posMax = m;
					}
				}
			}
			result += res[posMax];
		}
		
		return result;	
	}
	
	public int getCoef(int offX, int offY) {
		//List<Integer> pixs = new ArrayList<Integer>();
		
		int x = offX, y = offY;
		if(getColor(offX, offY) == BLACK) {
			while(true) {
				x++; y++;
				if(getColor(x, y) == WHITE) break;
			}
		}
		return x - offX;
		
	}
	
	public String read() throws IOException {
		img = ImageIO.read(file);
		
		coef = getCoef(0, 0);
		
		initCase();
		
	//	subMask(getMask());
		
		String text = "";
		List<String> word = new ArrayList<String>();
		
		int len = readInt(cass[0]);
		
		for(int i = 0, j = 0; i < cass.length - 1; i++, j++) {
			if(j == len) {
				word.add(text);
				text = "";
				j = 0;
			}
			text += readString(cass[i+1]);
		}
		
		return parser(word);
		
	}
	
	public String readString(Case cas) {
		return ASCII.valueOf(readInt(cas)).toString();
	}
	
	public int readInt(Case cas) {
		return cas.read();
	}
	
	public int read(int x, int y) {
		int color = getColor(x * coef, y * coef, img);
		
		int ret = -1;
		
		if(color > (WHITE - tolerance) && color < (WHITE + tolerance) || color == WHITE)
			ret = 1;
		else if(color > (BLACK - tolerance) && color < (BLACK + tolerance) || color == BLACK)
			ret = 0;
		
		System.out.println(ret);
		
		return ret;
	}
	
	public void write(String text) throws IOException {
		img = new BufferedImage(21, 21, BufferedImage.TYPE_INT_RGB);
		
		initCase();
		
		coef = 1;
		
		drawAll(WHITE);
		
		cass[0].drawInt(text.length());
		
		List<Character> chars = toCharList(text);
		
		for(int i = 0; i < cass.length - 1; i++) {
			drawASCII(cass[i+1], chars.get(i));
		}
		
		
	//	addMask();
		addBase();
		
		ImageIO.write(img, "png", file);
	}
	
	public void addBase() {
		drawSquare(0, 0, 7, WHITE, true);
		drawSquare(14, 0, 7, WHITE, true);
		drawSquare(0, 14, 7, WHITE, true);
		
		
		draw(6, 8, BLACK);
		draw(6, 10, BLACK);
		draw(6, 12, BLACK);
		
		draw(8, 6, BLACK);
		draw(10, 6, BLACK);
		draw(12, 6, BLACK);
		
		
		drawSquare(0, 0, 7, BLACK, false);
		drawSquare(14, 0, 7, BLACK, false);
		drawSquare(0, 14, 7, BLACK, false);
		
		drawSquare(2, 2, 3, BLACK, true);
		drawSquare(16, 2, 3, BLACK, true);
		drawSquare(2, 16, 3, BLACK, true);
		
		drawSquare(-1, -1, 9, WHITE, false);
		drawSquare(13, -1, 9, WHITE, false);
		drawSquare(-1, 13, 9, WHITE, false);
		
	}
	
	public void addMask() {
		Mask m = Mask.m011;
		
		drawMask(7, 0, m);
		
		drawMask(0, 7, m);
		drawMask(7, 7, m);
		drawMask(14, 7, m);
		
		drawMask(7, 14, m);
		drawMask(14, 14, m);
		
		draw(2, 8, m.getId().getNumber()[0]==0?WHITE:BLACK);
		draw(3, 8, m.getId().getNumber()[1]==0?WHITE:BLACK);
		draw(4, 8, m.getId().getNumber()[2]==0?WHITE:BLACK);
		
	}
	
	public void drawMask(int offX, int offY, Mask mask) {
		int[] point = mask.getPoint();
		
		for(int i = 0, x = 0, y = 0; i < 7 * 7; i++, y += x >= 7-1 ? 1 : 0, x = x < 7-1 ? x + 1: 0)
			if(point[i] == 1)
				draw(x + offX, y + offY, getColor(x + offX, y + offY)==BLACK?WHITE:BLACK);
	}
	
	public void drawASCII(Case cas, char car) {
		cas.drawInt(ASCII.valueOf(car).getCode());
	}
	
	public void draw(int offX, int offY, int color) {
		for(int i = 0, x = 0, y = 0; i < coef * coef; i++, y += x == coef-1 ? 1 : 0, x = x < coef - 1 ? x + 1: 0) {
			try {img.setRGB((x + (offX * coef)), (y + (offY * coef)), color);}
			catch(ArrayIndexOutOfBoundsException e) {return;}
		}
	}
	
	public void drawAll(int color) {
		for(int i = 0, x = 0, y = 0; i < img.getWidth() * img.getHeight(); i++, y += x == img.getWidth()-1 ? 1 : 0, x = x < img.getWidth()-1 ? x + 1: 0)
			img.setRGB(x, y, color);
	}
	
	public void drawSquare(int offX, int offY, int size, int color, boolean full) {
		for(int i = 0, x = 0, y = 0; i < size * size; i++, y += x == size-1 ? 1 : 0, x = x < size-1 ? x + 1: 0)
			if(full)
				draw(x + offX, y + offY, color);
			else
				if(x == 0 || x == size-1 || y == 0 || y == size-1)
					draw(x + offX, y + offY, color);
	}
	
	public void subMask(Mask mask) {
		drawMask(7, 0, mask);
		
		drawMask(0, 7, mask);
		drawMask(7, 7, mask);
		drawMask(14, 7, mask);
		
		drawMask(7, 14, mask);
		drawMask(14, 14, mask);
	}
	
	public Mask getMask() {
		int[] nums = new int[3];
		nums[0] = read(2, 8);
		nums[1] = read(3, 8);
		nums[2] = read(4, 8);
		
		String strNum = "";
		for(int num : nums)
			strNum += num;
		
		return Mask.getMask(strNum);
	}
	
	public int getColor(int x, int y) {
		int p = img.getRGB(x * coef, y * coef);
		int r = 0xff & (p >> 16);
		int b = 0xff & (p >> 8);
		int g = 0xff & p;
		
		return new Color(r, g, b).getRGB();
	}
	
	public static int getColor(int x, int y, BufferedImage img) {
		int p = img.getRGB(x, y);
		int r = 0xff & (p >> 16);
		int b = 0xff & (p >> 8);
		int g = 0xff & p;
		
		return new Color(r, g, b).getRGB();
	}

}
