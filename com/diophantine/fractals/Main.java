package com.diophantine.fractals;

import com.diophantine.fractals.mandelbrot.MandelbrotSet;
import com.diophantine.fractals.utilities.BinaryPoint;

public class Main {
	
	static MandelbrotSet m;
	
    public static void main(String[] args) {
    	
    	m = new MandelbrotSet();
    	
    	BinaryPoint b = new BinaryPoint(0b101, 0b100);
    	System.out.println(b + " " + b.minPointLevel());
    	
        View view = new View("Fractals", 320, 320);
        
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