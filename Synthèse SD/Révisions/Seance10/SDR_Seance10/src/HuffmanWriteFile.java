// ce code est largement inspiré du livre Algorithms de Robert Sedgewick

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class HuffmanWriteFile {
	
	    private static ObjectOutputStream output;

	    private static int buffer;     // 8-bit buffer of bits to write out
	    private static int n=0;          // number of bits remaining in buffer

	    public static void write(String file,String texte) throws IOException { 
			output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			writeString(texte);
			close();
	    }


	    
	    /**
	     * Write the specified bit to standard output.
	     */
	    private static void writeBit(boolean bit) {
	        // add bit to buffer
	        buffer <<= 1;
	        if (bit) buffer |= 1;
	        
	        // if buffer is full (8 bits), write out as a single byte
	        n++;
	        if (n == 8) {clearBuffer();}
	    } 
	    
	    // write out any remaining bits in buffer to standard output, padding with 0s
	    private static void clearBuffer() {
	        if (n == 0) return;
	        if (n > 0) buffer <<= (8 - n);
	        try {
	            output.write(buffer);
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        n = 0;
	        buffer = 0;
	    }
	    
	    private static void writeString(String s){
	        clearBuffer();
	    	char[] input = s.toCharArray();
	    	for(char c:input) writeBit(c=='1');
	    }
	    
	    /**
	     * Flush standard output, padding 0s if number of bits written so far
	     * is not a multiple of 8.
	     */
	    private static void flush() {
	        clearBuffer();
	        try {
	            output.flush();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	   /**
	     * Flush and close standard output. Once standard output is closed, you can no
	     * longer write bits to it.
	     */
	    private static void close() {
	        flush();
	        try {
	            output.close();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
}
