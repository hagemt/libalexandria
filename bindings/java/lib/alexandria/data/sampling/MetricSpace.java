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
package lib.alexandria.data.sampling;

import java.util.Comparator;

public class MetricSpace {
	public static final MetricSpace INTEGERS, RATIONALS, IRRATIONALS, REAL_NUMBERS;
	static {
		INTEGERS = new MetricSpace(Cardinal.ALEPH_NULL);
		RATIONALS = new MetricSpace(Cardinal.ALEPH_NULL);
		IRRATIONALS = new MetricSpace(Cardinal.ALEPH_ONE);
		REAL_NUMBERS = new MetricSpace(Cardinal.ALEPH_ONE);
		/* Not exactly sure what to do here. Oh well, to hell with rigor. */
		//COMPLEX_NUMBERS = new MetricSpace(Cardinal.ALEPH_TWO);
	}

	protected final Cardinal intrinsic;
	protected final Comparator<Number> metric;
	
	protected MetricSpace(Cardinal c) {
		this.intrinsic = c;
		/* Provide the discrete metric for now because I am lazy */
		this.metric = new Comparator<Number>() {
			@Override
			public int compare(Number o1, Number o2) {
				return (o1.equals(o2)) ? 0 : 1;
			}
		};
	}
}
