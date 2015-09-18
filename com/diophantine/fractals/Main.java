package com.diophantine.fractals;
 
public class Main {

    public static void main(String[] args) {
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