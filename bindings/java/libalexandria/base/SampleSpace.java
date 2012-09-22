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
package libalexandria.base;

public abstract class SampleSpace {
	protected final Ordinal<Integer> cardinality;
	protected final Number dimension;
	protected String label;

	protected SampleSpace(Ordinal<Integer> cardinality, Number dimension) {
		this.cardinality = cardinality;
		this.dimension = dimension;
	}
	
	protected SampleSpace(String label) {
		this(Aleph.ONE, 1);
		this.label = label;
	}
	
	protected SampleSpace() {
		this("");
	}

	public boolean isFinite() {
		return cardinality.isFinite();
	}

	public boolean isEnumerable() {
		return cardinality.isCountable();
	}
	
	public String getLabel() {
		return label;
	}
	
	public String setLabel(String label) {
		String old = this.label;
		if (label == null) {
			this.label = "";
		} else {
			this.label = label;
		}
		return old;
	}
}
