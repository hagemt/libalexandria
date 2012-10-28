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
package lib.alexandria.functional.kernels;

/**
 * Hyperbolic kernel are defined using one of six
 * trigonometric functions. They have no default flavor.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @see lib.alexandria.functional.kernels.HyperbolicKernel
 * @since libalexandria v0.1
 */
public enum TrigFlavor {
	SIN, COS, TAN, CSC, SEC, COT;
	
	public boolean isHyperbolic() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
