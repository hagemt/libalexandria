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
package lib.alexandria.functional.params;

import java.util.Map.Entry;

import lib.alexandria.LabelledEntity;

/**
 * Parameters are pairs of strings and some numeric type
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class Parameter<N extends Number> extends LabelledEntity implements Entry<String, N> {
	private N value;
	
	protected Parameter(String key, N value) {
		super(key);
		this.value = value;
	}
	
	protected Parameter(Entry<String, ? extends N> e) {
		this(e.getKey(), e.getValue());
	}
	
	@Override
	public String getKey() {
		return getLabel();
	}

	@Override
	public N getValue() {
		return value;
	}

	@Override
	public N setValue(N value) {
		N old = this.value;
		this.value = value;
		return old;
	}
	
	@Override
	public String setLabel(String label) {
		throw new UnsupportedOperationException("cannot change parameter labels");
	}
}
