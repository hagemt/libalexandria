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
package libalexandria.functional;

import java.io.IOException;

import java.nio.ByteBuffer;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import libalexandria.functional.params.ParameterMap;

/**
 * A function taking in a number of N's and producing a real number (double)
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 * @param <N> the type of this function's parameters
 */
public abstract class RealParameterizedFunction<N extends Number> extends ParameterMap<N> implements Function<Double> {
	/**
	 * This function's private data pipe
	 */
	private DataPipeline<N, Double> pipe;

	private void initialize() throws IOException {
		ByteBuffer buffer = alloc();
		assert(buffer.isDirect());
		pipe = new DataPipeline<N, Double>(buffer);
	}
	
	protected RealParameterizedFunction(String label) {
		super(label);
		try {
			initialize();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	protected RealParameterizedFunction(RealParameterizedFunction<? extends N> base) {
		super(base);
		try {
			initialize();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/* Submit some getArity() N's to pipeline
	public synchronized int offer(Collection<? extends N> c) {
		// TODO move to RealParameterizedFunction?
		int written = 0;
		Iterator<? extends N> itr = c.iterator();
		for (int r = pipe.remaining(); r >= Double.SIZE; ++written) {
			if (itr.hasNext()) {
				buffer.putDouble(itr.next().doubleValue());
			} else {
				break;
			}
		}
		return written;
	} */

	@Override
	public Class<Double> getReturnType() {
		return Double.class;
	}
	
	@Override
	public Future<Double> getFuture() {
		return new FutureTask<Double>(this);
	}

	@Override
	public Double call() throws Exception {
		sync();
		// TODO Auto-generated constructor stub
		return pipe.next();
	}

	/* Required of subclasses */
	private native ByteBuffer alloc();
	protected abstract void sync();
	private native void free();

	@Override
	public void close() throws IOException {
		boolean state = pipe.setMode(false);
		assert(state == false);
		this.free();
	}

	@Override
	public boolean isOpen() {
		return pipe.isOpen();
	}
}
