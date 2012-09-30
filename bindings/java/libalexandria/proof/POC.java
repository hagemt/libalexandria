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
package libalexandria.proof;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

import libalexandria.ModelConstants;
import libalexandria.ann.Cortex;

public class POC implements ModelConstants {
	static {
		System.loadLibrary("jalexandria");
	}

	/* Symbol for FORTRAN function */
	public static native void println(String s);
	
	public static void main(String... args) {
		// Simple POC
		for (String s : args) {
			POC.println(s);
		}
		// Hell yeah threads!
		int proc_count = Runtime.getRuntime().availableProcessors();
		System.out.println("libalexandria -- proof-of-concept sees " + proc_count + " processors...");
		ExecutorService pool = java.util.concurrent.Executors.newFixedThreadPool(proc_count);
		LinkedList<LatchedThreadGroup> comparisons = new LinkedList<LatchedThreadGroup>();
		/* Cortex comparison */
		Cortex java_cortex = new Cortex("java-cortex", false);
		Cortex native_cortex = new Cortex("native-cortex", true);
		comparisons.add(new LatchedThreadGroup(java_cortex, native_cortex));
		// Woah, man, too many threads
		System.out.println("libalexandria -- proof-of-concept active comparisons:");
		for (LatchedThreadGroup tg : comparisons) {
			System.out.println("\t" + tg);
		}
		pool.execute(comparisons.element());
	}
}
