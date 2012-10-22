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
package lib.alexandria.pipeline;

import java.io.Closeable;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * A generic pipeline implementation for libalexandria.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @param <M> the type of variables read into the stream
 * @param <N> the type of variables read out of the stream
 */
public class Aqueduct<M extends Number, N extends Number> extends Pipe implements Closeable {
	private State state;
	private final ByteBuffer buffer;
	private final int interval;
	private final long key;
	
	public enum State {
		ENABLED, DISABLED, ALLOCATION_ERROR;
		public static State given(boolean enabled, ByteBuffer buffer) {
			if (buffer == null || !buffer.hasArray() || !buffer.isDirect()) {
				return ALLOCATION_ERROR;
			}
			return (enabled) ? ENABLED : DISABLED;
		}
	};
	
	public Aqueduct(int interval) throws InterruptedException {
		this(Dam.get(), interval);
	}
	
	private Aqueduct(long key, int interval) {
		this(alloc(key), key, interval);
	}

	private Aqueduct(ByteBuffer buffer, long key, int interval) {
		this.key = key;
		this.interval = interval;
		this.buffer = buffer;
		this.state = State.given(true, buffer);
	}
	
	private static native ByteBuffer alloc(long key);
	private static native void free(long key);
	
	public synchronized int remaining() {
		return (buffer == null) ? 0 : buffer.remaining();
	}
	
	public synchronized boolean isEnabled() {
		return state.equals(State.ENABLED);
	}

	public synchronized boolean setEnabled(boolean state) {
		this.state = State.given(state, buffer);
		return isEnabled();
	}
	
	public synchronized State getState() {
		return state;
	}

	@Override
	public void close() throws IOException {
		setEnabled(false);
		if (buffer != null && buffer.hasArray()) {
			free(key);
			assert(!buffer.hasArray());
		}
	}

	@Override
	public SinkChannel sink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SourceChannel source() {
		// TODO Auto-generated method stub
		return null;
	}
}
