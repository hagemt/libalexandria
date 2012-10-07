package libalexandria.functional.kernels;

/**
 * Gaussian kernel functions are expressed in one of two styles
 * The "gamma" style is default, @see #GaussianKernel
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public enum StatFlavor {
	GAMMA, SIGMA;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}