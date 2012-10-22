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

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lib.alexandria.LearningModel;
import lib.alexandria.naming.LabelledEntity;

class Tester extends LabelledEntity implements Iterable<LatchedThreadGroup> {
	private final Runtime runtime;
	private final ExecutorService pool;
	/* LTGs move through the queue fluidly */
	private final LinkedHashMap<String, LatchedThreadGroup> queue;
	
	public Tester(String label) {
		super(label);
		runtime = Runtime.getRuntime();
		pool = newFixedThreadPool(bandwidth());
		queue = new LinkedHashMap<String, LatchedThreadGroup>();
	}

	public void addComparison(LearningModel... models) {
		LatchedThreadGroup tg = new LatchedThreadGroup(models);
		queue.put(tg.toString(), tg);
	}

	public int bandwidth() {
		return runtime.availableProcessors();
	}

	public long compare(LatchedThreadGroup tg, int timeout, TimeUnit units)
			throws TimeoutException, InterruptedException, ExecutionException {
		if (pool.isShutdown()) {
			throw new IllegalStateException("pool's closed");
		}
		return pool.submit(tg).get(timeout, units);
	}

	public Iterable<LatchedThreadGroup> die() {
		pool.shutdown();
		return this;
	}

	@Override
	public Iterator<LatchedThreadGroup> iterator() {
		return new Compariterator(queue);
	}
}
