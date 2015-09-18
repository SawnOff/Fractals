package com.diophantine.fractals;

import org.lwjgl.input.Mouse;

import com.diophantine.fractals.gl.GLAttributes;
import com.diophantine.fractals.gl.GLView;

public class View {

    private GLView glView;
    
    private boolean didClickLast;
    public boolean hasClicked;
    public int clickX, clickY;

	public View(final String title, int w, int h) {
		GLAttributes attr = new GLAttributes();
		
		attr.TITLE = title;
		
		attr.WIDTH = w;
		attr.HEIGHT = h;
		
		glView = new GLView(attr, this);
	}
	
	public void run(Runnable r) {
		glView.start(r);
	}
	
	public void clean() {
		glView.cleanup();
	}
	
	public void updatePixels(int[][] pixels) {
		glView.setPixels(pixels);
	}
	
	public void updateClickData() {
		hasClicked = false;
		if(Mouse.isButtonDown(0)) {
			if(!didClickLast) {
				hasClicked = true;
				didClickLast = true;
				clickX = Mouse.getX();
				clickY = Mouse.getY();
				
				System.out.println("Click at " + clickX + "," + clickY);
			}
		} else {
			didClickLast = false;
		}
	}
	
}
