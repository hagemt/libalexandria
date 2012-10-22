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
	private static final Semaphore refs;
	private static long flags;
	static {
		refs = new Semaphore(Long.SIZE);
		flags = ~(0L);
	}
	
	public static final synchronized long get() throws InterruptedException {
		refs.acquire();
		// If we acquired through the semaphore, one bit must be available.
		assert(flags != 0L);
		long key = Long.highestOneBit(flags);
		// Turn it off and return it.
		flags &= ~key;
		return key;
	}
	
	public static final synchronized boolean put(int key) {
		long new_flags = flags | key;
		// If the key changes flags, then it is a valid return.
		if (new_flags != flags) {
			flags = new_flags;
			refs.release();
			return true;
		}
		return false;
	}
}
