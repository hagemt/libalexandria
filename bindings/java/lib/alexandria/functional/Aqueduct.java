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

import java.nio.ByteBuffer;

/**
 * A generic pipeline implementation for libalexandria.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @param <M> the type of variables read into the stream
 * @param <N> the type of variables read out of the stream
 */
class Aqueduct<M extends Number, N extends Number> {
	private boolean enabled;
	private final ByteBuffer buffer;

	public Aqueduct(ByteBuffer buffer) {
		this.buffer = buffer;
		this.enabled = isOpen();
	}
	
	public int next(M m) {
		// TODO Auto-generated constructor stub
		return m.intValue();
	}
	
	public N next() {
		// TODO Auto-generated constructor stub
		return null;
	}
	
	public synchronized int remaining() {
		return (buffer == null) ? 0 : buffer.remaining();
	}
	
	public synchronized boolean isOpen() {
		return (enabled && buffer != null && buffer.hasArray() && buffer.isDirect());
	}

	public boolean setMode(boolean state) {
		enabled = state;
		return isOpen();
	}
}
