package com.diophantine.fractals.mandelbrot;

import java.util.ArrayList;
import java.util.HashMap;

import com.diophantine.fractals.utilities.BinaryPoint;
import com.diophantine.fractals.utilities.Complex;

public class MandelbrotSet {
	
	HashMap<String, Integer> pointMap;
	static int maxColour = 33554431;
	static double maxMod = 2;
	static int maxIter = 500;
	
	ArrayList colours;
	
	private BinaryPoint b;
	
	public MandelbrotSet() {
		pointMap = new HashMap<String, Integer>();
		b = new BinaryPoint();
		colours = new ArrayList();
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
			//System.out.println(loopPoint + " " + boundPoint + " " + loopPoint.lessThanX(boundPoint));
			loopPoint.setBase(loopPoint.baseX(), point.baseY());
			for (; loopPoint.lessThanY(boundPoint); 
					loopPoint.addY(1)) {
				// gets correct point level
				BinaryPoint p = loopPoint.copy();
				p.setLevel(p.minPointLevel());
				
				// calculates whether it's in the set
				calculatePoint(p);
			}
		}

		System.out.println("Done " + colours.size());
	}
	
	public int[][] getArrayInRect(BinaryPoint point, int width, int height) {
		int[][] array = new int[width][height];
		// the opposite corner of the rect
		BinaryPoint boundPoint = point.copy();
		boundPoint.addX(width);
		boundPoint.addY(height);
		System.out.println(point + " " + boundPoint);
		
		int x = 0;
		int y = 0;
		
		// goes through every point in rect on level
		for (BinaryPoint loopPoint = point.copy(); x < width; 
				loopPoint.addX(1)) {
			loopPoint.setBase(loopPoint.baseX(), point.baseY());
			y = 0;
			for (; y < height; 
					loopPoint.addY(1)) {
				
				BinaryPoint p = loopPoint.copy();
				p.setLevel(p.minPointLevel());
				
				if (!pointMap.containsKey(p.toString())) {
					System.out.println("Whoops");
				}
				
				// adds value to array
				array[x][y] = pointMap.get(p.toString());
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
		Complex c = new Complex(p.cartX(), p.cartY());
		Complex z = new Complex();
		
		int value = 0;
		
		// iterates through z(n+1) = zn^2 + c with z0 = z
		// stops iterating after max iterations and assumes it's converging
		for (int iter = 0; iter <= maxIter; iter++) {
			z = z.pow(2).add(c);
			// if the modulus is larger than maxMod than assume it's diverging
			if (z.mod() > maxMod) {
				value = (int) ((maxIter - iter) * (((double) maxColour)/((double) maxIter)));
				if (!colours.contains(value)) colours.add(value);
			}
		}
		
		// adds point to map
		pointMap.put(p.toString(), value);
	}
	
	public BinaryPoint clickToZoomPoint(BinaryPoint point, int width, int height, int x, int y) {
		System.out.println(x + " " + y);
		BinaryPoint b = point.copy();
		if (x - width / 4 < 0) x = width / 4;
		else if (x + width / 4 > width) x = width - width / 4;
		else x -= width / 4;
		
		if (y - height / 4 < 0) y = height / 4;
		else if (y + height / 4 > height) y = height - height / 4;
		else y -= height / 4;
		
		b.addX(x);
		b.addY(y);
		
		b.setLevel(b.maxPointLevel() + 1);
		
		return b;
	}
	
	// getters
	public BinaryPoint getCorner() {
		return b;
	}
}
