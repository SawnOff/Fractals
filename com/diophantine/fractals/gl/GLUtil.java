package gl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;

public class GLUtil {
	
    public static void glExitError(String errorMessage) {
        int errorValue = GL11.glGetError();
         
        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println("ERROR - " + errorMessage + ": " + errorString);
             
            if (Display.isCreated()) Display.destroy();
            System.exit(-1);
        }
    }
   
    public static void printLogInfo(final int obj) {
        ByteBuffer infoLog = BufferUtils.createByteBuffer(2048);
        IntBuffer lengthBuffer = BufferUtils.createIntBuffer(1);
        glGetShaderInfoLog(obj, lengthBuffer, infoLog);

        byte[] infoBytes = new byte[lengthBuffer.get()];
        infoLog.get(infoBytes);
        if (infoBytes.length == 0) {
          return;
        }
        System.err.println(new String(infoBytes, Charset.forName("ISO-8859-1")));
    }
    
    public static int glSetupShader(int program, String src, int type) {
		int id = GL20.glCreateShader(type);
		byte[] source = src.getBytes(Charset.forName("ISO-8859-1"));
		
		GL20.glShaderSource(id, (ByteBuffer) BufferUtils.createByteBuffer(source.length).put(source).flip());
        GL20.glCompileShader(id);
        if (GL20.glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
          printLogInfo(id);
          System.exit(-1);
        }
		
        glAttachShader(program, id);
        
        return id;
	}
    
    public static String getResource(String path) {
    	InputStream in = GLUtil.class.getResourceAsStream(path); 
    	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    	StringBuilder fileAsString = new StringBuilder();
    	String pointer;
    	try {
			while(true) {
				pointer = reader.readLine();
				if(pointer == null) break;
				fileAsString.append(pointer + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return fileAsString.toString();
    }
    
    public static ByteBuffer getTestBuffer() {
    	ByteBuffer buf = ByteBuffer.allocateDirect(4 * 320 * 320);
    	byte[] colors = new byte[] {0, 120, 0, 0};
    	for(int i = 0; i < 320; i++) {
    		for(int y = 0; y < 320; y++) {
    			buf.put(colors);
    		}
    	}
    	buf.flip();
    	return buf;
    }

}
