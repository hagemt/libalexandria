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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lib.alexandria.Profileable;
import lib.alexandria.naming.LabelledEntity;

/**
 * A sophisticated multi-threaded test harness that uses system
 * introspection to determine the number of threads may execute
 * in parallel on the current machine. This is termed a harness'
 * "multiplicity." The iterator supplies groupings of this size
 * that are combinatorically assigned and internally latched.
 * So, if you have two processors and submit three tasks in a
 * group. We will perform three comparisons. Likewise for the
 * situation with one processor. For two processors, the counts
 * would be one and two, respectively. This ensures that all
 * reasonable attributes are taken into account and produces
 * the most meaningful performance metrics possible. 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class UnityHarness extends LabelledEntity implements Iterable<Runnable> {
	private final Lock lock;
	private final int multiplicity;
	/* LTGs move through the queue fluidly */
	private final Queue<LatchedThreadGroup> queue;

	public UnityHarness(Class<? extends LabelledEntity> clazz) {
		super(clazz.getSimpleName() + "-harness");
		lock = new ReentrantLock(true);
		multiplicity = Runtime.getRuntime().availableProcessors();
		queue = new ConcurrentLinkedQueue<LatchedThreadGroup>();
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

	public void addGroup(String label, Profileable... p) {
		// We will add combinations of p
		int n = p.length;
		int m = Math.min(multiplicity, n);
		Profileable[] chosen = new Profileable[m];
		// Setup an index for choosing
		int[] index = new int[m];
		for (int i = 0; i < m; ++i) {
			index[i] = i;
		}
		// Modification of the queue requires lock
		synchronized (lock) {
			// TODO change index for each invocation
			for (int i = 0; i < m; ++i) {
				chosen[i] = p[index[i]];
			}
			queue.add(new LatchedThreadGroup(label, chosen));
		}
	}
	
	public String toString() {
		return this.plus(queue);
	}
}
