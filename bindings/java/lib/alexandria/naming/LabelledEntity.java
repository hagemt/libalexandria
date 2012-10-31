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
package lib.alexandria.naming;

import static lib.alexandria.Generate.randomString;

import java.util.Collection;

/**
 * Provides any type with a label for purposes of identification.
 * Labels are intentionally designed to provide non-null values.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public abstract class LabelledEntity implements Labelled, Comparable<Labelled> {
	private String label;
	
	/**
	 * By default, a label is generated.
	 */
	protected LabelledEntity() {
		this(randomString());
	}
	
	/**
	 * Construct an entity with the given label.
	 * Any null argument results in label generation.
	 * @param label a textual identifier for this entity
	 */
	protected LabelledEntity(String label) {
		this.label = (label == null) ? randomString() : label;
	}
	
	/**
	 * Fetch this entity's current label (identifier)
	 * @return a textual identifier for this entity
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Assign this entity a given label (identifier)
	 * @param label the new identifier for this entity
	 * @return the previous label
	 */
	public String setLabel(String label) {
		String oldLabel = this.label;
		if (label == null) {
			this.label = "";
		} else {
			this.label = label;
		}
		return oldLabel;
	}

	@Override
	public int compareTo(Labelled l) {
		return label.compareTo(l.getLabel());
	}

	@Override
	public String toString() {
		return getLabel();
	}
	
	/**
	 * Utility method for subclasses to concatenate "labels."
	 * Doesn't modify the current label; returns a copy.
	 * @param suffix text on which to prepend ourselves
	 * @return this label's text w/ the given suffix
	 */
	protected final String plus(final String suffix) {
		return label + '-' + suffix;
	}
	
	protected final String plus(final Collection<? extends Labelled> entities) {
		int capacity = label.length() * entities.size();
		StringBuilder sb = new StringBuilder(capacity);
		sb.append(label);
		sb.append(':');
		for (Labelled l : entities) {
			sb.append(' ');
			sb.append(l.getLabel());
		}
		return sb.toString();
	}
}
