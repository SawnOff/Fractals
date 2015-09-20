package com.diophantine.fractals.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigComplex {
	
	public BigDecimal r;
	public BigDecimal i;
	
	// constructors
	public BigComplex() {
		r = BigDecimal.ZERO;
		i = BigDecimal.ZERO;
	}
	
	public BigComplex(BigDecimal real, BigDecimal imaginary) {
		r = real;
		i = imaginary;
	}
	
	// for neatness sake
	public void set(BigDecimal real, BigDecimal imaginary) {
		r = real;
		i = imaginary;
	}

	// operations
	public BigComplex add(BigComplex a) {
		// adds each coefficient
		BigComplex z = new BigComplex(a.r.add(r), a.i.add(i));
		return z;
	}
	
	public BigComplex add(BigDecimal a) {
		// adds each coefficient
		BigComplex z = new BigComplex(a.add(r), i);
		return z;
	}

	public BigComplex subtract(BigComplex a) {
		// subtracts each coefficient
		BigComplex z = new BigComplex(a.r.subtract(r), a.i.subtract(i));
		return z;
	}

	public BigComplex multiply(BigComplex a) {
		// multiplies each coefficient
		BigComplex z = new BigComplex(a.r.multiply(r).subtract(a.i.multiply(i)),
				a.r.multiply(i).add(r.multiply(a.i)));
		return z;
	}

	public BigComplex divide(BigComplex a) {
		BigComplex z;

		// makes denominator real and divides coefficients
		if (!a.i.equals(BigDecimal.ZERO)) {
			// creates conjugate to create a real denominator
			BigComplex conj = new BigComplex(a.r, a.i.negate());
			z = this.multiply(conj).divide(a.multiply(conj));

		} else {
			// divides coefficients
			z = new BigComplex(r.divide(a.r), i.divide(a.r));
		}

		return z;
	}

	public BigComplex pow(int exp) {
		BigComplex z = this;

		// multiplies by itself exp - 1 number of times
		for (int x = 2; x <= exp; x++) {
			z = z.multiply(this);
		}

		return z;
	}
	
	// returns the complex modulus
	public double modSqrd() {
		//BigDecimal mod = sqrtNewtonRaphson(r.multiply(r).add(i.multiply(i)), 5);
		double rd;
		double id;
		if (r.compareTo(BigDecimal.ZERO) < 0) rd = r.negate().doubleValue();
		else rd = r.doubleValue();
		if (i.compareTo(BigDecimal.ZERO) < 0) id = i.negate().doubleValue();
		else id = i.doubleValue();
		
		double modSqrd = rd*rd + id*id;
		
		return modSqrd;
	}
	
	// for test purposes
	public String toString() {
		return r + " + " + i + " i";
	}
	
	/*private BigDecimal sqrtNewtonRaphson(BigDecimal x, int maxIter) {
		BigDecimal n = BigDecimal.ONE;
		BigDecimal d;
		for (int iter = 0; iter < maxIter; iter++) {
			n = n.multiply(n).subtract(x);
			d = n.multiply(BigDecimal.valueOf(2));

			n = n.divide(d);
		}
		
		return n;
	}*/
	
	private double sqrtNewtonRaphson(double x, int maxIter) {
		double n = 1;
		double d;
		for (int iter = 0; iter < maxIter; iter++) {
			n = n*n - x;
			d = 2*n;

			n = n/d;
		}
		
		return n;
	}
	
	public void setPrecision(int precision) {
		r = r.setScale(precision, RoundingMode.HALF_UP);
		i = i.setScale(precision, RoundingMode.HALF_UP);
	}
	
	public int maxScale() {
		return r.scale() > i.scale() ? r.scale() : i.scale();
	}
}
