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

import static lib.alexandria.Generate.reseed;
import static lib.alexandria.functions.kernels.KernelType.GAUSS;
import static lib.alexandria.functions.kernels.KernelType.POLY;

import lib.alexandria.models.LearningModel;
import lib.alexandria.models.reinforcement.nn.Cortex;
import lib.alexandria.models.supervised.KSVM;
import lib.alexandria.testing.UnityHarness;

/**
 * Provides a simple example and a sophisticated example. 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class POC {
	private static final long seed;
	static {
		/**
		 * Load the main and testing libraries.
		 */
		System.loadLibrary("jalexandria");
		System.loadLibrary("jpoc");
		// Keep the seed for finalization 
		reseed(seed = System.nanoTime());
		initialize(seed);
	}

	private POC() { }
	
	public static native void println(String s);

	public static native void initialize(long seed);
	public static native void finalize(long seed);

	public static void main(String... args) {
		// When no arguments are given, run harness
		if (args.length == 0) { 
			/**
			 * Prepare a test harness for learning models.
			 */
			UnityHarness model_harness = new UnityHarness(LearningModel.class);
			// Cortex models
			Cortex java_cortex = new Cortex("java", false);
			Cortex native_cortex = new Cortex("native", true);
			// Kernel Support Vector Machine models
			KSVM java_ksvm = new KSVM("polynomial", POLY.getDefault());
			KSVM native_ksvm = new KSVM("gaussian", GAUSS.getDefault());
			// Add both groups and run a proof-of-concept
			model_harness.addGroup("cortex", java_cortex, native_cortex);
			model_harness.addGroup("ksvm", java_ksvm, native_ksvm);
			new Proof(model_harness).run();
		}
		// Simple POC
		for (String s : args) {
			POC.println(s);
		}
		finalize(seed);
	}
}
