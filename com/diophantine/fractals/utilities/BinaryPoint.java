package com.diophantine.fractals.utilities;

import java.math.BigDecimal;

public class BinaryPoint {
	
	BinaryDec x;
	BinaryDec y;
	
	static int baseDecLevel = 2;
	
	// constructors
	public BinaryPoint() {
		x = new BinaryDec();
		y = new BinaryDec();
	}
	
	public BinaryPoint(short baseX, short baseY, String x, String y) {
		this.x = new BinaryDec(baseX, x);
		this.y = new BinaryDec(baseY, y);
	}
	
	public BinaryPoint(String x, String y) {
		this.x = new BinaryDec((short) 0, x);
		this.y = new BinaryDec((short) 0, y);
	}
	
	// returns smallest level
	public int minLevel() {
		// removes as many zeros from right as possible if common amongst x and y
		int xLevel = x.minLevel();
		int yLevel = y.minLevel();
		
		return xLevel > yLevel ? xLevel : yLevel;
	}

	/*// returns largest number of binary digits between x and y length
	public int maxPointLevel() {
		int xLevel = Long.toBinaryString(x).length() - 1;
		int yLevel = Long.toBinaryString(y).length() - 1;
		
		return xLevel > yLevel ? xLevel : yLevel;
	}*/
	
	// creates same binary point but with a certain level
	public void setLevel(int l) {
		// sets minlevel as level
		int level = minLevel();
		x.setLevel(level);
		y.setLevel(level);
	}
	
	/*// converts binary x to Cartesian x
	public BigDecimal cartX() {
		String xS = Long.toBinaryString(x);
		BigDecimal dec = BigDecimal.ZERO;
		for (int i = 1; i < xS.length(); i++) {
			if (xS.charAt(i) == '1') {
				dec.add(BigDecimal.valueOf(0.5).pow(i));
			}
		}
		
		return  dec.divide(BigDecimal.valueOf(Math.pow(10, baseDecLevel))).add(BigDecimal.valueOf(baseX/Math.pow(10, baseDecLevel)));
	}
	
	// converts binary y to Cartesian y
	public BigDecimal cartY() {
		String yS = Long.toBinaryString(y);
		BigDecimal dec = BigDecimal.ZERO;
		for (int i = 1; i < yS.length(); i++) {
			if (yS.charAt(i) == '1') {
				dec.add(BigDecimal.valueOf(0.5).pow(i));
			}
		}
		
		return  dec.divide(BigDecimal.valueOf(Math.pow(10, baseDecLevel))).add(BigDecimal.valueOf(baseY/Math.pow(10, baseDecLevel)));
	}*/
	
	// adds to x along same level
	public void addX(int a, int level) {
		BinaryDec b = new BinaryDec((short) 0, Integer.toBinaryString(a));
		b.setLevel(level);
		x = x.add(b);
	}
	
	// adds to y along same level
	public void addY(int a) {
		BinaryDec b = new BinaryDec((short) 0, Integer.toBinaryString(a));
		b.setLevel(level);
		y = y.add(b);
	}
	
	// compares x with x of other point
	public boolean lessThanX(BinaryPoint p) {
		if (baseX < p.baseX()) return true;
		else if (baseX > p.baseX()) return false;
		
		if (x < p.x()) return true;
		else return false;
	}
	
	// compares y with y of other point
	public boolean lessThanY(BinaryPoint p) {
		if (baseY < p.baseY()) return true;
		else if (baseY > p.baseY()) return false;
		
		if (y < p.y()) return true;
		else return false;
	}
	
	// getters
	public int baseX() {
		return baseX;
	}
	
	public int baseY() {
		return baseY;
	}
	public long x() {
		return x;
	}
	
	public long y() {
		return y;
	}
	
	// setter
	public void setBin(long x, long y) {
		this.x = x;
		this.y = y;
		
		// if the numbers have a different number of binary digits it makes them have the same
		if (Long.toBinaryString(x).length() != Long.toBinaryString(y).length()) {
			this.setLevel(maxPointLevel());
		}
	}
	
	public void setBase(int baseX, int baseY) {
		this.baseX = baseX;
		this.baseY = baseY;
	}
	
	// for test purposes
	public String toString() {
		String binary = "(" + Long.toBinaryString(x) + ", " + Long.toBinaryString(y) + ")";
		String base = "(" + baseX/Math.pow(10, baseDecLevel) + ", " + baseY/Math.pow(10, baseDecLevel) + ")";
		return binary + " at base " + base;
	}
	
	// creates string that represents coordinate
	public String hashString() {
		String hash = String.valueOf(baseX) + ";" + String.valueOf(baseY) 
		+ ";" + String.valueOf(x) + ";" + String.valueOf(y);
		return hash;
	}
	
	public static BinaryPoint hashToBinaryPoint(String hash) {
		String[] p = hash.split(";");
		BinaryPoint b = new BinaryPoint(Integer.parseInt(p[0]), Integer.parseInt(p[1]),
				Long.parseLong(p[2]), Long.parseLong(p[3]));
		return b;
	}
	
	//private static String longToString(long l) {
		//String s;
		//String lString = Integer.parseInt(Long.toBinaryString(l), 2);
		
		//s += lString.subSequence(0, 7);
		//return null;
	//}
	
	// copier
	public BinaryPoint copy() {
		return new BinaryPoint(this.baseX, this.baseY, this.x, this.y);
	}
	
	public BinaryDec yToBinDec() {
		return new BinaryDec(baseY/Math.pow(10, baseDecLevel), Long.toBinaryString(y));
	}
}
