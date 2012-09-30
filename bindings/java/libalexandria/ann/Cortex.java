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
package libalexandria.ann;

import java.util.Arrays;

import libalexandria.ModelConstants;
import libalexandria.LearningModel;

public class Cortex extends LearningModel implements ModelConstants {
	/* Each column on an NxN grid */
	private byte[][] columns;
	protected Thread worker;

	public Cortex(String label) {
		this(label, true);
	}
	
	public Cortex(String label, boolean isNative) {
		this(label, DEFAULT_HTM_DIMENSION, isNative);
	}
	
	public Cortex(String label, int dimension, boolean isNative) {
		super(ModelType.REINFORCEMENT, label, true);
		if (dimension < MIN_HTM_DIMENSION) {
			throw new IllegalArgumentException("dimension must be sufficiently large");
		}
		columns = new byte[dimension][];
		for (int i = 0; i < columns.length; ++i) {
			columns[i] = new byte[dimension];
			Arrays.fill(columns[i], (byte)(0));
		}
		if (isNative) {
			worker = new Thread(new NativeWorker(this));
		} else {
			worker = new Thread(new JavaWorker(this));
		}
	}
	
	public int getDimension() {
		return columns.length;
	}
	
	public void learn() {
		worker.start();
	}
	
	public void halt(long wait) {
		worker.interrupt();
		try {
			worker.join(wait);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	@Override
	public void benchmark() {
		System.out.println("[" + label + "] learning...");
		try {
			this.learn();
			Thread.sleep(DEFAULT_RUN_TIME);
			this.halt(DEFAULT_JOIN_TIME);
		} catch (Exception e) {
			System.err.println("[" + label + "] failed!");
			e.printStackTrace();
		}
	}
}
