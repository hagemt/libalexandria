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

public abstract class Ordinal<N extends Number> implements Comparable<N> {
	protected final N value;
	
	protected Ordinal(N value) {
		if (value.intValue() < 0) {
			throw new IllegalArgumentException("ordinals are not negative");
		}
		this.value = value;
	}
	
	public abstract boolean isFinite();
	public abstract boolean isCountable();

	public N value() {
		return value;
	}
}
