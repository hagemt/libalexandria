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

import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import lib.alexandria.LabelledEntity;
import lib.alexandria.LearningModel;
import lib.alexandria.ModelConstants;

public class LatchedThreadGroup extends LabelledEntity implements Callable<Long> {
	private final TreeMap<String, Thread> tasks;
	private final CountDownLatch start_signal, ready_count, finished_count;
	
	public LatchedThreadGroup(LearningModel... models) {
		start_signal = new CountDownLatch(1);
		ready_count = new CountDownLatch(models.length);
		finished_count = new CountDownLatch(models.length);
		tasks = new TreeMap<String, Thread>();
		for (final LearningModel model : models) {
			tasks.put(model.getLabel(), new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ready_count.countDown();
						start_signal.await();
						model.benchmark();
						finished_count.countDown();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}));
		}
	}

	@Override
	public Long call() {
		for (Thread t : tasks.values()) {
			t.start();
		}
		try {
			ready_count.await();
			start_signal.countDown();
			finished_count.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finished_count.getCount();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(ModelConstants.DEFAULT_BUFFER_SIZE);
		sb.append("latched-thread-group:");
		for (String s : tasks.keySet()) {
			sb.append(' ');
			sb.append(s);
		}
		return sb.toString();
	}
}
