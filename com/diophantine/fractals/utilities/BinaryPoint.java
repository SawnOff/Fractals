package com.diophantine.fractals.utilities;

public class BinaryPoint {
	
	int baseX;
	int baseY;
	
	long x;
	long y;
	
	static int baseDecLevel = 2;
	
	// constructors
	public BinaryPoint() {
		x = 0;
		y = 0;
		
		baseX = 0;
		baseY = 0;
	}
	
	public BinaryPoint(int baseX, int baseY, long x, long y) {
		this.baseX = baseX;
		this.baseY = baseY;
		
		this.x = x;
		this.y = y;
		
		// if the numbers have a different number of binary digits it makes them have the same
		if (Long.toBinaryString(x).length() != Long.toBinaryString(y).length()) {
			this.setLevel(maxPointLevel());
		}
	}
	
	public BinaryPoint(double baseX, double baseY, long x, long y) {
		this.baseX = (int) Math.floor(baseX * Math.pow(10, baseDecLevel));
		this.baseY = (int) Math.floor(baseY * Math.pow(10, baseDecLevel));
		
		this.x = x;
		this.y = y;
		
		// if the numbers have a different number of binary digits it makes them have the same
		if (Long.toBinaryString(x).length() != Long.toBinaryString(y).length()) {
			this.setLevel(maxPointLevel());
		}
	}
	
	public BinaryPoint(int x, int y) {
		this.baseX = 0;
		this.baseY = 0;
		
		this.x = x;
		this.y = y;
		
		// if the numbers have a different number of binary digits it makes them have the same
		if (Integer.toBinaryString(x).length() != Integer.toBinaryString(y).length()) {
			this.setLevel(maxPointLevel());
		}
	}
	
	// returns smallest level
	public int minPointLevel() {
		// removes as many zeros from right as possible if common amongst x and y
		int xLevel = Long.toBinaryString(x).lastIndexOf('1');
		int yLevel = Long.toBinaryString(y).lastIndexOf('1');
		
		return xLevel > yLevel ? xLevel : yLevel;
	}

	// returns largest number of binary digits between x and y length
	public int maxPointLevel() {
		int xLevel = Long.toBinaryString(x).length() - 1;
		int yLevel = Long.toBinaryString(y).length() - 1;
		
		return xLevel > yLevel ? xLevel : yLevel;
	}
	
	// creates same binary point but with a certain level
	public void setLevel(int l) {
		// divides it by a power of two depending on the requested level and current level
		double xDivisor = Math.pow(2, Long.toBinaryString(x).length() - 1 - l);
		double yDivisor = Math.pow(2, Long.toBinaryString(y).length() - 1 - l);
		x = (int) Math.floor(x/xDivisor);
		y = (int) Math.floor(y/yDivisor);
	}
	
	// converts binary x to Cartesian x
	public double cartX() {
		String xS = Long.toBinaryString(x);
		double dec = 0;
		for (int i = 1; i < xS.length(); i++) {
			if (xS.charAt(i) == '1') {
				dec += Math.pow(0.5, i);
			}
		}
		
		return baseX/Math.pow(10, baseDecLevel) + dec;
	}
	
	// converts binary y to Cartesian y
	public double cartY() {
		String yS = Long.toBinaryString(y);
		double dec = 0;
		for (int i = 1; i < yS.length(); i++) {
			if (yS.charAt(i) == '1') {
				dec += Math.pow(0.5, i);
			}
		}
		
		return baseY/Math.pow(10, baseDecLevel) + dec;
	}
	
	// adds to x along same level
	public void addX(int a) {
		int level = Long.toBinaryString(x).length() - 1;
		// removes flag from x
		int noFlag = (int) (x - Math.pow(2, level));
		int max = (int) Math.pow(2, level);
		
		// increases base if exceeds level max
		if (level != 0) {
			x = (noFlag + a) % max + max;
			baseX += Math.floor((noFlag + a) / max);
		} else {
			baseX += a;
		}
	}
	
	// adds to y along same level
	public void addY(int a) {
		int level = Long.toBinaryString(y).length() - 1;
		// removes flag from y
		int noFlag = (int) (y - Math.pow(2, level));
		int max = (int) Math.pow(2, level);
		
		// increases base if exceeds level max
		if (level != 0) {
			y = (noFlag + a) % max + max;
			baseY += Math.floor((noFlag + a) / max);
		} else {
			baseY += a;
		}
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
}
