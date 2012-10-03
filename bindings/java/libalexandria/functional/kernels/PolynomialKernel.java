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
 * A polynomial kernel has the form K(A,B) = (DOT(A,B)+C)^D
 * where D is a positive number TODO what type of number?
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class PolynomialKernel extends Kernel {
	public PolynomialKernel(String label, double degree, double constant) {
		super(label, KernelType.POLY);
		if (degree <= 0) {
			throw new IllegalArgumentException("degree must be positive");
		}
		this.addParameter("degree", degree);
		this.addParameter("constant", constant);
	}
	
	/* By default, make kernel homogeneous (C = 0) */
	public PolynomialKernel(String label, double degree) {
		this(label, degree, 0);
	}
}

