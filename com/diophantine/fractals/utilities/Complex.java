package com.diophantine.fractals.utilities;

public class Complex {
	
	public double r;
	public double i;
	
	// constructors
	public Complex() {
		r = 0;
		i = 0;
	}
	
	public Complex(double real, double imaginary) {
		r = real;
		i = imaginary;
	}
	
	// for neatness sake
	public void set(double real, double imaginary) {
		r = real;
		i = imaginary;
	}

	// operations
	public Complex add(Complex a) {
		// adds each coefficient
		Complex z = new Complex(a.r + r, a.i + i);
		return z;
	}
	
	public Complex add(double a) {
		// adds each coefficient
		Complex z = new Complex(a + r, i);
		return z;
	}

	public Complex subtract(Complex a) {
		// subtracts each coefficient
		Complex z = new Complex(a.r - r, a.i - i);
		return z;
	}

	public Complex multiply(Complex a) {
		// multiplies each coefficient
		Complex z = new Complex(a.r * r - a.i * i,
				a.r * i + r * a.i);
		return z;
	}

	public Complex divide(Complex a) {
		Complex z;

		// makes denominator real and divides coefficients
		if (a.i != 0) {
			// creates conjugate to create a real denominator
			Complex conj = new Complex(a.r, -a.i);
			z = this.multiply(conj).divide(a.multiply(conj));

		} else {
			// divides coefficients
			z = new Complex(r / a.r, i / a.r);
		}

		return z;
	}

	public Complex pow(int exp) {
		Complex z = this;

		// multiplies by itself exp - 1 number of times
		for (int x = 2; x <= exp; x++) {
			z = z.multiply(this);
		}

		return z;
	}
	
	// returns the complex modulus
	public double mod() {
		float mod = (float) Math.sqrt(Math.pow(r, 2) + Math.pow(i, 2));
		return mod;
	}
	
	// for test purposes
	public String toString() {
		return r + " + " + i + " i";
	}
}
