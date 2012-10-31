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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static lib.alexandria.Generate.LOG;
import static lib.alexandria.Generate.SPOOL;
import lib.alexandria.Profileable;

class LatchedThreadGroup extends Unity<Long> {
	private CountDownLatch start_signal, ready_count, finished_count;
	
	private synchronized void resetLatches(int value) {
		start_signal = new CountDownLatch(1);
		ready_count = new CountDownLatch(value);
		finished_count = new CountDownLatch(value);
	}
	
	public LatchedThreadGroup(String label, Profileable... candidates) {
		super(label, SPOOL.given(label));
		for (final Profileable candidate : candidates) {
			Runnable task = new Runnable() {
				@Override
				public void run() {
					try {
						ready_count.countDown();
						start_signal.await();
						candidate.benchmark();
						// FIXME shouldn't do in finally?
						finished_count.countDown();
					} catch (InterruptedException ie) {
						LOG.w(candidate, "was interrupted");
					} catch (Exception e) {
						LOG.e(candidate, "terminated abnormally");
					}
				}
			};
			super.add(candidate, task);
		}
		resetLatches(super.size());
	}
	
	@Override
	public synchronized Long call(int amount, TimeUnit unit) throws UnityException {
		super.start();
		try {
			ready_count.await();
			start_signal.countDown();
			finished_count.await();
		} catch (InterruptedException ie) {
			throw new UnityException(ie);
		}
		return finished_count.getCount();
	}

	@Override
	public synchronized void reset(boolean interrupt) throws UnityException {
		try {
			super.stop(interrupt);
		} catch (InterruptedException ie) {
			throw new UnityException(ie);
		}
		resetLatches(super.size());
	}
}
