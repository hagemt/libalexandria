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
package lib.alexandria.functional;

import java.io.IOException;

import java.nio.ByteBuffer;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static lib.alexandria.Generate.LOG;
import lib.alexandria.functional.params.ParameterMap;
import lib.alexandria.pipeline.Aqueduct;

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
	private Aqueduct<N, Double> pipe;
	/**
	 * TODO make this part of the aqueduct
	 */
	private ByteBuffer buffer;

	private void initialize(ByteBuffer b) throws IOException {
		buffer = (b == null) ? Aqueduct.alloc() : b;
		assert(buffer.isDirect());
		LOG.i(this, "buffer established");
		pipe = new Aqueduct<N, Double>(getArity(), buffer);
	}
	
	protected RealParameterizedFunction(String label) {
		super(label);
		try {
			initialize(null);
		} catch (IOException ioe) {
			throw new IllegalArgumentException(ioe);
		}
	}

	protected RealParameterizedFunction(RealParameterizedFunction<? extends N> base) {
		super(base);
		try {
			initialize(base.buffer);
		} catch (IOException ioe) {
			throw new IllegalArgumentException(ioe);
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
		this.sync();
		return apply();
	}
	
	@Override
	public void close() throws IOException {
		pipe.close();
	}

	/* Required of subclasses */
	protected abstract void sync();
	private native Double apply();
}
