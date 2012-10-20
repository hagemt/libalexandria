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

import java.io.IOException;

import java.util.LinkedList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
	
	public static void main(String... args) {
		// Simple POC
		if (args.length != 0) {
			for (String s : args) {
				POC.println(s);
			}
			/* Cleanup */
			finalize(seed);
			return;
		}
		// Hell yeah threads! (provided we got no args)
		int proc_count = Runtime.getRuntime().availableProcessors();
		System.out.println("libalexandria -- proof-of-concept sees " + proc_count + " processors...");
		ExecutorService pool = java.util.concurrent.Executors.newFixedThreadPool(proc_count);
		LinkedList<LatchedThreadGroup> comparisons = new LinkedList<LatchedThreadGroup>();
		/* Cortex comparison */
		LatchedThreadGroup cortex = new LatchedThreadGroup(new Cortex("java", false), new Cortex("native", true));
		comparisons.add(cortex);
		// Woah, man, too many threads
		System.out.println("libalexandria -- proof-of-concept active comparisons:");
		for (LatchedThreadGroup tg : comparisons) {
			System.out.println("\t" + tg);
			int timeout = DEFAULT_RUN_TIME + DEFAULT_JOIN_TIME;
			TimeUnit units = DEFAULT_TIME_UNIT;
			try {
				long result = pool.submit(tg).get(timeout, units);
				System.out.println("\tAchieved the following result: " + result);
			} catch (TimeoutException e) {
				System.err.println("\tModel did not complete within: " + timeout + " " + units.toString().toLowerCase());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/* Benchmark all the kernels */
		for (KernelType t : KernelType.values()) {
			KSVM s = new KSVM(t.toString(), t);
			System.err.println("*** BEGIN KSVM('" + s.getLabel() + "').benchmark() ***");
			s.benchmark();
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				System.err.println("*** END KSVM('" + s.getLabel() + "').benchmark() ***");
			}
		}
		/* Cleanup */
		pool.shutdown();
		finalize(seed);
	}
}
