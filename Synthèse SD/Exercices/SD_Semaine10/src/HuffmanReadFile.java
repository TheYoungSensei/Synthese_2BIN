//ce code est largement inspiré par le livre Algorithms de Robert Sedgzwick

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StringWriter;
import java.util.NoSuchElementException;

public class HuffmanReadFile {

	private static ObjectInputStream in;

	private static final int EOF = -1; // end of file

	private static int buffer; // one character buffer
	private static int n; // number of bits left in buffer


	/**
	 * Reads the next bit of data from standard input and return as a boolean.
	 *
	 * @return the next bit of data from standard input as a {@code boolean}
	 * @throws NoSuchElementException
	 *             if standard input is empty
	 */
	private static boolean readBoolean() {
		if (isEmpty())
			throw new NoSuchElementException("Reading from empty input stream");
		n--;
		boolean bit = ((buffer >> n) & 1) == 1;
		if (n == 0)
			fillBuffer();
		return bit;
	}

	private static void fillBuffer() {
		try {
			buffer = in.read();
			n = 8;
		} catch (IOException e) {
			System.out.println("EOF");
			buffer = EOF;
			n = -1;
		}
	}

	/**
	 * Close this input stream and release any associated system resources.
	 */
	private static void close() {
		try {
			in.close();
		} catch (IOException ioe) {
			throw new IllegalStateException("Could not close BinaryStdIn", ioe);
		}
	}

	/**
	 * Returns true if standard input is empty.
	 * 
	 * @return true if and only if standard input is empty
	 */
	private static boolean isEmpty() {
		return buffer == EOF;
	}

	public static String read(String file) {
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			StringWriter out = new StringWriter();
			fillBuffer();
			while (!isEmpty()) {
				if (readBoolean())
					out.write('1');
				else
					out.write('0');
			}

			out.flush();
			out.close();
			close();
			return out.toString();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return null;
	}
	
	// Prend un fichier d'entrée et le retourne sous forme de String
	public static String loadFile(File f) {
	    try {
	       BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
	       StringWriter out = new StringWriter();
	       int b;
	       while ((b=in.read()) != -1)
	           out.write(b);
	       out.flush();
	       out.close();
	       in.close();
	       return out.toString();
	    }
	    catch (IOException ie)
	    {
	         ie.printStackTrace(); 
	    }
	    return null;
	}
	

}
