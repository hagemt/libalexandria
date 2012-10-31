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
package lib.alexandria.testing;

import java.util.Iterator;
import java.util.Queue;

/**
 * Provides an easy way to manage a remove-on-access Map.
 * As soon as an element is iterated over, it's removed.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @see lib.alexandria.proof.POC
 */
abstract class Compariterator<T extends LatchedThreadGroup> implements Iterator<Runnable> {
	private Queue<T> ref;
	private Iterator<T> itr;

	public Compariterator(Queue<T> q) {
		this.ref = q;
		this.itr = ref.iterator();
	}

	@Override
	public boolean hasNext() {
		if (itr.hasNext()) {
			return true;
		} else {
			done();
			return false;
		}
	}

	@Override
	public Runnable next() {
		T t = ref.peek();
		itr.remove();
		return t;
	}

	@Override
	public void remove() {
		itr.remove();
	}

	protected abstract void done();
}

