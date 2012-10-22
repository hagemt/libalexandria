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
package lib.alexandria.reinforcement.nn;

import java.io.IOException;
import java.util.Arrays;

import lib.alexandria.LearningModel;

import static lib.alexandria.Generate.LOG;

import static lib.alexandria.ModelConstants.DEFAULT_HTM_DIMENSION;
import static lib.alexandria.ModelConstants.DEFAULT_JOIN_TIME;
import static lib.alexandria.ModelConstants.DEFAULT_RUN_TIME;
import static lib.alexandria.ModelConstants.MIN_HTM_DIMENSION;
import static lib.alexandria.ModelConstants.ModelType;

public class Cortex extends LearningModel {
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
		super(label, ModelType.REINFORCEMENT, true);
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
	
	private void halt(long wait) throws InterruptedException {
		if (worker != null && worker.isAlive()) {
			worker.interrupt();
			worker.join(wait);
		}
	}

	@Override
	public void benchmark() {
		LOG.i(this, "cortex is learning");
		try {
			worker.start();
			Thread.sleep(DEFAULT_RUN_TIME);
			halt(DEFAULT_JOIN_TIME);
		} catch (Exception e) {
			LOG.w(this, "cortex learning failed");
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		try {
			halt(DEFAULT_JOIN_TIME);
		} catch (InterruptedException ie) {
			throw new IOException(ie);
		}
	}
}
