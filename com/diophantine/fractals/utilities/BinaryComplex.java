package com.diophantine.fractals.utilities;

public class BinaryComplex {
	
	public double r;
	public double i;
	
	// constructors
	public BinaryComplex() {
		r = 0;
		i = 0;
	}
	
	public BinaryComplex(double real, double imaginary) {
		r = real;
		i = imaginary;
	}
	
	// for neatness sake
	public void set(double real, double imaginary) {
		r = real;
		i = imaginary;
	}

	// operations
	public BinaryComplex add(BinaryComplex a) {
		// adds each coefficient
		BinaryComplex z = new BinaryComplex(a.r + r, a.i + i);
		return z;
	}
	
	public BinaryComplex add(double a) {
		// adds each coefficient
		BinaryComplex z = new BinaryComplex(a + r, i);
		return z;
	}

	public BinaryComplex subtract(BinaryComplex a) {
		// subtracts each coefficient
		BinaryComplex z = new BinaryComplex(a.r - r, a.i - i);
		return z;
	}

	public BinaryComplex multiply(BinaryComplex a) {
		// multiplies each coefficient
		BinaryComplex z = new BinaryComplex(a.r * r - a.i * i,
				a.r * i + r * a.i);
		return z;
	}

	public BinaryComplex divide(BinaryComplex a) {
		BinaryComplex z;

		// makes denominator real and divides coefficients
		if (a.i != 0) {
			// creates conjugate to create a real denominator
			BinaryComplex conj = new BinaryComplex(a.r, -a.i);
			z = this.multiply(conj).divide(a.multiply(conj));

		} else {
			// divides coefficients
			z = new BinaryComplex(r / a.r, i / a.r);
		}

		return z;
	}

	public BinaryComplex pow(int exp) {
		BinaryComplex z = this;

		// multiplies by itself exp - 1 number of times
		for (int x = 2; x <= exp; x++) {
			z = z.multiply(this);
		}

		return z;
	}
	
	// returns the complex modulus
	public double mod() {
		double mod = Math.sqrt(Math.pow(r, 2) + Math.pow(i, 2));
		return mod;
	}
	
	// for test purposes
	public String toString() {
		return r + " + " + i + " i";
	}
}
