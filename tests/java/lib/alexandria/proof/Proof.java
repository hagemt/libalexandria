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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static lib.alexandria.Generate.LOG;
import static lib.alexandria.Generate.SPOOL;

import static lib.alexandria.ModelConstants.DEFAULT_TIME_TEST;
import static lib.alexandria.ModelConstants.DEFAULT_TIME_UNIT;

import lib.alexandria.Profileable;
import lib.alexandria.testing.UnityHarness;

/**
 * Enables quick calling of sophisticated harnesses
 * @author Tor Hagemann <hagemt@rpi.edu>
 * @param <P> The type of profile to support
 */
public class Proof<P extends Profileable> implements Runnable {
	private final UnityHarness<P> harness;
	private final ExecutorService pool;
	
	protected Proof(UnityHarness<P> harness) {
		this.harness = harness;
		pool = newFixedThreadPool(harness.bandwidth(), SPOOL);
	}
	
	protected static String format(int amount, TimeUnit units) {
		return amount + " " + units.toString().toLowerCase();
	}

	@Override
	public void run() {
		int timeout = DEFAULT_TIME_TEST;
		LOG.i(harness, "running " + harness.bandwidth() + " task(s) at a time");
		LOG.d(harness, "running " + harness.size() + " comparison(s) total");
		LOG.v(harness, "running each comparison for " + format(timeout, DEFAULT_TIME_UNIT));
		timeout *= harness.size();
		for (Runnable r : harness) {
			// Hell yeah threads!
			pool.submit(r, null);
			//pool.invokeAll(unit.callableSet());
		}
		// Woah, too many threads...
		//for (Runnable r : pool.shutdownNow()) {
		//	LOG.w(harness, "failed to run");
		//}
		try {
			LOG.v(harness, "waiting " + format(timeout, DEFAULT_TIME_UNIT) + " for comparisons");
			boolean normal = pool.awaitTermination(timeout, DEFAULT_TIME_UNIT);
			if (!normal) {
				LOG.w(harness, "terminated abnormally after " + format(timeout, DEFAULT_TIME_UNIT));
			}
		} catch (InterruptedException ie) {
			LOG.i(harness, "interruption has triggered immediate shutdown");
			int casualties = pool.shutdownNow().size();
			if (casualties != 0) {
				LOG.w(harness, "interruption caused " + casualties + " threads to die");
			}
		} finally {
			int unfinished = harness.size();
			LOG.v(harness, "there were " + unfinished + " remaining comparisons");
			if (unfinished != 0) {
				LOG.w(harness, harness.toString());
			}
		}
	}
}
