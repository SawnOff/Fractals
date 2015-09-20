package com.diophantine.fractals.utilities;

public class BinaryDec {

	public short base = 0;
	public String s;
	
	public BinaryDec() {
		s ="0";
	}
	
	public BinaryDec(short base, String s) {
		this.base = base;
		this.s = s;
	}
	
	public BinaryDec add(BinaryDec d) {
		String b;
		String s;
		String r = "";
		
		if (length() > d.length()) {
			b = this.s;
			s = d.s;
		} else {
			b = d.s;
			s = this.s;
		}
		
		char carry = '0';
		for (int x = s.length() - 1 ; x >= 0; x--) {
			
			short n = 0;
			
			if (b.charAt(x) == '1') n++;
			if (s.charAt(x) == '1') n++;
			if (carry == '1') n++;
			
			if (n == 0) {
				r = '0' + r;
			} else if (n == 1) {
				r = '1' + r;
				carry = '0';
			} else if (n == 2) {
				r = '0' + r;
				carry = '1';
			} else {
				r = '1' + r;
				carry = '1';
			}
		}
		
		if (b.length() > s.length()) r += b.substring(s.length());
		
		BinaryDec br = new BinaryDec((short) (this.base + d.base), r);
		if (carry == '1') br.base += 1;
		
		return br;
	}
	
	public BinaryDec subtract(BinaryDec d) {
		String r = "";
		
		BinaryDec a = this.copy();
		BinaryDec b = d.copy();
		
		if (a.s.length() > b.s.length()) b.setLevel(a.s.length() - 1);
		else if (a.s.length() < b.s.length()) a.setLevel(b.s.length() - 1);
		
		char carry = '0';
		for (int x = a.length() - 1 ; x >= 0; x--) {
			String tri = "" + a.s.charAt(x) + b.s.charAt(x) + carry;
			System.out.println(tri +  " " + a + " " + b + " " + r);
			
			if (tri.equals("000")) r = '0' + r;
			else if (tri.equals("001")) r = '1' + r;
			else if (tri.equals("010")) {
				r = '1' + r;
				carry = '1';
				System.out.println("1");
			} else if (tri.equals("011")) r = '0' + r;
			else if (tri.equals("100")) r = '1' + r;
			else if (tri.equals("101")) {
				r = '0' + r;
				carry = '0';
			} else if (tri.equals("110")) r = '0' + r;
			else r = '1' + r;
		}
		
		BinaryDec br = new BinaryDec((short) (a.base - b.base), r);
		if (carry == '1') br.base -= 1;
		
		return br;
	}
	
	public BinaryDec multiply(BinaryDec d) {
		String b;
		String s;
		
		if (length() > d.length()) {
			b = this.s;
			s = d.s;
		} else {
			b = d.s;
			s = this.s;
		}
		
		String[] ra = new String[s.length()];
		
		for (int x = s.length() - 1; x >= 0; x--) {
			if (s.charAt(x) == '1') {
				ra[x] = b;
				for (int y = x; y >= 0; y--) {
					ra[x] = '0' + ra[x];
				}
			} else ra[x] = "0";
		}
		
		BinaryDec r = new BinaryDec((short) (this.base * d.base), ra[ra.length - 1]);
		r.add(this.multiply(d.base));
		r.add(d.multiply(this.base));
		System.out.println(r);
		for (int x = s.length() - 2; x >= 0; x--) {
			System.out.println(ra[x]);
			if (ra[x] != "0") r = r.add(new BinaryDec((short) 0, ra[x]));
		}
		
		return r;
	}
	
	public BinaryDec multiply(int i) {
		BinaryDec r = this.copy();
		for (int x = 2; x <= i; x++) {
			r = r.add(this);
		}
		
		return r;
	}
	
	public void setLevel(int level) {
		if (s.length() - 1 > level) s = s.substring(0, level + 1);
		else if (s.length() - 1 < level) {
			for (int x = level - (s.length() - 1); x > 0; x++) {
				s += '0';
			}
		}
	}
	
	public int minLevel() {
		return s.lastIndexOf('1');
	}
	
	public int length() {
		return s.length();
	}
	
	public String toString() {
		return s;
	}
	
	public BinaryDec copy() {
		return new BinaryDec(this.base, this.s);
	}
	
	public double toDec() {
		double dec = 0;
		for (int x = 0; x < s.length(); x++) {
			if (s.charAt(x) == '1') dec += Math.pow(0.5, x + 1);
		}
		return dec + base;
	}
}
