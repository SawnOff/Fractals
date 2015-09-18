package com.diophantine.fractals;
 
public class Main {

    public static void main(String[] args) {
        View view = new View("GL_STATIC_TEST", 320, 320);
        
        view.run(new Runnable() {
        	public void run() {
        		// Update code
        	}
        });
        
        view.clean();
        
        System.exit(0);
    }
     

}