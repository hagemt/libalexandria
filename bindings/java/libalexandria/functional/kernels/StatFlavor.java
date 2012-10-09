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
 * Gaussian kernel functions are expressed in one of two styles
 * The "gamma" style is default, @see GaussianKernel
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public enum StatFlavor {
	GAMMA, SIGMA;
	
	public static double convert(double d, StatFlavor f, StatFlavor g) {
		/* Ignore any pointless conversions */
		if (f.equals(g) || d < 0) {
			return d;
		}
		return (f.equals(GAMMA)) ? Math.sqrt(2 * d) : 0.5 * d * d;
	}
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}