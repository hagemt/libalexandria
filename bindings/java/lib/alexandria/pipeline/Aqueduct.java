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
 * (used for processing native data synchronously)
 * Pipelines or "aqueducts" (teehee) interact with the Dam.
 * Requests that run through it are subject to the capacity,
 * the duct's "mood," and native state of affairs.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @param <M> the type of variables read into the stream
 * @param <N> the type of variables read out of the stream
 * @see lib.alexandria.pipeline.Dam
 */
public class Aqueduct<M extends Number, N extends Number> extends Pipe implements Closeable {
	private boolean enabled;
	private final ByteBuffer buffer;
	private final int interval;
	private final long key;
	
	/**
	 * A pipeline's state isn't as simple as on/off
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 */
	public enum State {
		ENABLED, DISABLED, NO_BUFFER, BAD_BUFFER;
	};
	
	/**
	 * Requests a new pipe be created, backed by native interface.
	 * @param interval how many M's are required between applications
	 * @throws InterruptedException if a handle failed to be acquired
	 */
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
	}
	
	private static native ByteBuffer alloc(long key);
	private static native void free(long key);
	
	/**
	 * Check if this pipe can fit any more data in it.
	 * @return the number of bytes available, from our perspective
	 */
	public synchronized int remaining() {
		return isMoody() ? 0 : buffer.remaining();
	}
	
	/**
	 * Simply checks if this pipe isn't ready...
	 * @return true if getState() is NOT State.ENABLED, false otherwise
	 * @see lib.alexandria.pipeline.Aqueduct#getState()
	 */
	public boolean isMoody() {
		return (getState() != State.ENABLED);
	}

	/**
	 * Try to enable, or disable, this pipe.
	 * Note that both operations may not occur immediately.
	 * If disabling, a pipe may finish what it is doing first.
	 * If enabling, a pipe may not be able to open.
	 * @param state true for good mood, false otherwise
	 * @return true if the pipe is ready now, false otherwise
	 */
	public synchronized boolean setMood(boolean state) {
		this.enabled = state;
		return !isMoody();
	}

	/**
	 * Retrieve the current state of affairs for this pipe.
	 * @return true if the pipe is ready, false otherwise
	 */
	public synchronized State getState() {
		if (buffer == null) {
			return State.NO_BUFFER;
		} else if (buffer.hasArray() && buffer.isDirect()) {
			return (enabled) ? State.ENABLED : State.DISABLED;
		} else {
			return State.BAD_BUFFER;
		}
	}

	@Override
	public void close() throws IOException {
		setMood(false);
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
