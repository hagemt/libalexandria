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
package lib.alexandria.functional.sigmoid;

import lib.alexandria.Generate;

public enum SigmoidType implements Generate.A<SigmoidFunction> {
	/**
	 * Counts as a sigmoid function, with kinks?
	 * Would need argument for x-location.
	 */
	THRESHOLD,
	/**
	 * (Note: colinearity results in constant function. Catch special case.)
	 * Draw two horizontal parallel line segments. Locate the points nearest to one another.
	 * Call these A and B, and connect them with another line segment, AB.
	 * Perpendicular to A's original line segment, draw line L1. Do the same for B and L2, respectively. 
	 * Draw the perpendicular bisector of AB, which intersects that segment at its midpoint, C.
	 * Similarly draw the perpendicular bisector of AC and CB. Call these lines B1 and B2.
	 * Now, where 
	 */
	OGEE,
	/**
	 * y(t) = A * exp(b * exp(c * t))
	 * Increasing range [0, A] or decreasing range [A, 0]
	 * b (disp.) and c (rate) are both negative numbers
	 */
	GOMPERTZ,
	/**
	 * Y(t) = A + (K - A)/(1 + Q * exp(-B(t-M)))^(1/v)
	 * So, let A = 0, K = 1 (range is now [0,1])
	 * Q = 1, v = 1 so denom. is (1 + exp(-B(t-M)))
	 * Eliminate the shift and stretch with M = 0, B = 1
	 * Results in Y(t) = 1/(1 + exp(-t))
	 */
	LOGISTIC,
	/* Well-defined mathematical functions */
	/**
	 * A well-defined mathematical function, arc-tangent.
	 */
	ATAN,
	/**
	 * A well-defined mathematical function, hyperbolic tangent.
	 */
	TANH,
	/**
	 * A well-defined mathematical function, the error function.
	 */
	ERF,
	/**
	 * A monotonically-non-decreasing algebraic function.
	 * e.g. x / sqrt(c + x^2) where c \in R
	 */
	ALGEBRAIC;

	@Override
	public boolean knows(String label) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SigmoidFunction knownAs(String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SigmoidFunction getDefault() {
		// TODO Auto-generated method stub
		return null;
	}
}
