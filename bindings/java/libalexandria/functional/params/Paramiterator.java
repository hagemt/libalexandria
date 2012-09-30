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
import java.util.Map.Entry;

/**
 * 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @param <N> 
 */
public class Paramiterator<N extends Number> implements Iterator<Parameter<N>> {
	private final Iterator<Entry<String, N>> iterator;
	
	public Paramiterator(Map<String, N> m) {
		iterator = m.entrySet().iterator();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Parameter<N> next() {
		Map.Entry<String, N> entry = iterator.next();
		return new MutableParameter<N>(entry.getKey(), entry.getValue());
	}

	@Override
	public void remove() {
		iterator.remove();
	}
}
