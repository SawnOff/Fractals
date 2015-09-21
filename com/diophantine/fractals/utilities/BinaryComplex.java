package com.diophantine.fractals.utilities;

public class BinaryComplex {
	
	public BinaryDec r;
	public BinaryDec i;
	
	// constructors
	public BinaryComplex() {
		r = new BinaryDec();
		i = new BinaryDec();
	}
	
	public BinaryComplex(BinaryDec real, BinaryDec imaginary) {
		r = real.copy();
		i = imaginary.copy();
	}
	
	// operations
	public BinaryComplex add(BinaryComplex a) {
		// adds each coefficient
		BinaryComplex z = new BinaryComplex(a.r.add(r), a.i.add(i));
		return z;
	}

	public BinaryComplex subtract(BinaryComplex a) {
		// subtracts each coefficient
		BinaryComplex z = new BinaryComplex(a.r.subtract(r), a.i.subtract(i));
		return z;
	}

	public BinaryComplex multiply(BinaryComplex a) {
		// multiplies each coefficient
		BinaryComplex z = new BinaryComplex(a.r.multiply(r).subtract(a.i.multiply(i)),
				a.r.multiply(i).add(r.multiply(a.i)));
		return z;
	}

	public BinaryComplex pow(int exp) {
		BinaryComplex z = this.copy();

		// multiplies by itself exp - 1 number of times
		for (int x = 2; x <= exp; x++) {
			z = z.multiply(this);
		}

		return z;
	}
	
	// for test purposes
	public String toString() {
		return r + " + " + i + " i";
	}
	
	public BinaryComplex copy() {
		return new BinaryComplex(r, i);
	}
	
	public BinaryDec modSqrd() {
		return r.multiply(r).add(i.multiply(i));
	}
	
	public void setLevel(int l) {
		r.setLevel(l);
		i.setLevel(l);
	}
}
