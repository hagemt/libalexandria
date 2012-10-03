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
 * A Hyperbolic kernel has the form K(A,B) = TANH(KAPPA*DOT(A,B)-C)
 * where KAPPA > 0, C < 0, which influence the rate of growth, note ^
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class HyperbolicKernel extends Kernel {
	public static enum Nature { SIN, COS, TAN; }
	
	protected HyperbolicKernel(String label, Nature n, double kappa, double constant) {
		super(label, KernelType.HYPER);
		if (kappa <= 0 || -constant <= 0) {
			throw new IllegalArgumentException("parameters must be positive");
		}
		this.addParameter("kappa", kappa);
		this.addParameter("constant", constant);
	}
}


