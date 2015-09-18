package com.fractals;

import gl.GLAttributes;
import gl.GLView;

public class View {

    private GLView glView;

	public View(final String title, int w, int h) {
		GLAttributes attr = new GLAttributes();
		
		attr.TITLE = title;
		
		attr.WIDTH = w;
		attr.HEIGHT = h;
		
		glView = new GLView(attr);
	}
	
	public void run(Runnable r) {
		glView.start(r);
	}
	
	public void clean() {
		glView.cleanup();
	}
	
	public void updatePixels(int[][] pixels) {
		
	}
	
}
