package com.diophantine.fractals.mandelbrot;

import java.util.HashMap;

import com.diophantine.fractals.utilities.BinaryPoint;
import com.diophantine.fractals.utilities.Complex;

public class MandelbrotSet {
	
	HashMap<String, Integer> pointMap;
	static int maxMod = 1000;
	static int maxIter = 1000;
	
	public MandelbrotSet() {
		pointMap = new HashMap<String, Integer>();
	}
	
	public void loadInRect(BinaryPoint point, int width, int height) {
		// the opposite corner of the rect
		BinaryPoint boundPoint = point.copy();
		boundPoint.addX(width);
		boundPoint.addY(height);
		
		System.out.println("About to load " + width*height + " points.");
		// goes through every point in rect on level
		for (BinaryPoint loopPoint = point.copy(); loopPoint.lessThanX(boundPoint); 
				loopPoint.addX(1)) {
			loopPoint.setBase(loopPoint.baseX(), point.baseY());
			for (; loopPoint.lessThanY(boundPoint); 
					loopPoint.addY(1)) {
				// gets correct point level
				BinaryPoint p = loopPoint;
				p.setLevel(p.minPointLevel());
				// calculates whether it's in the set
				calculatePoint(p);
			}
		}

		System.out.println("Done");
	}
	
	public int[][] getArrayInRect(BinaryPoint point, int width, int height) {
		
		int[][] array = new int[width][height];
		// the opposite corner of the rect
		BinaryPoint boundPoint = point.copy();
		boundPoint.addX(width);
		boundPoint.addY(height);
		
		int x = 0;
		int y = 0;
		
		System.out.println("About to load " + width*height + " points.");
		// goes through every point in rect on level
		for (BinaryPoint loopPoint = point.copy(); loopPoint.lessThanX(boundPoint); 
				loopPoint.addX(1)) {
			loopPoint.setBase(loopPoint.baseX(), point.baseY());
			for (; loopPoint.lessThanY(boundPoint); 
					loopPoint.addY(1)) {
				array[x][y] = pointMap.get(loopPoint.toString());
				y++;
			}
			x++;
		}
		
		return array;
	}
	
	// calculates whether a point is in the mandelbrot set
	private void calculatePoint(BinaryPoint p) {
		// checks if already loaded this point
		if (pointMap.containsKey(p.toString())) return;

		float c = 1;
		Complex z = new Complex(p.cartX(), p.cartY());
		
		int value = 0;
		
		// iterates through z(n+1) = zn^2 + c with z0 = z
		// stops iterating after max iterations and assumes it's converging
		for (int iter = 0; iter <= maxIter; iter++) {
			z = z.pow(2).add(c);
			// if the modulus is larger than maxMod than assume it's diverging
			if (z.mod() < maxMod) {
				value = iter;
			}
		}
		
		// adds point to map
		pointMap.put(p.toString(), value);
	}
}
