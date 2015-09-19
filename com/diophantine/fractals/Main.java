package com.diophantine.fractals;

import java.awt.Color;

import com.diophantine.fractals.gl.GLUtil;
import com.diophantine.fractals.mandelbrot.MandelbrotSet;
import com.diophantine.fractals.utilities.BinaryPoint;

public class Main {
	
	static MandelbrotSet m;
	static int width = 640;
	static int height = 640;
	
    public static void main(String[] args) {
    	
    	m = new MandelbrotSet();
    	
    	BinaryPoint b = new BinaryPoint(0b101, 0b100);
    	System.out.println(b + " " + b.minPointLevel());
        
        BinaryPoint p = new BinaryPoint(-2.0, -1.5, 1, 1);
        //m.loadInRect(p, width, height);
    	
        View view = new View("Fractals", width, height);
        //view.updatePixels(m.getArrayInRect(p, width, height));
        view.updatePixels(m.mandelbrotTest(p, width, height));
        
        view.run(new Runnable() {
        	public void run() {
        		if(view.hasClicked) {
        			int colour = new Color((float) Math.random(), (float) Math.random(), (float) Math.random()).getRGB();
        			System.out.println(Integer.toHexString(colour));
        			view.updatePixels(GLUtil.solidColour(width, height, colour));
        		}
        		
        		/*
        		 * Example Usage
        		 * 
        		boolean hasClicked = view.hasClicked;
        		if(hasClicked) {
        			int x = view.clickX;
        			int y = view.clickY;
        		}
        		view.updatePixels(array)
        		*/
        		
        	}
        });
        
        view.clean();
        
        System.exit(0);
    }
     

}