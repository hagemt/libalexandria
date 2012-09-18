package libalexandria;

public class POC {
	/* Symbol for FORTRAN function */
	public static native void print(String s);

	public static void main(String... args) {
		for (String s : args) {
			POC.print(s);
		}
	}
}
