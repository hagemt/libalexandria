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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lib.alexandria.Profileable;
import lib.alexandria.naming.LabelledEntity;

/**
 * A sophisticated multi-threaded test harness that uses system
 * introspection to determine the number of threads may execute
 * in parallel on the current machine. This is termed a harness'
 * "multiplicity." The iterator supplies groupings of 
 * 
 * FIXME NO MORE INTERFACES EXTENDED?!
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @param <T>
 */
public class UnityHarness<P extends Profileable> extends LabelledEntity implements Iterable<Runnable> {
	/* LTGs move through the queue fluidly */
	private final Map<String, LatchedThreadGroup> queue;
	private final int multiplicity;
	private final Lock lock;
	
	public UnityHarness(String label) {
		super(label);
		queue = new LinkedHashMap<String, LatchedThreadGroup>();
		multiplicity = Runtime.getRuntime().availableProcessors();
		lock = new ReentrantLock();
	}

	public Iterator<Runnable> iterator() {
		lock.lock();
		return new Compariterator<LatchedThreadGroup>(queue) {
			@Override
			protected void done() {
				lock.unlock();
			}
		};
	}

	public int bandwidth() {
		return multiplicity;
	}
	
	public int size() {
		return queue.size();
	}

	public boolean addGroup(String label, Profileable... p) {
		// If we can't acquire the lock, fail
		if (!lock.tryLock()) {
			return false;
		}
		LatchedThreadGroup g;
		int n = p.length;
		int m = Math.min(multiplicity, n);
		int[] index = new int[m];
		for (int i = 0; i < m; ++i) {
			index[i] = i;
		}
		Profileable[] chosen = new Profileable[m];
		{
			// TODO change index
			for (int i = 0; i < m; ++i) {
				chosen[i] = p[index[i]];
			}
			g = new LatchedThreadGroup(label, chosen);
			queue.put(g.getLabel(), g);
		}
		lock.unlock();
		return true;
	}
	
	public String toString() {
		return this.plus(queue.keySet());
	}
}
