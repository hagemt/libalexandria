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

import java.util.LinkedHashMap;
import java.util.Map;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.defaultThreadFactory;

import static lib.alexandria.ModelConstants.DEFAULT_TIME_TEST;
import static lib.alexandria.ModelConstants.DEFAULT_TIME_UNIT;

import static lib.alexandria.Generate.LOG;

import lib.alexandria.naming.LabelledEntity;

/**
 * Encapsulates a unit test (essentially a named executable)
 * @author Tor E Hagemann <hagmet@rpi.edu>
 * @param <V> the type the test should return
 */
public abstract class Unity<V> extends LabelledEntity implements Callable<V>, Runnable {
	private final Map<String, Thread> task_map;
	private final ThreadFactory factory;
	
	protected Unity(String label, ThreadFactory factory) {
		super(label);
		task_map = new LinkedHashMap<String, Thread>();
		this.factory = (factory != null) ? factory : defaultThreadFactory();
	}
		
	protected synchronized void add(String id, Runnable task) {
		task_map.put(id, factory.newThread(task));
	}
	
	protected synchronized void start() {
		for (Thread t : task_map.values()) {
			t.start();
		}
	}
	
	protected synchronized void stop(boolean interrupt) throws InterruptedException {
		for (Thread t : task_map.values()) {
			if (t.isAlive()) {
				if (interrupt) {
					t.interrupt();
				}
				t.join();
			}
		}
	}
	
	protected synchronized int size() {
		return task_map.size();
	}

	@Override
	public String toString() {
		return this.plus(task_map.keySet());
	}

	@Override
	public V call() throws UnityException {
		return call(DEFAULT_TIME_TEST, DEFAULT_TIME_UNIT);
	}

	@Override
	public void run() {
		V value = null;
		try {
			LOG.i(this, "starting unit test...");
			value = call();
		} catch (UnityException ue) {
			ue.printStackTrace();
		} finally {
			LOG.i(this, "unit test result: " + value);
		}
	}

	public abstract V call(int amount, TimeUnit unit) throws UnityException;
	public abstract void reset(boolean interrupt) throws UnityException;
}
