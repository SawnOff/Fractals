package com.diophantine.fractals.mandelbrot;

import com.diophantine.fractals.utilities.BinaryPoint;

public class Test {

	public static void main(String [] args) {
		/*BinaryPoint p = new BinaryPoint(-1.0f, -1.0f, 1, 1);
		//p.addX(1920);
		//System.out.println(p);
		MandelbrotSet m = new MandelbrotSet();
		m.loadInRect(p, 192, 108);
		m.loadInRect(p, 192, 108);*/
		BinaryPoint b = new BinaryPoint(100000, 100000);
		System.out.println(b.hashString());
	}
}
