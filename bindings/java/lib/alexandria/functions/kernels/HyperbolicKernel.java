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
package lib.alexandria.functions.kernels;

import static lib.alexandria.Generate.Some;

/**
 * A Hyperbolic kernel has the form K(A,B) = TANH(KAPPA*DOT(A,B)-C)
 * where KAPPA > 0, C < 0, which influence the rate of growth, note ^
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class HyperbolicKernel extends Kernel implements Some<TrigFlavor> {
	private TrigFlavor flavor;
	
	public HyperbolicKernel(String label, TrigFlavor flavor, double kappa, double constant) {
		super(label, KernelType.HYPER);
		if (kappa <= 0 || -constant <= 0) {
			throw new IllegalArgumentException("parameters must be positive");
		}
		this.flavor = flavor;
		this.addParameter("kappa", kappa);
		this.addParameter("constant", constant);
	}
	
	public TrigFlavor getFlavor() {
		return flavor;
	}
}


