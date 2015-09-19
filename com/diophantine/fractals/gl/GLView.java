package com.diophantine.fractals.gl;

import static org.lwjgl.opengl.GL20.glCreateProgram;

import java.nio.ByteBuffer;
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

	private boolean needsUpdate = false;
	private int[][] pixels;
	private ByteBuffer vertexByteBuffer;
	
	public static final int VERTEX_STRIDE = 32;
    public static final int ELEMENT_COUNT = 8;   
    public static final int ELEMENT_BYTES = 4;
    public static final int SIZE_IN_BYTES = ELEMENT_BYTES * ELEMENT_COUNT;

	public GLView(GLAttributes attributes, View v) {
		this.v = v;
	    A = attributes;
	    
	    pixels = new int[A.WIDTH][A.HEIGHT];
	    
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
            Display.sync(30);
            // Let the CPU synchronize with the GPU if GPU is tagging behind
            Display.update();
        }
	}
	
	public void cleanup() {
        GL20.glDisableVertexAttribArray(0);
        
        // Deleting vertex/fragment shader, VBO, color VBO and VAO
        GL20.glUseProgram(0);
        GL20.glDetachShader(A.PROGRAM_ID, A.VERTEX_SHADER_ID);
        GL20.glDetachShader(A.PROGRAM_ID, A.FRAGMENT_SHADER_ID);
        GL20.glDeleteShader(A.VERTEX_SHADER_ID);
        GL20.glDeleteShader(A.FRAGMENT_SHADER_ID);
        GL20.glDeleteProgram(A.PROGRAM_ID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(A.VBO_ID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(A.VBOC_ID);
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(A.VAO_ID);
         
        Display.destroy();
	}
	
	private ArrayList<String> check = new ArrayList<String>();
	
	private void cycle() {
		if(needsUpdate) {
			needsUpdate = false;
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, A.VBO_ID);
			
			for(float x = 0; x < A.WIDTH; x++) {
				for(float y = 0; y < A.HEIGHT; y++) {
					FloatBuffer verFloatBuffer = vertexByteBuffer.asFloatBuffer();
					verFloatBuffer.rewind();
					
					if(!check.contains(Integer.toHexString(pixels[(int) x][(int) y]))) {
						check.add(Integer.toHexString(pixels[(int) x][(int) y]));
					}

					GLUtil.glSetBuffer(x, y, (float) A.HEIGHT / 2, (float) A.WIDTH / 2, pixels[(int) x][(int) y], verFloatBuffer);
					verFloatBuffer.flip();
					
					GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, (long) ((x * A.WIDTH) + y) * VERTEX_STRIDE, vertexByteBuffer);
				}
			}
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			//System.out.println(check.size() + " many colours");
			//check.clear();
		}
       
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
		A.VERTEX_COUNT = A.WIDTH * A.HEIGHT;
		FloatBuffer verticesBuffer = GLUtil.glCartesianBuffer(GLUtil.randomColours(A.WIDTH, A.HEIGHT), ELEMENT_COUNT);
		
		vertexByteBuffer = BufferUtils.createByteBuffer(VERTEX_STRIDE);

        A.VAO_ID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(A.VAO_ID);

        A.VBO_ID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, A.VBO_ID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STREAM_DRAW);

        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, SIZE_IN_BYTES, 0);

        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, SIZE_IN_BYTES, 
                ELEMENT_BYTES * 4);
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
		this.needsUpdate = true;
	}

}

