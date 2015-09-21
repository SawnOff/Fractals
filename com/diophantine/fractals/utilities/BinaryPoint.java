package com.diophantine.fractals.utilities;

import java.math.BigDecimal;

public class BinaryPoint {
	
	public BinaryDec x;
	public BinaryDec y;
	
	static int baseDecLevel = 2;
	
	// constructors
	public BinaryPoint() {
		x = new BinaryDec();
		y = new BinaryDec();
	}
	
	public BinaryPoint(short baseX, short baseY) {
		this.x = new BinaryDec(baseX, "");
		this.y = new BinaryDec(baseY, "");
	}
	
	public BinaryPoint(short baseX, short baseY, String x, String y) {
		this.x = new BinaryDec(baseX, x);
		this.y = new BinaryDec(baseY, y);
	}
	
	public BinaryPoint(String x, String y) {
		this.x = new BinaryDec((short) 0, x);
		this.y = new BinaryDec((short) 0, y);
	}
	
	public BinaryPoint(BinaryDec x, BinaryDec y) {
		this.x = x.copy();
		this.y = y.copy();
	}
	
	// returns smallest level
	public int minLevel() {
		// removes as many zeros from right as possible if common amongst x and y
		int xLevel = x.minLevel();
		int yLevel = y.minLevel();
		
		return xLevel > yLevel ? xLevel : yLevel;
	}
	
	// creates same binary point but with a certain level
	public void setLevel(int l) {
		x.setLevel(l);
		y.setLevel(l);
	}
	
	// adds to x along same level
	public void addX(BinaryDec b) {
		x = x.add(b);
	}
	
	// adds to y along same level
	public void addY(BinaryDec b) {
		y = y.add(b);
	}
	
	// adds to x along same level
	public void addX(int i) {
		String is = Integer.toBinaryString(i);
		BinaryDec b = new BinaryDec((short) 0, "");
		if (x.s.length() > is.length() - 1) {
			b.setLevel(x.s.length() - is.length());
			b.s += is;
		} else if (x.s.length() < is.length() - 1) {
			b.s += is.substring(is.length() - y.s.length());
			b.base = Short.parseShort(is.substring(0, is.length() - x.s.length()), 2);
		} else {
			b.s += is;
		}
		x = x.add(b);
	}
	
	// adds to y along same level
	public void addY(int i) {
		String is = Integer.toBinaryString(i);
		BinaryDec b = new BinaryDec((short) 0, "");
		if (y.s.length() > is.length() - 1) {
			b.setLevel(y.s.length() - is.length());
			b.s += is;
		} else if (y.s.length() < is.length() - 1) {
			b.s += is.substring(is.length() - y.s.length());
			b.base = Short.parseShort(is.substring(0, is.length() - y.s.length()), 2);
		} else {
			b.s += is;
		}
		
		y = y.add(b);
	}
	
	// adds to x along same level
	public void add1X() {
		BinaryDec b = new BinaryDec((short) 0, "");
		b.setLevel(x.s.length() - 2);
		b.s += "1";
		x = x.add(b);
	}
	
	// adds to y along same level
	public void add1Y() {
		BinaryDec b = new BinaryDec((short) 0, "");
		b.setLevel(y.s.length() - 2);
		b.s += "1";
		y = y.add(b);
	}
	
	// for test purposes
	public String toString() {
		return "(" + x.toDec() + ", " + y.toDec() + ")";
	}
	
	// creates string that represents coordinate
	/*public String hashString() {
		String hash = String.valueOf(baseX) + ";" + String.valueOf(baseY) 
		+ ";" + String.valueOf(x) + ";" + String.valueOf(y);
		return hash;
	}*/
	
	// copier
	public BinaryPoint copy() {
		return new BinaryPoint(x, y);
	}
}
