package com.diophantine.fractals;

import com.diophantine.fractals.mandelbrot.MandelbrotSet;
import com.diophantine.fractals.utilities.BinaryPoint;

public class Main {
	
	static MandelbrotSet m;
	static int width = 320;
	static int height = 320;
	
    public static void main(String[] args) {
    	
    	m = new MandelbrotSet();
    	
    	BinaryPoint b = new BinaryPoint(0b101, 0b100);
    	System.out.println(b + " " + b.minPointLevel());
        
        BinaryPoint p = new BinaryPoint();
        m.loadInRect(p, width, height);
    	
        View view = new View("Fractals", width, height);
        view.updatePixels(m.getArrayInRect(new BinaryPoint(), width, height));
        
        view.run(new Runnable() {
        	public void run() {
        		// Update code
        		
        		boolean hasClicked = view.hasClicked;
        		if(hasClicked) {
        			int x = view.clickX;
        			int y = view.clickY;
        		}
        		
        		// if needs updating
        		// view.updatePixels(int[][] array);
        		
        	}
        });
        
        view.clean();
        
        System.exit(0);
    }
     

}