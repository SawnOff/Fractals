package com.diophantine.fractals.gl;

import static org.lwjgl.opengl.GL20.glCreateProgram;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;

import com.diophantine.fractals.View;

public class GLView {
	private GLAttributes A;
	private View v;
	
	//private ByteBuffer vertexByteBuffer;
	
	private int[][] pixels;

	public GLView(GLAttributes attributes, View v) {
		this.v = v;
	    A = attributes;
		
	    //data = new int[A.WIDTH][A.HEIGHT];
	    
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
            Display.setDisplayMode(new DisplayMode(A.WIDTH, A.HEIGHT));
            Display.setTitle(A.TITLE);
            Display.create(pixelFormat, contextAtrributes);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        initGL();
	}
	
	public void start(Runnable code) {
        initLogic();
		while (!Display.isCloseRequested()) {
			v.updateClickData();
        	code.run();
            // Do a single loop (logic/render)
            cycle(); 
            // Force a maximum FPS of about 60
            Display.sync(1);
            // Let the CPU synchronize with the GPU if GPU is tagging behind
            Display.update();
        }
	}
	
	public void cleanup() {
        GL20.glDisableVertexAttribArray(0);
        
        // Delete the shaders
        GL20.glUseProgram(0);
        GL20.glDetachShader(A.PROGRAM_ID, A.VERTEX_SHADER_ID);
        GL20.glDetachShader(A.PROGRAM_ID, A.FRAGMENT_SHADER_ID);
         
        GL20.glDeleteShader(A.VERTEX_SHADER_ID);
        GL20.glDeleteShader(A.FRAGMENT_SHADER_ID);
        GL20.glDeleteProgram(A.PROGRAM_ID);
        
        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(A.VBO_ID);
        
        // Delete the color VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(A.VBOC_ID);
         
        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(A.VAO_ID);
         
        Display.destroy();
	}
	
	private void cycle() {
		
		/*
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, A.VBO_ID);
		 

		for (int i = 0; i < vertices.length; i++) {
		    Vertex vertex = vertices[i];
		     

		    float offsetX = (float) (Math.cos(Math.PI * Math.random()) * 0.1);
		    float offsetY = (float) (Math.sin(Math.PI * Math.random()) * 0.1);
		     

		    float[] xyz = vertex.getXYZW();
		    vertex.setXYZ(xyz[0] + offsetX, xyz[1] + offsetY, xyz[2]);
		     

		    FloatBuffer vertexFloatBuffer = vertexByteBuffer.asFloatBuffer();
		    vertexFloatBuffer.rewind();
		    vertexFloatBuffer.put(vertex.getXYZW());
		    vertexFloatBuffer.put(vertex.getRGBA());
		    vertexFloatBuffer.flip();
		     
		    GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, i * 32, 
		            vertexByteBuffer);

		    //vertex.setXYZ(xyz[0], xyz[1], xyz[2]);
		}
		 
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	   */
		
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL30.glBindVertexArray(A.VAO_ID);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        GL11.glDrawArrays(GL11.GL_POINTS, 0, A.VERTEX_COUNT);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
	}
	
	private void initLogic() {		
		Vertex[] vertices = new Vertex[A.WIDTH * A.HEIGHT]; 
		ArrayList<Vertex> v = new ArrayList<Vertex>();
		float hh = A.HEIGHT / 2;
        float hw = A.WIDTH / 2;
		for(float x = 0; x < pixels.length; x++) {
			for(float y = 0; y < pixels[(int) x].length; y++) {
            	float x1 = 0;
            	float y1 = 0;
            	Vertex ver = new Vertex();
            	if((x+1) == hw) {
            		if((y+1) == hh) {
            			x1 = 0f;
            			y1 = 0f;
            		} else {
            			x1 = 0f;
            			if((y+1) < hh) {
            				y1 = -(y+1)/hh;
            			} else {
            				y1 = (y+1-hh)/hh;
            			}
            		}
            	} else if((y+1) == hh) {
            		y1 = 0;
            		if((x+1) > hw) {
            			x1 = (x+1-hw)/hw;
            		} else {
            			x1 = -(x+1)/hw;
            		}
            	} else {
            		if((x+1) > hw) {
            			x1 = (x+1-hw)/hw;
            		} else {
            			x1 = -(x+1)/hw;
            		}
            		if((y+1) < hh) {
        				y1 = -(y+1)/hh;
        			} else {
        				y1 = (y+1-hh)/hh;
        			}
            	}
            	ver.setXYZ(x1, y1, 0f);
                ver.setRGB((0x123456 & pixels[(int) x][(int) y]) >> 16, (0x123456 & pixels[(int) x][(int) y]) >> 8, (0x123456 & pixels[(int) x][(int) y]));
                v.add(ver);
			}
		}
		
		for(Vertex ver5: v) {
        	vertices[v.indexOf(ver5)] = ver5;
        }

        A.VERTEX_COUNT = vertices.length;
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length *
                Vertex.elementCount);
        for (int i = 0; i < vertices.length; i++) {
            verticesBuffer.put(vertices[i].getXYZW());
            verticesBuffer.put(vertices[i].getRGBA());
            
        }
        verticesBuffer.flip();

        A.VAO_ID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(A.VAO_ID);

        A.VBO_ID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, A.VBO_ID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, Vertex.sizeInBytes, 0);

        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, Vertex.sizeInBytes, 
                Vertex.elementBytes * 4);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
         

        GL30.glBindVertexArray(0);
        
        GLUtil.glExitError("Error in setting up data");
	}
	
	private void initGL() {
        A.PROGRAM_ID = glCreateProgram();
        
        A.FRAGMENT_SHADER_ID = GLUtil.glSetupShader(A.PROGRAM_ID, GLUtil.getResource("fragment.vsh"), GL20.GL_FRAGMENT_SHADER);
        A.VERTEX_SHADER_ID = GLUtil.glSetupShader(A.PROGRAM_ID, GLUtil.getResource("vertex.vsh"), GL20.GL_VERTEX_SHADER);
        

        GL20.glBindAttribLocation(A.PROGRAM_ID, 0, "in_Position");
        GL20.glBindAttribLocation(A.PROGRAM_ID, 1, "in_Color");
        
        GL20.glLinkProgram(A.PROGRAM_ID);
        GL20.glUseProgram(A.PROGRAM_ID);
        
        GL11.glViewport(0, 0, A.WIDTH, A.HEIGHT);
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
        
        GLUtil.glExitError("Error setting up GL context and program");
	}

	public void setPixels(int[][] data) {
		this.pixels = data;
	}

}

