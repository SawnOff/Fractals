package com.diophantine.fractals;

import com.diophantine.fractals.mandelbrot.MandelbrotSet;
import com.diophantine.fractals.utilities.BinaryPoint;

public class Main {
	
	static MandelbrotSet m;
	static int width = 320;
	static int height = 320;
	static BinaryPoint p;
	
    public static void main(String[] args) {
    	
    	m = new MandelbrotSet();
        
        p = new BinaryPoint((-width/2)/100.0 - 1.0, (-height/3)/100, 1, 1);
        m.loadInRect(p, width, height);
    	
        View view = new View("Fractals", width, height);
        view.updatePixels(m.getArrayInRect(p, width, height));
        //view.updatePixels(m.colourTest(p, width, height));
        
        view.run(new Runnable() {
        	public void run() {
        		if(view.hasClicked) {
        			/*int colour = new Color((float) Math.random(), (float) Math.random(), (float) Math.random()).getRGB();
        			System.out.println(Integer.toHexString(colour));
        			view.updatePixels(GLUtil.solidColour(width, height, colour));*/
        			p = m.clickToZoomPoint(p, width, height, view.clickX, view.clickY, false);
        			m.loadInRect(p, width, height);
        			view.updatePixels(m.getArrayInRect(p, width, height));
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