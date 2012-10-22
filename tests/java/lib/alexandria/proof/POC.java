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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static lib.alexandria.Generate.LOG;
import static lib.alexandria.Generate.reseed;

import static lib.alexandria.ModelConstants.DEFAULT_JOIN_TIME;
import static lib.alexandria.ModelConstants.DEFAULT_RUN_TIME;
import static lib.alexandria.ModelConstants.DEFAULT_TIME_UNIT;

import lib.alexandria.reinforcement.nn.Cortex;
import lib.alexandria.functional.kernels.KernelType;
import lib.alexandria.supervised.KSVM;

public class POC {
	private static final long seed;
	static {
		System.loadLibrary("jalexandria");
		System.loadLibrary("jpoc");
		reseed(seed = System.nanoTime());
		initialize(seed);
	}

	public static native void println(String s);

	public static native void initialize(long seed);
	public static native void finalize(long seed);

	private static String time(int amount, TimeUnit units) {
		return amount + " " + units.toString().toLowerCase();
	}

	public static void main(String... args) {
		// Simple POC
		if (args.length != 0) {
			for (String s : args) {
				POC.println(s);
			}
			finalize(seed);
			return;
		}
		// Hell yeah threads! (provided we got no args)
		Tester poc = new Tester("POC");
		poc.addComparison(new Cortex("java", false), new Cortex("native", true));
		LOG.d(poc, "there are " + poc.bandwidth() + " processors available");
		int timeout = DEFAULT_RUN_TIME + DEFAULT_JOIN_TIME;
		TimeUnit units = DEFAULT_TIME_UNIT;
		LOG.i(poc, "running all comparisons for " + time(timeout, units));
		for (LatchedThreadGroup tg : poc) {
			LOG.i(tg, tg.toString());
			try {
				long result = poc.compare(tg, timeout, units);
				LOG.i(tg, "completed, returned: " + result);
			} catch (TimeoutException e) {
				LOG.w(tg, "failed to complete within: " + time(timeout, units));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Woah, too many threads...
		for (LatchedThreadGroup tg : poc.die()) {
			LOG.w(tg, tg.toString() + " was not run");
		}
		LOG.i(poc, "will now benchmark other models");
		// Benchmark all the kernels
		for (KernelType t : KernelType.values()) {
			KSVM svm = new KSVM(t.toString() + "-svm", t);
			LOG.i(svm, "starting benchmark");
			try {
				svm.benchmark();
				svm.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				LOG.i(svm, "finished benchmark");
			}
		}
		LOG.i(poc, "shutting down");
		finalize(seed);
	}
}
