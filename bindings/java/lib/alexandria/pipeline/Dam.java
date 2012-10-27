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
package lib.alexandria.pipeline;

import java.util.concurrent.Semaphore;

/**
 * An important locking mechanism from which native keys are managed.
 * TODO is there a way to get more than sixty-four? bitset DS?
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class Dam {
	private static final Semaphore loch;
	private static long pool;
	static {
		loch = new Semaphore(Long.SIZE);
		pool = ~(0L);
	}

	private Dam() { }
	
	public static final synchronized long get() throws InterruptedException {
		loch.acquire();
		// If we acquired a lock, at least one bit must be set. (availability)
		assert(pool != 0L);
		long key = Long.highestOneBit(pool);
		// Turn it off and return it.
		pool &= ~key;
		return key;
	}
	
	public static final synchronized boolean put(long key) {
		long set = pool | key;
		// If the key changes the pool, then it is a valid return.
		if (set != pool) {
			pool = set;
			loch.release();
			return true;
		}
		return false;
	}
}
