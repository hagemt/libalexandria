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
	private final int count;
	private final ByteBuffer buffer;
	
	public enum State {
		ENABLED, DISABLED, ALLOCATION_ERROR;
		public static State given(boolean enabled, ByteBuffer buffer) {
			if (buffer == null || !buffer.hasArray() || !buffer.isDirect()) {
				return ALLOCATION_ERROR;
			}
			return (enabled) ? ENABLED : DISABLED;
		}
	};
	
	public Aqueduct(int count) throws NativeAllocationException {
		this(count, alloc());
	}

	public Aqueduct(int count, ByteBuffer buffer) {
		this.count = count;
		this.buffer = buffer;
		this.state = State.given(true, buffer);
	}
	
	public static native ByteBuffer alloc() throws NativeAllocationException;
	private native void free() throws NativeAllocationException;
	
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
		if (isEnabled()) {
			boolean state = setEnabled(false);
			assert(state == false);
		}
		this.free();
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
