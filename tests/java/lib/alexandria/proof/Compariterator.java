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
package lib.alexandria.proof;

import java.util.Iterator;
import java.util.Map;

/**
 * Provides an easy way to manage a remove-on-access Map.
 * As soon as an element is iterated over, it's removed.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @see lib.alexandria.proof.POC
 */
class Compariterator implements Iterator<LatchedThreadGroup> {
	private Map<String, LatchedThreadGroup> ref;
	private Iterator<LatchedThreadGroup> itr;

	public Compariterator(Map<String, LatchedThreadGroup> m) {
		this.ref = m;
		this.itr = m.values().iterator();
	}

	@Override
	public boolean hasNext() {
		return itr.hasNext();
	}

	@Override
	public LatchedThreadGroup next() {
		LatchedThreadGroup tg = itr.next();
		ref.remove(tg.toString());
		return tg;
	}

	@Override
	public void remove() {
		itr.remove();
	}
}

