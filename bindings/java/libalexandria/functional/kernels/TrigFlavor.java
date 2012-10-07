/**
 * 
 */
package libalexandria.functional.kernels;

public enum TrigFlavor {
	SIN, COS, TAN;
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}