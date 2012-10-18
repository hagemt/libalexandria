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
package lib.alexandria.sampling;

import java.util.Vector;

import lib.alexandria.LabelledEntity;

public abstract class SampleSpace extends LabelledEntity {
	protected Ordinal<Integer> cardinality;
	protected Vector<Feature> features;
	
	protected SampleSpace(String label) {
		this(3, label);
	}
	
	protected SampleSpace(int dimension, String label) {
		super(label);
		features = new Vector<Feature>(dimension);
		cardinality = new Cardinal(0);
	}

	public boolean isFinite() {
		return cardinality.isFinite();
	}

	public boolean isEnumerable() {
		return cardinality.isCountable();
	}
}
