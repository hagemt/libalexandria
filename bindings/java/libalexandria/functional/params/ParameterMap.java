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
package libalexandria.functional.params;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import libalexandria.LabelledEntity;

/**
 * A convenient, generic, and efficient default implementation of a type with parameters.
 * @see libalexandria.functional.params.Parameterized
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @param <N> a numeric type
 */
public abstract class ParameterMap<N extends Number> extends LabelledEntity implements Parameterized<N> {
	private Map<String, N> parameters;
	
	protected ParameterMap(String label) {
		super(label);
		parameters = new TreeMap<String, N>();
	}
	
	protected ParameterMap(ParameterMap<N> m) {
		this(m.getLabel());
		parameters.putAll(m.parameters);
	}

	@Override
	public boolean addParameter(String name, N n) {
		/* don't clobber old mappings */
		if (this.hasParameter(name)) {
			return false;
		}
		/* put returns the old mapping, which should be null */
		return (parameters.put(name, n) == null);
	}

	@Override
	public N getParameter(String name) {
		return parameters.get(name);
	}

	@Override
	public boolean removeParameter(String name) {
		return (parameters.remove(name) != null);
	}

	@Override
	public synchronized boolean renameParameter(String oldName, String newName) {
		/* This is an ACID transaction */
		if (this.hasParameter(newName) || !this.hasParameter(oldName)) {
			return false;
		}
		/* value is not null, see getParameter */
		N value = this.getParameter(oldName);
		if (this.addParameter(newName, value)) {
			if (this.removeParameter(oldName)) {
				return true;
			}
			/* if removal of the old value fails, try to remove the new one */
			this.removeParameter(newName);
			/* in the worst case, oldName will be aliased to newName */
		}
		return false;
	}

	@Override
	public N setParameter(String name, N n) {
		if (this.hasParameter(name)) {
			return parameters.put(name, n);
		}
		return null;
	}

	@Override
	public boolean hasParameter(String name) {
		return parameters.containsKey(name);
	}

	@Override
	public Iterator<Parameter<N>> iterator() {
		return new Paramiterator<N>(parameters);
	}
}
