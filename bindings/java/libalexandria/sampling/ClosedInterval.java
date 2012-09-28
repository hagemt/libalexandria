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
package libalexandria.sampling;

public abstract class ClosedInterval<E extends Comparable<E>> extends MetricSpace {
	protected E greatest_lower_bound, least_upper_bound;
	
	public ClosedInterval(E a, E b) {
		super(null);
		if (a.compareTo(b) > 0) {
			throw new IllegalArgumentException("lower bound cannot be greater than upper bound");
		}
		this.greatest_lower_bound = a;
		this.least_upper_bound = b;
	}
	
	public E getLowerBound() {
		return greatest_lower_bound;
	}
	
	public E getUpperBound() {
		return least_upper_bound;
	}
}
