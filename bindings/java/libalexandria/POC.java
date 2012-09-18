package libalexandria;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class POC {
	static {
		System.loadLibrary("alexandria");
	}

	/* Symbol for FORTRAN function */
	public static native void print(String s);

	public static void main(String... args) {
		String line;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while ((line = reader.readLine()) != null) {
					POC.print(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
