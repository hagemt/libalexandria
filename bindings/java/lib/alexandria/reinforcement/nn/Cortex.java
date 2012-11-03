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
import static java.util.Arrays.fill;

import lib.alexandria.LearningModel;

import static lib.alexandria.Generate.LOG;

import static lib.alexandria.ModelConstants.DEFAULT_HTM_DIMENSION;
import static lib.alexandria.ModelConstants.DEFAULT_HTM_DIMENSION_MIN;
import static lib.alexandria.ModelConstants.DEFAULT_TIME_JOIN;
import static lib.alexandria.ModelConstants.DEFAULT_TIME_RUN;
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
		if (dimension < DEFAULT_HTM_DIMENSION_MIN) {
			throw new IllegalArgumentException("dimension must be sufficiently large");
		}
		columns = new byte[dimension][];
		for (int i = 0; i < columns.length; ++i) {
			columns[i] = new byte[dimension];
			fill(columns[i], (byte)(0));
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
		try {
			worker.start();
			LOG.i(this, "waiting for worker");
			Thread.sleep(DEFAULT_TIME_RUN);
			halt(DEFAULT_TIME_JOIN);
		} catch (Exception e) {
			LOG.w(this, "learning failed");
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		try {
			halt(DEFAULT_TIME_JOIN);
		} catch (InterruptedException ie) {
			throw new IOException(ie);
		}
	}
}
