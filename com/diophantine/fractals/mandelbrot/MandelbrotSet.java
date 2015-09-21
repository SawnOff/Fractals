package com.diophantine.fractals.mandelbrot;

import java.util.ArrayList;
import java.util.HashMap;

import com.diophantine.fractals.utilities.BigComplex;
import com.diophantine.fractals.utilities.BinaryComplex;
import com.diophantine.fractals.utilities.BinaryDec;
import com.diophantine.fractals.utilities.BinaryPoint;

public class MandelbrotSet {
	
	HashMap<String, Integer> pointMap;
	static double maxColour = 16777215;
	static int maxMod = 4;
	BinaryDec maxModBin;
	static int maxIter = 500;
	
	ArrayList<Integer> colours;
	
	public MandelbrotSet() {
		pointMap = new HashMap<String, Integer>();
		colours = new ArrayList<Integer>();
		maxModBin = new BinaryDec((short) maxMod, "");
	}
	
	public void loadInRect(BinaryPoint point, int width, int height) {
		// the opposite corner of the rect
		BinaryPoint boundPoint = point.copy();
		boundPoint.addX(width);
		boundPoint.addY(height);
		System.out.println(boundPoint);
		
		System.out.println("About to load " + width*height + " points.");
		// goes through every point in rect on level
		for (BinaryPoint lp = point.copy(); lp.x.lessThan(boundPoint.x); 
				lp.add1X()) {
			System.out.println("starting y loop");
			//System.out.println(loopPoint + " " + boundPoint + " " + loopPoint.lessThanX(boundPoint));
			for (lp.y = point.y.copy(); lp.y.lessThan(boundPoint.y); lp.add1Y()) {
				// gets correct point level
				BinaryPoint p = lp.copy();
				//p.setLevel(p.minLevel());
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
		
		int x = 0;
		int y = 0;
		
		// goes through every point in rect on level
		for (BinaryPoint lp = point.copy(); x < width; 
				lp.add1X()) {
			y = 0;
			for (lp.y = point.y.copy(); y < height; lp.add1Y()) {
				BinaryPoint p = lp.copy();
				p.setLevel(p.minLevel());
				
				if (!pointMap.containsKey(p.toString())) {
					System.out.println("Whoops");
				}
				
				//System.out.println(p);
				
				// adds value to array
				array[x][y] = pointMap.get(p.toString());
				y++;
			}
			x++;
		}
		
		return array;
	}
	
	// calculates whether a point is in the mandelbrot set
	public void calculatePoint(BinaryPoint p) {
		
		// checks if already loaded this point
		if (pointMap.containsKey(p.toString())) return;

		BinaryComplex c = new BinaryComplex(p.x, p.y);
		BinaryComplex z = new BinaryComplex();
		int value = 0;
		
		// iterates through z(n+1) = zn^2 + c with z0 = z
		// stops iterating after max iterations and assumes it's converging
		for (int iter = 0; iter <= maxIter; iter++) {
			
			z = z.multiply(z).add(c);
			if (z.r.s.length() > p.x.s.length()*100 || z.i.s.length() > p.x.s.length()*2) z.setLevel(p.x.s.length()*2);
			//System.out.println("z : " + z);
			// if the modulus is larger than maxMod than assume it's diverging
			//BigDecimal compare = z.mod().subtract(BigDecimal.valueOf(maxMod));
			//System.out.println(z + " " + z.modSqrd());
			if (!z.modSqrd().lessThan(maxModBin)) {
			//if (p.x.base != 0) {
				//value = colour(iter);
				value = 100000;
				//value = (int) ((maxIter - iter) * (((double) maxColour)/((double) maxIter)));
				if (!colours.contains(value)) colours.add(value);
				break;
			}
			//break;
		}
		
		// adds point to map
		pointMap.put(p.toString(), value);
	}
	
	public BinaryPoint clickToZoomPoint(BinaryPoint point, int width, int height, int x, int y, boolean bound) {
		System.out.println(x + " " + y);
		BinaryPoint b = point.copy();
		if (bound) {
			if (x - width / 4 < 0) x = width / 4;
			else if (x + width / 4 > width) x = width - width / 4;
			else x -= width / 4;
			
			if (y - height / 4 < 0) y = height / 4;
			else if (y + height / 4 > height) y = height - height / 4;
			else y -= height / 4;
		} else {
			y -= height / 4;
			x -= height / 4;
		}
		
		b.addX(x);
		b.addY(y);
		
		//b.setLevel(b.maxPointLevel() + 1);
		
		return b;
	}
	
	public int[][] colourTest(BinaryPoint point, int width, int height) {
		int[][] array = new int[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				array[x][y] = (int) ((y*maxColour)/height);
			}
		}
		
		return array;
	}
	
	public int colour(int iter) {
		int colour = 0;
		
		// calculates red
		int red = 0;
		if (iter < maxIter/3.0) {
			red = (int) ((-Math.pow(iter/(maxIter/3.0), 2) + 1) * (maxIter/3.0));
		} else if (iter > 2*maxIter/3) {
			red = (int) ((-Math.pow((iter - maxIter)/(maxIter/3.0), 2) + 1) * (maxIter/3.0));
		}
		
		// calculates blue
		int blue = 0;
		if (iter < 2*maxIter/3.0) {
			blue = (int) ((-Math.pow((iter - maxIter/3.0)/(maxIter/3.0), 2) + 1) * (maxIter/3.0));
		}
		
		// calculates blue
		int green = 0;
		if (iter > maxIter/3.0) {
			green = (int) ((-Math.pow((iter - 2*maxIter/3.0)/(maxIter/3.0), 2) + 1) * (maxIter/3.0));
		}
		
		colour += (red << 16);
		colour += (blue << 8);
		colour += green;
		
		return colour;
	}
}
