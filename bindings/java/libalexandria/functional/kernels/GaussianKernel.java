/*
 *    This file is part of libalexandria.
 *
 *    libalexandria is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    libalexandria is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.
 */
package libalexandria.functional.kernels;

/**
 * A Gaussian kernel has the form K(A,B) = EXP(-GAMMA*NORM(A-B)^2)
 * where GAMMA > 0 and may instead be specified GAMMA = 1/2*SIGMA^2
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class GaussianKernel extends Kernel {
	/**
	 * Gaussian kernel functions are expressed in one of two styles
	 * The "gamma" style is default, @see #GaussianKernel
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 */
	public static enum Parameterization { GAMMA, SIGMA }

	/**
	 * The type of parameterization exhibited here
	 */
	private Parameterization type;

	protected GaussianKernel(String label, Parameterization type, double value) {
		super(label, KernelType.GAUSS);
		if (value <= 0) {
			throw new IllegalArgumentException("parameter must be positive");
		}
		this.type = (type == null) ? Parameterization.GAMMA : type;
		this.addParameter(this.type.name().toLowerCase(), value);
	}
}