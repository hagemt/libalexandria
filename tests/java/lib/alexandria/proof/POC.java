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

import static lib.alexandria.Generate.LOG;
import static lib.alexandria.Generate.reseed;
import static lib.alexandria.Generate.randomType;

import java.io.IOException;

import lib.alexandria.LearningModel;

import lib.alexandria.functional.kernels.Kernel;
import lib.alexandria.functional.kernels.KernelType;

import lib.alexandria.reinforcement.nn.Cortex;
import lib.alexandria.testing.UnityHarness;

/**
 * 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class POC {
	private static final long seed;
	private static final UnityHarness<LearningModel> model_harness; 
	static {
		System.loadLibrary("jalexandria");
		System.loadLibrary("jpoc");
		reseed(seed = System.nanoTime());
		initialize(seed);
		Cortex java_cortex = new Cortex("java", false);
		Cortex native_cortex = new Cortex("native", true);
		model_harness = new UnityHarness<LearningModel>("ML");
		model_harness.addGroup("cortex", java_cortex, native_cortex);
	}

	private POC() { }
	
	public static native void println(String s);

	public static native void initialize(long seed);
	public static native void finalize(long seed);

	public static void main(String... args) {
		// Simple POC
		if (args.length != 0) {
			for (String s : args) {
				POC.println(s);
			}
		} else {
			//
			new Proof<LearningModel>(model_harness).run();
			//
			Kernel k = randomType(KernelType.class).getDefault();
			try {
				LOG.i(k, "starting benchmark");
				k.benchmark();
				k.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				LOG.i(k, "finished benchmark");
			}
		}
		finalize(seed);
	}
}
